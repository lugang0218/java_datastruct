import com.hubu.tree.AvlTree;
import com.hubu.tree.BinarySearchTree;
public class TreeSetTest {
    public static void main(String[] args) {
//        TreeSet<Integer> set = new TreeSet<>();
//        for(int i=0;i<100000;i++){
//            set.add((i+1));
//        }
//        System.out.println("添加完毕");
//        System.out.println(set.contains(10000000));
//        testBinarySearchTree();
        testAvlTree();
    }
    public static void testBinarySearchTree(){
        BinarySearchTree<Integer> tree=new BinarySearchTree<>(null,null);
        for(int i=0;i<100000;i++){
            tree.add((i+1));
        }
    }


    public static void testAvlTree(){
        AvlTree<Integer> tree=new AvlTree<>(null,null);
        for(int i=0;i<100000;i++){
            tree.add((i+1));
        }
    }
}
