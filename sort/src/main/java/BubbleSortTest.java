import java.util.Arrays;
public class BubbleSortTest {
    public static void main(String[] args) {
        BubbleSort<Integer> sort=new BubbleSort();
        Integer array[]=new Integer[]{5,4,3,2,1};
        sort.sort(array,null);
        System.out.println(Arrays.toString(array));
    }
}
