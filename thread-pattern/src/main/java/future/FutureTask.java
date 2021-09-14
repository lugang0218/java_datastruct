package future;
import java.util.concurrent.Callable;
public class FutureTask<V> implements Future<V>,Runnable{

    private static final int RUN=0;


    private static final int NORMAL=1;

    private static final int CANCEL=2;

    private volatile  int state;
    private Callable<V> task;
    private Thread thread;

    private V result;
    public FutureTask(Callable<V> task){
        this.task=task;
        try {
            V value=task.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            result=task.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public V get() {
        return null;
    }
}
