import com.hubu.util.ArrayUtils;

public class BubbleSortTest {
    public static void main(String[] args) {
        AbstractSort<Integer> sort=new BubbleSort(null);
        Integer array[]=ArrayUtils.randomInteger(8000,1,80000);
        sort.toSort(array);
    }
}
