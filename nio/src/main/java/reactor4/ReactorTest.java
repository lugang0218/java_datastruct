package reactor4;

import reactor4.server.MainReactor;
import sun.nio.ch.WindowsSelectorProvider;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;

public class ReactorTest {
    public static void main(String[] args) {
        MainReactor mainReactor =new MainReactor();
        Thread thread=new Thread(mainReactor);
        thread.start();
    }
}
