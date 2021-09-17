package future;
public class Main {
    public static void main(String[] args) {
        FutureService<String> futureService=new FutureService<>();
        Future<String> future = futureService.submit(() -> {
            Thread.sleep(2000);
            return "hello world";
        });
        future.addListener(new Listener() {
            @Override
            public void operation(Future future) {
                Object o = future.get();
                System.out.println(o);
            }
        });
    }
}