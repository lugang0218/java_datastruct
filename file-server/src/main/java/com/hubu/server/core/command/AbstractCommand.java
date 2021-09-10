package com.hubu.server.core.command;




import java.util.*;
public interface AbstractCommand {
    String getType();


    
    List<Param<String, String>> getParams();
}
