package future;


/**
 *
 *
 * @param <V>
 */

import java.util.List;
public interface Future <V>{

    boolean isDone();

    boolean isSuccess();

    boolean isError();

    V get();


    void addListener(Listener listener);


    void removeListener(Listener listener);


    List<Listener<V>> getListeners();
}
