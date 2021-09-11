package com.hubu.server;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class TestServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost",9999));
            System.out.println("开始连接");
            Socket accept = serverSocket.accept();
            System.out.println("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
