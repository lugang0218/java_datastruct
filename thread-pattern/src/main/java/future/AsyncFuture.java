package future;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
public class AsyncFuture<V> implements Future<V>,Runnable{
    private List<Listener<V>> listenerList=new ArrayList<>();
    private static Object lock=new Object();
    private enum ResultState{
        NEW,SUCCESS,EXCEPTION,ERROR
    }
    private ResultState resultState;
    private V result;
    private Callable<V> task;
    public AsyncFuture(Callable<V> task){
        this.task=task;
        resultState=ResultState.NEW;
    }
    @Override
    public boolean isDone() {
        return false;
    }
    @Override
    public boolean isSuccess() {
        return false;
    }
    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public V get() {
       return result;
    }
    @Override
    public void addListener(Listener listener) {
        listenerList.add(listener);
    }
    @Override
    public void removeListener(Listener listener) {
        if(listener!=null){
            listenerList.remove(listener);
        }
    }
    @Override
    public List<Listener<V>> getListeners() {
        return listenerList;
    }
    @Override
    public void run() {
        if(resultState!=ResultState.NEW){
            throw new RuntimeException("error");
        }
        try {
            resultState= ResultState.SUCCESS;
            V v = task.call();
            System.out.println(this);
            report(v);
            notifyAllListeners();
        } catch (Exception e) {
            resultState=ResultState.ERROR;
            e.printStackTrace();
        }
    }
    private void notifyAllListeners() {
        if(listenerList.size()>0){
            for(Listener<V> listener:listenerList){
                listener.operation(this);
            }
        }
    }
    private void report(V v) {
        if(resultState!=ResultState.SUCCESS){
            return ;
        }
        result=v;
    }
}
