import com.hubu.tree.RedBlackTree;
public class TreeSet<T>{
    private RedBlackTree<T,Object> redBlackTree;
    private  final Object o=new Object();
    public void add(T key){
        if(redBlackTree==null){
            redBlackTree=new RedBlackTree<>(null);
        }
        redBlackTree.put(key,o);
    }
    public boolean contains(T t){
        return redBlackTree.containsKey(t);
    }

    public int height(){
        return redBlackTree.height();
    }
}