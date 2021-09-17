package reactor3.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    public Acceptor(Selector selector,ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel=serverSocketChannel;
        this.selector = selector;
    }
    // 处理连接事件
    @Override
    public void run() {
        try {

            System.out.println("有连接过来建立");
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            //返回一个ioEvent事件，创建对应的IoHandler处理器处理读写事件
            SelectionKey ioEvent = socketChannel.register(selector, 0, null);
            AsyncHandler ioHandler = new AsyncHandler(socketChannel,selector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
