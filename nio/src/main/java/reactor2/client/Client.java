package reactor2.client;
import reactor2.server.AbstractHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
public class Client {
    private int serverPort;
    private String serverAddress;
    private Selector selector;
    private SocketChannel socketChannel;
    public Client(int serverPort,String serverAddress){
        this.serverAddress=serverAddress;
        this.serverPort=serverPort;
        try {
            selector=Selector.open();
            socketChannel=SocketChannel.open();
            socketChannel.configureBlocking(false);
            SelectionKey event = socketChannel.register(selector, SelectionKey.OP_CONNECT);
            AbstractHandler handler = new ConnectorHandler(selector,socketChannel);

            event.attach(handler);
            //建立连接
            socketChannel.connect(new InetSocketAddress(this.serverAddress,this.serverPort));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        while(!Thread.interrupted()){
            try {
                selector.select();
                Set<SelectionKey> events = selector.selectedKeys();
                Iterator<SelectionKey> eventIterator = events.iterator();
                while(eventIterator.hasNext()){
                    dispatcher(eventIterator.next());
                }
                events.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void dispatcher(SelectionKey event){
        if(event!=null){
            AbstractHandler handler=(AbstractHandler) event.attachment();
            try {
                handler.handler();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Client client=new Client(9090,"localhost");
        client.start();
    }
}
