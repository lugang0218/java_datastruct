package com.hubu.tree.btree;
import java.util.ArrayList;
import java.util.Arrays;
public class Core {
    /**
     * 二分添加
     */
    public static void binaryAdd(Integer key, ArrayList<Integer> array){
        boolean isUpdate=false;
        int low=0;
        int high=array.size()-1;
        while(low<=high){
            int mid=(low+high)/2;
            if(key.compareTo(array.get(mid))<0){
                high=mid-1;
            }
            else if(key.equals(array.get(mid))){
                array.set(mid,key);
                isUpdate=true;
                break;
            }
            else{
                low=mid+1;
            }
        }
        if(!isUpdate){
            array.add(low,key);
        }

        System.out.println(Arrays.toString(array.toArray()));
    }

    /**
     *
     * 二分查找
     * @param key
     * @param array
     * @return
     */
    public static int binarySearch(int key, int []array){
        int low=0;
        int high=array.length-1;
        while(low<=high){
            int mid=(low+high)/2;
            if(key<array[mid]){
                high=mid-1;
            }
            else if(key==array[mid]){
                return array[mid];
            }
            else{
                low=mid+1;
            }
        }
        return -1;
    }
}



class A{

    Point p=new Point(100,200);
    public Point  func3(){
        return p;
    }

    void show(){
        System.out.println(p.x);
        System.out.println(p.y);
    }
}
class PointManager{
    public static void main(String[] args) {
        A a=new A();
        Point point = a.func3();
        point.x=200;
        point.y=1000;
        point=null;
        a.show();
    }
}

class Point{
    public int x;
    public int y;
    public Point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    void func(Point p) {
        p = null;
    }


    Point func2(){
        Point p=new Point(100,200);
        return p;
    }
    public static void main(String[] args) {
        Point p=new Point(10,20);
        p.func(p);
        System.out.println(p.x);
        System.out.println(p.y);

    }
}


