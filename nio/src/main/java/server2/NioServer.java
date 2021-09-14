package server2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        while(true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey next = iterator.next();
                iterator.remove();
                if(next.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) next.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    Selector workSelector=Selector.open();
                    WorkThread workThread=new WorkThread(workSelector);
                    socketChannel.configureBlocking(false);
                    socketChannel.register(workSelector,SelectionKey.OP_READ,null);
                    Thread t=new Thread(workThread);
                    t.start();
                }
            }
        }
    }
}
