package com.hubu.server.core;

import java.net.Socket;

public class SocketStreamWrapper{
    private Socket socket;
    
    private InStreamWrapper inStreamWrapper;

    private OutStreamWrapper outStreamWrapper;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public InStreamWrapper getInStreamWrapper() {
        return inStreamWrapper;
    }

    public void setInStreamWrapper(InStreamWrapper inStreamWrapper) {
        this.inStreamWrapper = inStreamWrapper;
    }

    public OutStreamWrapper getOutStreamWrapper() {
        return outStreamWrapper;
    }

    public void setOutStreamWrapper(OutStreamWrapper outStreamWrapper) {
        this.outStreamWrapper = outStreamWrapper;
    }
}