package com.hubu.server.core.handler;


import java.util.*;
public class MkdirCommandHandler implements CommandHandler {
    /**
     * 处理新建文件夹的命令处理器
     * @param command
     */
    public void checkCommand(String command){
        if(command==null||command==""){
            throw new RuntimeException("command can not be null");
        }
    }

    public String[] splitCommand(String command){
        String[] value = command.split(" ");
        return value;
    }
    @Override
    public List<String> handleCommand(String command) {
        checkCommand(command);
        String[] result = splitCommand(command);
        List<String> list=new ArrayList<>();
        for(String item:result){
            list.add(item);
        }
        return list;
    }
}
