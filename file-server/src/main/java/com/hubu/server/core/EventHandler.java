package com.hubu.server.core;
import com.alibaba.fastjson.JSON;
import com.hubu.server.core.command.MkdirCommand;
import com.hubu.server.core.handler.CommandHandler;
import com.hubu.server.core.handler.AbstractFileHandler;
import com.hubu.server.core.handler.MkdirCommandHandler;
import com.hubu.server.core.handler.MkdirFileHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class EventHandler extends Thread{
    private static Map<String, CommandHandler> commandHandlerMap=new HashMap<>();
    private static Map<CommandHandler, AbstractFileHandler> map=new HashMap<>();
    static{
        CommandHandler mkdirHandler=new MkdirCommandHandler();
        commandHandlerMap.put("mkdir",mkdirHandler);
        map.put(mkdirHandler,new MkdirFileHandler());
    }
    private String threadName;
    private SocketStreamWrapper wrapper;
    public EventHandler(String threadName, SocketStreamWrapper wrapper){
        this.threadName=threadName;
        this.wrapper=wrapper;
    }
    @Override
    public void run() {
        handleMessage();
    }
    public void handleMessage(){
        System.out.println("开始处理函数");
        while(true){
            InStreamWrapper inStreamWrapper = wrapper.getInStreamWrapper();
            BufferedReader bufferedReader = inStreamWrapper.getBufferedReader();
            InputStream inputStream = inStreamWrapper.getInputStream();
            byte buffer[]=new byte[1024];
            try {
                String s = bufferedReader.readLine();
                System.out.println("读取到的数据是:" + s);
                if (s != null) {
                    MkdirCommand command = JSON.parseObject(s, MkdirCommand.class);
                    CommandHandler commandHandler = commandHandlerMap.get(command.getType());
                    List<String> list = commandHandler.handleCommand(s);
                    AbstractFileHandler fileHandler = map.get(commandHandler);
                    fileHandler.handler(list);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}