package observer.thread;
import java.util.ArrayList;
/**
 * 线程主题
 */
public abstract class SubjectRunnable implements Runnable {


    protected ArrayList<Observer> observerList=new ArrayList<>();


    abstract void addListener(Observer observer);


    abstract void removeListener(Observer observer);


    void notifyAllListener(Event event){

        notifyListener(event);
        //通知所有监听者
    }


    abstract  void notifyListener(Event event);
}
