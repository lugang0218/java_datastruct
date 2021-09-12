package com.hubu.file;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
public class DirectFileWrite {
    /**
     * 写入的位置
     */
    private int position;


    private String filePath;
    public DirectFileWrite(String filePath,int position){
        this.filePath=filePath;
        this.position=position;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public void writeFile(String content) {
        File file = new File(filePath);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            MappedByteBuffer bufferMap = null;
            FileChannel channel = randomAccessFile.getChannel();
            long size = 1024;
            try {
                bufferMap = channel.map(FileChannel.MapMode.READ_WRITE, 0, content.length());
                System.out.println("position="+this.position);
                bufferMap.position(this.position);
                bufferMap.put(content.getBytes());
                this.position = bufferMap.position();
                bufferMap.flip();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        DirectFileWrite directFileWrite=new DirectFileWrite("d:\\data2\\file.txt",0);
        directFileWrite.writeFile("hello world\n");
        directFileWrite.writeFile("hello world\n");
        directFileWrite.writeFile("hello world\n");
    }
}
