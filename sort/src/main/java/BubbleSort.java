import java.util.Comparator;

/**
 * 冒泡排序实现
 */
public class BubbleSort<T> extends AbstractSort<T> {
    public BubbleSort(Comparator<T> comparator) {
        super(comparator);
    }

    //传入一个比较逻辑和一个数组


    @Override
    public void sort(T [] array){
        for(int i=0;i<array.length-1;i++){
            for(int j=0;j<array.length-1-i;j++){
                if(comparator!=null&&comparator.compare(array[j],array[j+1])>0){
                    T temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
                else if( ((Comparable<T>)array[j]).compareTo(array[j+1])>0){
                    T temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
        }
    }
}
