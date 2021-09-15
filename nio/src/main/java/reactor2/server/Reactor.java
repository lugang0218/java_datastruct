package reactor2.server;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
public class Reactor {
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT=9090;
    private Selector selector;
    private int port;
    public Reactor(){
        this(PORT);
    }
    public Reactor(int port){
        this.port=port;
        try {
            serverSocketChannel=ServerSocketChannel.open();
            selector=Selector.open();
            serverSocketChannel.configureBlocking(false);
            SelectionKey key= serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, null);
            serverSocketChannel.bind(new InetSocketAddress("localhost",port));
            //初始事件，为这个事件绑定一个处理器
            AbstractHandler acceptorHandler=new AcceptorHandler(selector,serverSocketChannel);

            /**
             * 为当前事件添加一个附件
             */
            key.attach(acceptorHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        while(!Thread.interrupted()){
            System.out.println("服务器启动在:"+port+"端口监听");
            try {
                selector.select();
                Set<SelectionKey> events = selector.selectedKeys();
                Iterator<SelectionKey> eventIterator = events.iterator();
                while(eventIterator.hasNext()){
                    dispatcher(eventIterator.next());
                }
                System.out.println("清楚集合");
                events.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void dispatcher(SelectionKey event) {
        if(event!=null){
            AbstractHandler handler=(AbstractHandler) event.attachment();
            try {
                handler.handler();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
