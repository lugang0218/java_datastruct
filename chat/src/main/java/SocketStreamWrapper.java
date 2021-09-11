import java.net.Socket;

public class SocketStreamWrapper{
    
    private Socket socket;
    
    
    private StreamWrapper streamWrapper;
    
    
    public SocketStreamWrapper(Socket socket,StreamWrapper streamWrapper){
        this.socket=socket;
        
        this.streamWrapper=streamWrapper;
    }
}
