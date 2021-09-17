package future;

public interface Promise<V> extends  Future<V> {
    Promise<V> setSuccess(V result);


    boolean trySuccess(V result);

}
