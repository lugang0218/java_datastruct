import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class Client2 {
    private static ObjectInputStream objectInputStream=null;
    InputStream inputStream=null;
    public static void main(String[] args) {
        Socket socket=new Socket();
        try {
            socket.connect(new InetSocketAddress("localhost",9090));
            OutputStream outputStream = socket.getOutputStream();
            String command="list";
            byte[] buffer = command.getBytes();
            outputStream.write(buffer);
            InputStream inputStream = socket.getInputStream();
            objectInputStream=new ObjectInputStream(inputStream);
            try {
                Object o = objectInputStream.readObject();
                List<String> fileList=(List<String>)o;
                for(String item:fileList){
                    System.out.println(item);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
