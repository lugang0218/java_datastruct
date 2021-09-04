import java.util.Comparator;

public class SearchUtil<T> {
    private  Comparator<T> comparator;
    public SearchUtil(Comparator<T> comparator){
        this.comparator=comparator;
    }


    /**
     *
     *
     *
     * 二分查找
     */


    public int binarySearch(T value,T [] array) {
        if (value == null || array == null || array.length == 0) {
            return -1;
        }
        int low = 0;
        int high = array.length - 1;
        int mid = 0;
        int compare = 0;
        while (low <= high) {
            mid = (low + high) / 2;
            compare = comparator.compare(value, array[mid]);
            if (compare > 0) {
                low = mid + 1;
            } else if (compare == 0) {
                return mid;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }



    private void check(T value,T array[]){
        if(value==null||array==null||array.length==0){
            throw new RuntimeException("参数错误");
        }
    }

    public void search(T value,T array[]){

    }



    public static void main(String[] args) {
        SearchUtil<Integer> searchUtil=new SearchUtil<Integer>((value1,value2)->{



            return value1-value2;
        });

        int i = searchUtil.binarySearch(6, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

    }
}
