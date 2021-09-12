package observer.thread;

public class Client {
    public static void main(String[] args){
        Observer observer=new Observer();
        SubjectRunnable task=new SubjectRunnableImpl();
        observer.setSubjectRunnable(task);
        task.addListener(observer);
        Thread t=new Thread(task);
        t.start();
    }
}
