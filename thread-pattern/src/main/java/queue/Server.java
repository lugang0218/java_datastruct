package queue;
public class Server extends Thread{
    private final  String name;
    private volatile  boolean flag=true;
    private final MessageQueue messageQueue;
    public Server(final MessageQueue messageQueue,final  String name){
        this.name=name;
        this.messageQueue=messageQueue;
    }
    @Override
    public void run() {
        ServerMonitorThread serverMonitorThread =new ServerMonitorThread(this);


        serverMonitorThread.start();
        while(flag&&!Thread.currentThread().isInterrupted()){
            try {
                sleep(50);
                String message = messageQueue.pollMessage();
                System.out.println("拿到消息回来");
                System.out.println("消费的消息是+"+message);
            } catch (InterruptedException e) {
                System.out.println("睡眠过程中被打断");
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
    /**
     *
     * 该线程用来监控服务线程的信息
     */

    class ServerMonitorThread extends Thread{

        /**
         * 被监控的线程
         */
        private Thread serverThread;



        public ServerMonitorThread(Thread thread){
            this.serverThread=thread;
        }
        @Override
        public void run() {
            while(true){
                if(flag==false){
                    serverThread.interrupt();
                }
            }
        }
    }
}
