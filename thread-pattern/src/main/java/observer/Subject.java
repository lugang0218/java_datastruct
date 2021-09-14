package observer;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * 主题 状态
 */
public class Subject {
    private int state;
    private List<Observer> observerList;

    public Subject(int state){
        this.state=state;
        observerList=new ArrayList<>();
    }

    public void removeObserver(Observer observer){
        observerList.remove(observer);
    }

    public void addObserver(Observer observer){
        observerList.add(observer);
    }


    public void setState(int state){
        if(this.state!=state){
            this.state=state;
            notifyAllObserver(state);
        }
    }

    private void notifyAllObserver(int state) {
        if(observerList!=null&&observerList.size()>0){
            for(Observer observer:observerList){
                observer.subSquere(state);
            }
        }
    }
}
