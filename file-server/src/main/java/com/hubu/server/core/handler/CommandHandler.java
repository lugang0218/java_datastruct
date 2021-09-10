package com.hubu.server.core.handler;
import java.util.*;
public interface CommandHandler {
    List<String> handleCommand(String command);
}
