package queue;
import java.util.Queue;
public class MessageQueue {
    private final Queue<String> messageQueue;
    private final int maxSize;
    public MessageQueue(final int maxSize, final Queue<String> messageQueue){
        this.messageQueue=messageQueue;
        this.maxSize=maxSize;
    }
    public synchronized void offerMessage(String message){
        while(messageQueue.size()>=maxSize){
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("线程被打断");
                break;
            }
        }
        messageQueue.offer(message);
        this.notify();
    }
    /**
     *
     * 消费线程
     * @return
     */
    public synchronized String pollMessage(){
        while(messageQueue.size()<=0){
            try {
                System.out.println("线程wait住");
                this.wait();
            } catch (InterruptedException e) {
                /**
                 * 线程被打断之后 直接退出
                 */
                //wait中的线程被打断会被清除打断标记
                System.out.println(Thread.currentThread().isInterrupted());

                /**
                 * 直接打断该线程
                 */
                Thread.currentThread().interrupt();
                break;
            }
        }
        String message = messageQueue.poll();
        this.notify();
        return message;
    }
}
