package com.hubu.count2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
public class FileSystemTest {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS","hdfs://node1:8020");
        System.setProperty("HADOOP_USER_NAME","root");
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(configuration);
            fileSystem.mkdirs(new Path("/hahaha"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(fileSystem != null){
                    fileSystem.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
