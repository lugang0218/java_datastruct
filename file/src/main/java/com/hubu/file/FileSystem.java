package com.hubu.file;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

    public static  void bfsFile(File file){
        if(!file.exists()){
            throw new RuntimeException("file不存在");
        }
        Queue<File> fileQueue=new LinkedList<>();
        fileQueue.offer(file);
        while(!fileQueue.isEmpty()){
            File itemFile = fileQueue.poll();
            if(itemFile.isFile()){
                System.out.println(itemFile.getName());
            }
            else if(itemFile.isDirectory()){
                File[] files = itemFile.listFiles();
                if(files!=null&&files.length>0){
                    for(File item:files){
                        if(item.isFile()){
                            System.out.println(item.getName());
                        }
                        else if(item.isDirectory()){
                            fileQueue.offer(item);
                        }
                    }
                }
            }
        }
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
        String filePath="d:\\data2\\hello.txt";
        long index = fileWrite(filePath, "hello world", 0);
        System.out.println("index="+index);
    }
    /**
     *
     * @param filePath
     * @param content
     * @param index
     * @return
     */
    public static long fileWrite(String filePath, String content, int index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        MappedByteBuffer map;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, (long) 1024 * 1024 * 1024);
            map.position(index);
            map.put(content.getBytes());
            return map.position();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return 0L;
    }

    public static String fileRead(String filePath, long index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        MappedByteBuffer map;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, index);
            byte[] byteArr = new byte[10 * 1024];
            map.get(byteArr, 0, (int) index);
            return new String(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "";
    }

}
