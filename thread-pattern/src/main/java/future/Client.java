package future;

import java.util.concurrent.ExecutionException;

public class Client {


    public static void main(String[] args) {
        FutureTask<String> task=new FutureTask<>(()->{
            return "hello world";
        });


        Thread thread=new Thread(task);

        String s = task.get();
        System.out.println(s);

    }
}
