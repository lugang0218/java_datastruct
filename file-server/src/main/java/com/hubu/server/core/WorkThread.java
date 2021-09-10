package com.hubu.server.core;

import com.alibaba.fastjson.JSON;
import com.hubu.server.core.command.MkdirCommand;
import com.hubu.server.core.command.Param;
import com.hubu.server.core.handler.CommandHandler;
import com.hubu.server.core.handler.MkdirCommandHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class WorkThread extends Thread{
    private static Map<String, CommandHandler> commandHandlerMap=new HashMap<>();
    static{
        CommandHandler mkdirHandler=new MkdirCommandHandler();
        commandHandlerMap.put("mkdir",mkdirHandler);
    }
    private String threadName;
    private SocketStreamWrapper wrapper;
    public WorkThread(String threadName,SocketStreamWrapper wrapper){
        this.threadName=threadName;
        this.wrapper=wrapper;
    }

    public static void createDirectory(String currentPath,String filePath) {
        String createPath = currentPath + "//" + filePath;
        System.out.println("创建的文件夹是" + filePath);
        File file = new File(createPath);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            System.out.println("文件已经存在，不能创建");
        }
    }
    @Override
    public void run() {
        InStreamWrapper inStreamWrapper = wrapper.getInStreamWrapper();
        BufferedReader bufferedReader = inStreamWrapper.getBufferedReader();
        try {
            String s = bufferedReader.readLine();
            if(s!=null){
                MkdirCommand command = JSON.parseObject(s, MkdirCommand.class);
                CommandHandler commandHandler = commandHandlerMap.get(command.getType());
                List<String> list = commandHandler.handleCommand(s);

                for (Param<String, String> param : command.getParams()) {
                    String fileName = param.getValue();
                    createDirectory("d://data2", fileName);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}