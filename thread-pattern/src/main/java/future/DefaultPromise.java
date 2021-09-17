package future;

import java.util.ArrayList;
import java.util.List;

public class DefaultPromise<V> implements Promise<V>{

    private List<Listener<V>> listenerList=new ArrayList<>();
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
        return null;
    }

    @Override
    public void addListener(Listener listener) {

    }

    @Override
    public void removeListener(Listener listener) {

    }

    @Override
    public List<Listener<V>> getListeners() {
        return null;
    }

    @Override
    public Promise<V> setSuccess(V result) {
        return null;
    }

    @Override
    public boolean trySuccess(V result) {
        return false;
    }
}
