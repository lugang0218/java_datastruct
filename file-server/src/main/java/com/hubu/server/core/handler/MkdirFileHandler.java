package com.hubu.server.core.handler;

import com.hubu.server.core.command.Param;

import java.io.File;
import java.util.List;

/**
 *
 *
 * Mkdir事件处理器
 */
public class MkdirFileHandler extends AbstractFileHandler{


    private String currentPath;


    public void setCurrentPath(String currentPath){
        this.currentPath=currentPath;
    }
    @Override
    public void handler(List<String> list) {
        createDirectory(list);
    }

    public void createDirectory(List<String> paramList){
        for(String item:paramList){
            String filePath=this.currentPath+"\\"+item;
            File file=new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            else{
                System.out.println("目录"+item+"已经存在");
            }
        }
    }
}
