package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
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
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ,null);

                }else if(next.isReadable()){
                    try{
                        SocketChannel socketChannel = (SocketChannel) next.channel();
                        ByteBuffer buffer=ByteBuffer.allocate(10);
                        int read = socketChannel.read(buffer);
                        //如果读取完毕，就将事件从集合中删除
                        if(read==-1){
                            next.cancel();
                        }
                        else{
                            buffer.flip();
                            System.out.println(buffer.array().toString());
                        }
                    }catch (IOException e){
                        System.out.println("连接下线");
                        next.cancel();
                    }
                }
            }
        }
    }
}
