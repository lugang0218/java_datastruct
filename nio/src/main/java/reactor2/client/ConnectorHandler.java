package reactor2.client;

import reactor2.server.AbstractHandler;
import reactor2.server.IoHandler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ConnectorHandler extends AbstractHandler {
    private SocketChannel socketChannel;
    private Selector selector;
    public ConnectorHandler(Selector selector,SocketChannel socketChannel) {
        this.selector=selector;
        this.socketChannel=socketChannel;
    }
    @Override
    public void handler() throws IOException {
        if(socketChannel.finishConnect()){
            AbstractHandler ioHandler=new reactor2.client.IoHandler(selector,socketChannel);
        }
        //处理连接事件
    }
}
