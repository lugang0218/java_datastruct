import java.util.Comparator;
public abstract class AbstractSort<T> {
    private long beginTime;
    private long endTime;
    protected Comparator<T> comparator;
    public AbstractSort(Comparator<T> comparator){
        this.comparator=comparator;
    }
    public void begin(){
        beginTime=System.currentTimeMillis();
        System.out.println("开始排序时间"+beginTime);
    }
    public abstract void sort(T [] array);
    public void end(){
        endTime=System.currentTimeMillis();
        System.out.println("结束排序时间:"+endTime);


        System.out.println("排序耗时为:"+(endTime-beginTime)+"毫秒");
    }


    public void toSort(T array[]){
        //第一步 打印开始排序的时间
        begin();


        //真正的sort
        sort(array);

        //第三步 打印结束时间

        end();

    }
}
