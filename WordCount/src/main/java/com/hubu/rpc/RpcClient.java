package com.hubu.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RpcClient {
    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration();
        long clientVersion=0;

        Protocol proxy = RPC.getProxy(Protocol.class, clientVersion,
                new InetSocketAddress("localhost", 9999), configuration);
        String lugang = proxy.sayHello("lugang");
        System.out.println(lugang);
    }
}
