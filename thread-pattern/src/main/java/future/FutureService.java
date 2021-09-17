package future;
import java.util.concurrent.Callable;
public class FutureService<V>{
    public Future<V> submit(Callable<V> task){
        AsyncFuture<V> asyncFuture=new AsyncFuture<>(task);
        new Thread(asyncFuture).start();
        return asyncFuture;
    }
}
