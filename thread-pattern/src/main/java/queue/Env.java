package queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Env {
    public static void main(String[] args) {
        Queue<String> queue=new LinkedList<>();
        MessageQueue messageQueue=new MessageQueue(100,queue);
        Client client=new Client(messageQueue,"client");
        Server server=new Server(messageQueue,"server");
        client.start();
        server.start();
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("主线程醒来了");
            server.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
