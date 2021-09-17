package reactor3.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable{
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT=8080;
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
            Acceptor acceptor=new Acceptor(selector,serverSocketChannel);
            key.attach(acceptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                System.out.println("开始等待客户端的连接");
                selector.select();
                System.out.println("有客户端过来连接");
                Set<SelectionKey> events = selector.selectedKeys();
                Iterator<SelectionKey> iterator = events.iterator();
                while(iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    dispatch(next);
                }
                events.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 线程分配器
     * @param eventKey
     */
    public void dispatch(SelectionKey eventKey){
        Runnable r = (Runnable) (eventKey.attachment());
        if(r!=null){
            System.out.println("来到这儿");
            r.run();
        }
    }
}
