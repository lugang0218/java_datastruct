package com.hubu.server;
import com.hubu.server.core.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
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
    public void start() {
        init();
        if(!isStart){
            isStart=true;
            AcceptorHandler acceptorHandler=new AcceptorHandler(serverSocket);
            acceptorHandler.handleAcceptor();
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
