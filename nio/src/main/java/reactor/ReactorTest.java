package reactor;

import reactor.server.Reactor;

public class ReactorTest {
    public static void main(String[] args) {
        Reactor reactor=new Reactor();
        Thread thread=new Thread(reactor);
        thread.start();
    }
}
