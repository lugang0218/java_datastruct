package com.hubu.server.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class InStreamWrapper{
    
    
    
    
    
    private Socket socket;
    
    
    
    private InputStream inputStream;
    
    private InputStreamReader inputStreamReader;
    
    
    private BufferedReader bufferedReader;
    
    


    public InStreamWrapper(){}
    public InStreamWrapper(InputStream inputStream, InputStreamReader inputStreamReader, BufferedReader bufferedReader){
        this.inputStream=inputStream;
        this.bufferedReader=bufferedReader;
        this.inputStreamReader=inputStreamReader;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStreamReader getInputStreamReader() {
        return inputStreamReader;
    }

    public void setInputStreamReader(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
}