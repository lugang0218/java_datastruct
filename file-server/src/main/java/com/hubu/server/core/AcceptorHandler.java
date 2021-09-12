package com.hubu.server.core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class AcceptorHandler {
    private ServerSocket serverSocket;
    public AcceptorHandler(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return socketStreamWrapper;
        }
        return null;
    }
    public void handleAcceptor(){
        while(true){
            try {
                System.out.println("等待连接");
                Socket socket = serverSocket.accept();
                System.out.println("连接发生");
                SocketStreamWrapper wrapper = initSocketStreamWrapper(socket);
                if(wrapper!=null){
                    System.out.println("socket初始化完毕");
                    //处理socket
                    EventHandler eventHandler =new EventHandler("thread1",wrapper);
                    eventHandler.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
