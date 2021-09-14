package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IoClient {
    public static void main(String[] args) {
        Socket socket=new Socket();
        try {
            socket.connect(new InetSocketAddress("localhost",8080));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
