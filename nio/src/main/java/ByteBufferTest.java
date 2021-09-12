import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class ByteBufferTest {
    public static void main(String[] args) {
        readFile("d:\\data2\\hello.txt");
        writeFile("d:\\data2\\hello2.txt");
    }
    public static void readFile(String filePath){
        FileInputStream fileInputStream=null;
        ByteBuffer buffer=ByteBuffer.allocate(10);
        try {
            fileInputStream=new FileInputStream(filePath);
            FileChannel channel = fileInputStream.getChannel();
            try {
                int read = 0;
                while(true){
                    buffer.clear();
                    read=channel.read(buffer);
                    if(read==-1){
                        break;
                    }
                    String result=new String(buffer.array());
                    System.out.println(result);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void writeFile(String filePath){
        FileOutputStream fileOutputStream=null;
        ByteBuffer buffer=ByteBuffer.allocate(10);
        try {
            fileOutputStream=new FileOutputStream(filePath);
            FileChannel channel = fileOutputStream.getChannel();
            buffer.put((byte)'h');
            buffer.put((byte)'e');
            buffer.put((byte)'l');
            buffer.put((byte)'l');
            buffer.put((byte)'w');
            buffer.flip();
            try {
                channel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
