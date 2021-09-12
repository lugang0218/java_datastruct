import sun.nio.ch.DirectBuffer;
import java.nio.ByteBuffer;
public class DirectoryByteBufferTest {
    public static void main(String[] args) {
        /**
         *
         * 分配直接内存
         */
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    }
}
