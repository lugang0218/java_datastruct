import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
public class Server {
    private static  Queue<String> queue=new ArrayDeque<>();
    static class DfsFileThread extends Thread{

        private String filePath;
        public DfsFileThread(String filePath){
           this.filePath=filePath;
        }
        public  List<String> listAllFileFile(String filePath){
            List<String> fileNames=new ArrayList<>();
            File file=new File(filePath);
            if(file.exists()){
                File[] files = file.listFiles();
                for(File fileItem:files){
                    String name = fileItem.getName();
                }
            }
            return fileNames;
        }
        public void dfsFile(File file){
            File[] files = file.listFiles();
            if(files!=null&&files.length>0){
                for(File item:files){
                    String name=item.getName();
                    System.out.println(name);
                    synchronized (queue){
                        queue.offer(name);
                        queue.notifyAll();
                    }
                    if(file.isDirectory()){
                        dfsFile(item);
                    }
                }
            }

        }

        public  void dfsFile(String filePath){
            File file=new File(filePath);
            dfsFile(file);
        }
        @Override
        public void run() {
            dfsFile(filePath);
        }
    }

    static class WorkerThread extends Thread{
        private Socket socket;
        private byte[] buffer=new byte[1024];
        private ObjectOutputStream objectOutputStream;
        public WorkerThread(Socket socket){
            this.socket=socket;
        }
        private ArrayDeque<String> queue=new ArrayDeque<>();
        @Override
        public void run() {
            try{
                InputStream inputStream =socket.getInputStream();
                int read=0;
                read=inputStream.read(buffer);
                String command=new String(buffer,0,read);
                objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                if(command.equals("dfs")){
                    System.out.println("dfs");
                    DfsFileThread dfsFileThread=new DfsFileThread("D:\\maven_repo");
                    dfsFileThread.start();
                    while(true){
                        if(queue.isEmpty()){
                            synchronized (queue){
                                queue.wait();
                            }
                            String value = queue.poll();
                            System.out.println("value="+value);

                            objectOutputStream.writeObject(value);
                        }
                    }
                }
            }catch (Exception e){

            }
        }
    }
    public static void main(String[] args) {
        ServerSocket serverSocket=null;
        byte buffer[]=new byte[1024];
        ObjectOutputStream objectOutputStream=null;
        try {
            serverSocket=new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost",9090));
            while(true){
                Socket socket= serverSocket.accept();
                WorkerThread workerThread=new WorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
