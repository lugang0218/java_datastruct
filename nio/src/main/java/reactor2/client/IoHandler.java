package reactor2.client;

import reactor2.core.IoState;
import reactor2.server.AbstractHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.UUID;

public class IoHandler extends AbstractHandler {

    enum IoState{
        READ,WRITE
    }
    private IoState state= IoState.WRITE;
    private Selector selector;
    private static final int ONE_MB=1024*1024;
    private ByteBuffer readBuffer=ByteBuffer.allocate(ONE_MB);
    private ByteBuffer writeBuffer=ByteBuffer.allocate(ONE_MB);
    private SelectionKey event;
    private SocketChannel socketChannel;
    public IoHandler(Selector selector,SocketChannel socketChannel) {
        this.socketChannel=socketChannel;
        this.selector=selector;
        try {
            this.socketChannel.configureBlocking(false);
            SelectionKey event= this.socketChannel.register(selector, 0);
            this.event=event;
            event.interestOps(SelectionKey.OP_WRITE);
            event.attach(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * 处理读写事件
     */
    @Override
    public void handler() {
        //处理写事件
        if(state==IoState.WRITE){
            handleWrite();
        }
        //处理读事件
        else if(state==IoState.READ){
            handleRead();
        }
    }
    /**
     *
     * 写事件
     */
    public void handleWrite(){
        System.out.println("开始发送消息");
        String message= UUID.randomUUID().toString()+"客户端消息";
        writeBuffer.put(message.getBytes());
        try {
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
            //切换为读状态
            event.interestOps(SelectionKey.OP_READ);
            state=IoState.READ;
            System.out.println("消息已经发送出去");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 处理读事件
     */
    public void handleRead(){

    }
}
