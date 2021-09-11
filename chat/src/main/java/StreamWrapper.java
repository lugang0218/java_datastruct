import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamWrapper{
    
    private InputStream inputStream;
    
    private OutputStream outputStream;
    
    
    private BufferedReader bufferedReader;
    
    
    private BufferedWriter bufferedWriter;
    
    
    public StreamWrapper(InputStream inputStream,OutputStream outputStream,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        this.inputStream=inputStream;
        this.outputStream=outputStream;
        this.bufferedReader=bufferedReader;
        this.bufferedWriter=bufferedWriter;
    }
}