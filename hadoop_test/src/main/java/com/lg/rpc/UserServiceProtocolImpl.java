package com.lg.rpc;

import java.io.File;

public class UserServiceProtocolImpl implements UserServiceProtocol{
    @Override
    public String sayHello(String name) {
        return "hello:"+name;
    }

    @Override
    public void mkdir(String filePath) {
        File file = new File(filePath);
        boolean result = file.mkdir();
    }
}
