package observer.thread;
import java.util.ArrayList;
public class SubjectRunnableImpl extends SubjectRunnable{
    @Override
    void addListener(Observer observer) {
        observerList.add(observer);
    }
    @Override
    void removeListener(Observer observer) {
        observerList.remove(observer);
    }
    @Override
    void notifyListener(Event event) {
        if(observerList!=null&&observerList.size()>0){
            for(Observer observer:observerList){
                observer.onEvent(event);
            }
        }
    }
    @Override
    public void run() {
        // 模拟运行

        System.out.println("线程开始运行");

        int i=100;

        Event event=new Event();
        try{

            event.setThreadState(Event.ThreadState.RUNNING);
            event.setMessage(null);
            event.setThead(Thread.currentThread());
            notifyAllListener(event);
            Thread.sleep(2000);
            int value=100/10;
            //业务运行
            event.setThreadState(Event.ThreadState.DONE);
            event.setMessage(null);
            notifyAllListener(event);
        }catch (Exception e){
            event.setThreadState(Event.ThreadState.ERROR);
            notifyAllListener(event);
        }
    }
}
