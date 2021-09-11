import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
public class ChatServer {
    AcceptorHandler acceptorHandler=null;
    private String ipAddr;
    private int port;
    public ChatServer(int port,String ipAddr){
        this.ipAddr=ipAddr;
        this.port=port;
    }
    private ServerSocket initServerSocket(){
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket();
            serverSocket.bind(new InetSocketAddress(this.ipAddr,this.port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverSocket;
    }
    public void start(){
        if(acceptorHandler==null){
            ServerSocket serverSocket=initServerSocket();
            acceptorHandler=new AcceptorHandler(serverSocket);
            acceptorHandler.start();
        }
    }

    public static void main(String[] args) {
        ChatServer server=new ChatServer(9999,"localhost");
        server.start();
    }
}
