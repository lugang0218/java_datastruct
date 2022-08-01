package com.hubu.rpc;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import java.io.IOException;
interface Protocol{
    int versionID = 0;
    String sayHello(String name);
}
class ProtocolImpl implements Protocol{
    public String sayHello(String name) {
        return "hello:"+ name;
    }
}
public class RpcServer {
    public static void main(String[] args) {
        RPC.Builder builder = new RPC.Builder(new Configuration());
        builder.setPort(9999).setProtocol(Protocol.class).setBindAddress("localhost").setInstance(new ProtocolImpl()) ;
        try{
            RPC.Server server = builder.build();
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
