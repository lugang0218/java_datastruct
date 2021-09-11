import java.io.IOException;
import java.net.ServerSocket;

public class AcceptorHandler extends Thread{
    private ServerSocket serverSocket=null;




    public AcceptorHandler(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }
    @Override
    public void run() {
        //循环处理客户端的连接
        while(true){
            handleAcceptor();
        }
    }
    /**
     *
     *
     * 处理连接
     */
    public void handleAcceptor(){
        while(true){
            System.out.println("服务器开始监听");
            try {
                serverSocket.accept();
                System.out.println("有客户端的连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
