package queue;

import java.util.UUID;

public class Client extends Thread{

    private int count=0;
    private final  String name;
    private final MessageQueue messageQueue;

    public Client(final MessageQueue messageQueue,final String name){
        this.messageQueue=messageQueue;
        this.name=name;
    }
    /**
     *
     * 放入1000消息之后退出程序
     */
    @Override
    public void run() {
        while(count<10){
            count++;
            String message= UUID.randomUUID().toString();
            messageQueue.offerMessage(message);
        }
    }
}
