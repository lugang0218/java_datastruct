package countdown;

public class MyCountDownLatch {


    private int count=0;
    private final int total;

    public MyCountDownLatch(final  int total){
        this.total=total;
    }
    public void countDown(){
        synchronized (this){
            count++;
        }
    }

    public void await(){
        synchronized (this){
            while(count!=total){
                this.await();
            }
        }
    }
}
