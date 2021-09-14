package server2;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
public class WorkThread implements Runnable {
    private Selector ioSelector;
    public WorkThread(Selector ioSelector){
        this.ioSelector =ioSelector;
    }
    @Override
    public void run() {
        while(true){
            try {
                ioSelector.select();
                Set<SelectionKey> keys = ioSelector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while(iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if(next.isReadable()){
                        System.out.println("可读事件发生");
                        try{
                            SocketChannel channel = (SocketChannel) next.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(4);
                            int read = channel.read(buffer);
                            if (read == -1) {
                                next.cancel();
                            } else {
                                buffer.flip();
                                CharBuffer decode = Charset.defaultCharset().decode(buffer);
                                System.out.println(decode);
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                            next.cancel();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
