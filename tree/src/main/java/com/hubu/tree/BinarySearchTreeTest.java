package com.hubu.tree;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
public class BinarySearchTreeTest {
    static class MyComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1-o2;
        }
    }
    public static BinarySearchTree<Integer> readObject(){

        BinarySearchTree<Integer> tree=null;
        try {
            InputStream inputStream=new FileInputStream("D:\\data2\\hello.dat");
            try {
                ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
                try {
                    tree = (BinarySearchTree<Integer>) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    if(objectInputStream!=null){
                        objectInputStream.close();
                    }
                    if(inputStream!=null){
                        inputStream.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static void writeBinarySearchTree(){
        BinarySearchTree<Integer> tree=new BinarySearchTree<>(new MyComparator());
        List<Integer> list = Arrays.asList(4, 2, 7, 1, 3,6,5);
        tree.build(list);
        tree.levelOrder();
        System.out.println(tree.height());
        ObjectOutputStream objectOutputStream=null;
        try {
            OutputStream outputStream=new FileOutputStream("D:\\data2\\hello.dat");
            try {
                objectOutputStream = new ObjectOutputStream(outputStream);
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
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree2=readObject();
        tree2.inOrder();
    }
}
