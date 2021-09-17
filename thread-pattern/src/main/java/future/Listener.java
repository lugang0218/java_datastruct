package future;

public interface Listener<V> {




    void operation(Future<V> future);
}
