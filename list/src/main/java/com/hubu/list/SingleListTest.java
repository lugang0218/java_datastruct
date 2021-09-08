package com.hubu.list;
import java.io.*;
public class SingleListTest {
    public static void main(String[] args) {
//        String filePath="d:\\data2\\list.dat";
//        SingleList<Integer> list=new SingleList<>(null);
//        for(int i=1;i<=10000;i++){
//            list.add(i);
//        }

        SingleList<Integer> list=new SingleList<>(null);
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(15);
        list.print();

    }
    public static void readList(String filePath){
        ObjectInputStream objectInputStream=null;
        InputStream inputStream=null;
        try {
            inputStream=new FileInputStream(filePath);
            try {
                objectInputStream=new ObjectInputStream(inputStream);
                try {
                    SingleList<Integer>  list= (SingleList<Integer>) objectInputStream.readObject();
                    list.show();
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
    public static<T> void writeList(String filePath,SingleList<T> list){
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
