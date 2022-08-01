package com.lg.rpc;

public interface UserServiceProtocol {
    long versionID = 0l;
    String sayHello(String name);
    void mkdir(String filePath);
}
