package com.hubu.server;
import com.hubu.server.core.InStreamWrapper;
import com.hubu.server.core.OutStreamWrapper;
import com.hubu.server.core.SocketStreamWrapper;
import com.hubu.server.core.WorkThread;
import com.hubu.server.core.handler.CommandHandler;
import com.hubu.server.core.handler.MkdirCommandHandler;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FileServer {
    private boolean isStart=false;
    private int connectionSize;
    private ServerSocket serverSocket=null;

    private Integer port;
    private String localhost;
    private static final Integer MAX_CONNECTION=1024;
    private List<SocketStreamWrapper> socketStreamWrappers=new ArrayList<>();
    public FileServer(Integer port,String localhost){
        this.port=port;
        this.localhost=localhost;
    }
    /**
     * 文件服务器启动程序
     *
     *
     */
    public SocketStreamWrapper initSocketStreamWrapper(Socket socket){
        if(socket!=null) {
            SocketStreamWrapper socketStreamWrapper=new SocketStreamWrapper();
            socketStreamWrapper.setSocket(socket);
            InStreamWrapper inStreamWrapper=new InStreamWrapper();
            InputStream inputStream= null;
            try {
                inputStream = socket.getInputStream();
                OutputStream outputStream=socket.getOutputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                inStreamWrapper.setInputStream(inputStream);
                inStreamWrapper.setInputStreamReader(inputStreamReader);
                inStreamWrapper.setBufferedReader(bufferedReader);
                inStreamWrapper.setSocket(socket);
                OutStreamWrapper outStreamWrapper=new OutStreamWrapper(outputStream,outputStreamWriter,bufferedWriter);
                socketStreamWrapper.setInStreamWrapper(inStreamWrapper);
                socketStreamWrapper.setOutStreamWrapper(outStreamWrapper);
                socketStreamWrappers.add(socketStreamWrapper);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return socketStreamWrapper;
        }
        return null;
    }

    public void start() {
        init();
        if(!isStart){
            isStart=true;
            while(true){
                try {
                    System.out.println("等待连接");
                    Socket socket = serverSocket.accept();
                    System.out.println("连接发生");
                    SocketStreamWrapper wrapper = initSocketStreamWrapper(socket);

                    if(wrapper!=null){
                        System.out.println("socket初始化完毕");
                        //处理socket
                        WorkThread workThread=new WorkThread("thread1",wrapper);
                        workThread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private void init() {
        try {
            serverSocket=new ServerSocket();
            serverSocket.bind(new InetSocketAddress(this.localhost,this.port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        FileServer fileServer=new FileServer(8989,"localhost");
        fileServer.start();
    }
}
