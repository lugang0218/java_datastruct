package com.lg.rpc;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtocolProxy;
import org.apache.hadoop.ipc.RPC;
import java.io.IOException;
import java.net.InetSocketAddress;
public class RpcClient {
    public static void main(String[] args) {
        InetSocketAddress address = new InetSocketAddress("localhost",9999);
        try {
            ProtocolProxy<UserServiceProtocol> userServiceProxy = RPC.getProtocolProxy(UserServiceProtocol.class, 0l, address, new Configuration());
            UserServiceProtocol userServiceProtocolProxy = userServiceProxy.getProxy();
            String result = userServiceProtocolProxy.sayHello("lugang");
            System.out.println("the server reply is:"+result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
