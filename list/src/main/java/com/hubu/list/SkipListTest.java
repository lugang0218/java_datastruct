package com.hubu.list;
import com.hubu.list.util.Asserts;

import java.io.*;

public class SkipListTest {
    public static void main(String[] args) {
        SkipList<Integer, Integer> list = new SkipList<>();
        for (int i = 1; i <= 500; i++) {
            list.put(i, i);
        }
        String filePath = "d:\\data2\\skipList.dat";
        writeList(filePath, list);
        readList(filePath);
    }
    public static void readList(String filePath){
        ObjectInputStream objectInputStream=null;
        InputStream inputStream=null;
        try {
            inputStream=new FileInputStream(filePath);
            try {
                objectInputStream=new ObjectInputStream(inputStream);
                try {
                    SkipList<Integer,Integer>  list= (SkipList<Integer, Integer>) objectInputStream.readObject();
                    for(int i=100;i<=500;i++){
                        System.out.println(list.get(i));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static<K,V> void writeList(String filePath,SkipList<K,V> list){
        ObjectOutputStream objectOutputStream=null;
        OutputStream outputStream=null;
        try {
            outputStream=new FileOutputStream(filePath);
            try {
                objectOutputStream=new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(list);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(objectOutputStream!=null){
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(outputStream!=null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
