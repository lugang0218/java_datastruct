package com.hubu.file;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class FileSystem {
    private static int count=0;
    public static void dfsFile(String filePath){
        File file=new File(filePath);
        dfsFile(file);
        System.out.println("文件的个数是:"+count);
    }
    public static void createFile(String filePath,List<String> fileNames){
        File file=new File(filePath);
        //如果文件已经存在
        if(file.exists()){

        }else{
            file.mkdirs();
        }
        for(String fileName:fileNames){
            File itemFile=new File(filePath,fileName);
            if(!itemFile.exists()){
                itemFile.mkdir();
            }
        }
    }
    public static void updateFileName(String parentPath,String oldFileName,String newFileName){
        String oldFilePath=parentPath+"\\"+oldFileName;
        File file=new File(oldFilePath);
        if(file.exists()){
            String newFilePath=parentPath+"\\"+newFileName;
            File newFile=new File(newFilePath);
            file.renameTo(newFile);
        }
    }

    /**
     *
     *
     * 查询出当前文件夹下的所有文件 不包含目录
     * @param filePath
     */
    public static List<String> listAllFileFile(String filePath){
        List<String> fileNames=new ArrayList<>();
        File file=new File(filePath);
        if(file.exists()){
            File[] files = file.listFiles();
            for(File fileItem:files){
                String name = fileItem.getName();
                fileNames.add(name);
            }
        }
        return fileNames;
    }
    public static void dfsFile(File file){
        File[] files = file.listFiles();
        for(File item:files){
            if(item.isFile()){
                count++;
                System.out.println(item.getName());
            }
            else if(file.isDirectory()){
                dfsFile(item);
            }
        }
    }
    public static void main(String[] args) {
        listAllFileFile("D:\\data2");
    }
}
