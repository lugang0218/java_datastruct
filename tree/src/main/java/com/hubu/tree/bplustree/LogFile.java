package com.hubu.tree.bplustree;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class LogFile {
    private Lock lock = new ReentrantLock();
    private File file;
    public LogFile(String filePath) {
        file = new File(filePath);
    }
    public void writeWithLock(String key,String value) {
        try {
            lock.lock();
            try (RandomAccessFile fileHandle = new RandomAccessFile(file, "rw")) {
                fileHandle.seek(file.length());
                byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
                long keyLength = keyBytes.length;
                long valueLength = valueBytes.length;
                fileHandle.seek(file.length());
                fileHandle.writeLong(keyLength);
                fileHandle.writeLong(valueLength);
                fileHandle.writeBytes(key);
                fileHandle.writeBytes(value);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.unlock();
        }
    }
    public void write(String key,String value,boolean locked){
        if(locked){
            writeWithLock(key,value);
        }else{
            writeNoLock(key,value);
        }
    }
    public void writeNoLock(String key,String value){
        try(RandomAccessFile fileHandle = new RandomAccessFile(file,"rw")) {
            fileHandle.seek(file.length());
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
            long keyLength = keyBytes.length;
            long valueLength = valueBytes.length;
            fileHandle.seek(file.length());
            fileHandle.writeLong(keyLength);
            fileHandle.writeLong(valueLength);
            fileHandle.writeBytes(key);
            fileHandle.writeBytes(value);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<AbstractMap.SimpleEntry<String,String>> readAll(){
        List<AbstractMap.SimpleEntry<String,String>> stringEntryList= new ArrayList<>();
        try{
            lock.lock();
            try(RandomAccessFile fileHandle = new RandomAccessFile(file,"r")) {
                fileHandle.seek(0);
                while(fileHandle.getFilePointer() != file.length()){
                    long keyLength = fileHandle.readLong();
                    long valueLength = fileHandle.readLong();
                    byte keyBuffer[] = new byte[(int) keyLength];
                    byte valueBuffer[] = new byte[(int) valueLength];
                    fileHandle.read(keyBuffer);
                    fileHandle.read(valueBuffer);
                    String keyString = new String(keyBuffer);
                    String valueString = new String(valueBuffer);
                    System.out.println(keyString);
                    System.out.println(valueString);
                    AbstractMap.SimpleEntry<String,String> entry = new AbstractMap.SimpleEntry<>(keyString,valueString);
                    stringEntryList.add(entry);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }finally {
            lock.unlock();
        }
        return stringEntryList;
    }
}
