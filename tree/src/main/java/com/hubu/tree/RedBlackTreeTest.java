package com.hubu.tree;
import java.io.*;
public class RedBlackTreeTest {
    static class Person implements Serializable{
        String id;
        String name;
        String address;
        String phone;
        String email;
        String qq;
        @Override
        public String toString() {
            return "Person{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", qq='" + qq + '\'' +
                    '}';
        }
    }
    public static void main(String[] args) {
        RedBlackTree<Integer,Person> redBlackTree=new RedBlackTree<>(null);
        String id="100001id";
        String name="zhangsan";
        String address="贵州省毕节市七星关区";
        String phone="1830857089";
        String email="1355755081@qq.com";
        String qq="1355755081";
        for(int i=0;i<1000000;i++){
            Person person=new Person();
            person.id=id+i;
            person.name=name+i;
            person.address=address+i;
            person.phone=phone+i;
            person.qq= qq+i;
            redBlackTree.put((i+1),person);
        }
        String filePath="d:\\data2\\redBlackTree.dat";
        writeList(filePath,redBlackTree);
        RedBlackTree<Object, Object> rTree = readList(filePath);
        rTree.preOrder();
    }

    public static<K,V> RedBlackTree<K,V> readList(String filePath){
        ObjectInputStream objectInputStream=null;
        InputStream inputStream=null;
        RedBlackTree<K,V> redBlackTree=null;
        try {
            inputStream=new FileInputStream(filePath);
            try {
                objectInputStream=new ObjectInputStream(inputStream);
                try {
                    redBlackTree= (RedBlackTree<K, V>) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return redBlackTree;
    }
    public static<K,V> void writeList(String filePath,RedBlackTree<K,V> tree){
        ObjectOutputStream objectOutputStream=null;
        OutputStream outputStream=null;
        try {
            outputStream=new FileOutputStream(filePath);
            try {
                objectOutputStream=new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(tree);
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
