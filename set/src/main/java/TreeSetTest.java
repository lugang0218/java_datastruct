import com.hubu.util.ArrayUtils;
public class TreeSetTest {
    public static void main(String[] args) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        Integer array[] = ArrayUtils.randomInteger(1000000, 1, 100);
        for (int i = 0; i < 1000000; i++) {
            treeSet.add(array[i]);
        }
        System.out.println(treeSet.size());
        System.out.println(treeSet.contains(1));
    }
}
