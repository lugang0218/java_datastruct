public class BubbleSortTest {
    public static void main(String[] args) {
        AbstractSort<Integer> sort=new BubbleSort(null);
        Integer array[]=new Integer[]{5,4,3,2,1};
        sort.toSort(array);
    }
}
