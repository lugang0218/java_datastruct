package com.hubu.server.core;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class OutStreamWrapper{
    private Socket socket;
    
    
    private OutputStream outputStream;
    
    
    private OutputStreamWriter outputStreamWriter;
    
    
    private BufferedWriter bufferedWriter;

    public OutStreamWrapper(OutputStream outputStream, OutputStreamWriter outputStreamWriter, BufferedWriter bufferedWriter) {
        this.outputStream = outputStream;
        this.outputStreamWriter = outputStreamWriter;
        this.bufferedWriter = bufferedWriter;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStreamWriter getOutputStreamWriter() {
        return outputStreamWriter;
    }

    public void setOutputStreamWriter(OutputStreamWriter outputStreamWriter) {
        this.outputStreamWriter = outputStreamWriter;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }
}
