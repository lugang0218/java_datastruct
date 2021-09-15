package reactor2.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AcceptorHandler extends AbstractHandler{




    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    public AcceptorHandler(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector=selector;
        this.serverSocketChannel=serverSocketChannel;
    }
    @Override
    public void handler() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            SelectionKey event = socketChannel.register(selector, 0);
            AbstractHandler ioHandler=new IoHandler(selector,socketChannel);
            event.attach(ioHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
