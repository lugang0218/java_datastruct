package reactor2.server;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public abstract class AbstractHandler {
    public abstract void handler() throws IOException;
}
