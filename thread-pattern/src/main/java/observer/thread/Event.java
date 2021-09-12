package observer.thread;
public class Event {
    private ThreadState threadState;
    private Message message;

    private Thread thread;
    public void setThreadState(ThreadState threadState) {
        this.threadState = threadState;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setThead(Thread thread) {
        this.thread=thread;
    }


    enum ThreadState{
        RUNNING,DONE,ERROR;
    }
    static class Message{
        Throwable throwable;
    }

    public ThreadState getThreadState() {
        return threadState;
    }

    public Thread getThread() {
        return thread;
    }

    public Message getMessage() {
        return message;
    }

}
