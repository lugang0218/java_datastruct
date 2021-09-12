package immutable;

public class PersonTask implements Runnable {
    private Person person=new Person(21,"lugang");
    @Override
    public void run() {
        System.out.println(person);
    }
    public static void main(String[] args) {
        PersonTask task= new PersonTask();
        Thread t1=new Thread(task);
        Thread t2=new Thread(task);
        Thread t3=new Thread(task);
        t1.start();
        t2.start();
        t3.start();
    }
}
