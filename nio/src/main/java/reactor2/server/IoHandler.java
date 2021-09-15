package reactor2.server;
import reactor2.core.IoState;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
public class IoHandler extends AbstractHandler {
    private Selector selector;
    private IoState state=IoState.READ;
    private static final int ONE_MB=1024*1024;
    private ByteBuffer readBuffer=ByteBuffer.allocate(ONE_MB);
    private ByteBuffer writeBuffer=ByteBuffer.allocate(ONE_MB);
    private SocketChannel socketChannel;
    private SelectionKey event;
    public IoHandler(Selector selector,SocketChannel socketChannel) {

        this.selector=selector;
        this.socketChannel=socketChannel;

        try {
            this.socketChannel.configureBlocking(false);
            SelectionKey event = this.socketChannel.register(selector, 0);
            this.event=event;
            event.interestOps(SelectionKey.OP_READ);
            event.attach(this);
            selector.wakeup();
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
            System.out.println("写事件触发");
        }
        //处理读事件
        else if(state==IoState.READ){
            handleRead();
        }
    }
    public void handleRead(){
        readBuffer.clear();
        try {
            int read = socketChannel.read(readBuffer);
            if(read>0){
                System.out.println(String.format("收到来自客户端端的消息: %s", new String(readBuffer.array())));
                event.interestOps(SelectionKey.OP_WRITE);
                state = IoState.WRITE;
            }
            else{
                event.cancel();
                socketChannel.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
