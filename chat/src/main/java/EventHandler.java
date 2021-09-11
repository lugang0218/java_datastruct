public class EventHandler extends Thread{
    private SocketStreamWrapper socketStreamWrapper;
    /**
     * 缓冲区
     */
    private byte[] buffer=new byte[1024*1024];

    @Override
    public void run() {
        while(true){
            /**
             * 循环处理客户端的消息
             */
            handleMessage();
        }
    }
    //处理消息
    public void handleMessage(){

    }
}
