package com.hubu.list;
public class SingleHeadTailListTest {
    public static void main(String[] args) {
        testList();
    }
    public static void testList(){
        SingleHeadTailList<Integer> list=new SingleHeadTailList<>(null);
        list.add(0,12);
        list.add(1,23);
        list.add(2,34);
        list.add(3,45);
        list.show();
        System.out.println("_____________________");
        list.remove(list.size-1);
        list.show();

        System.out.println("_____________________");
        list.remove(list.size-1);
        list.show();

        System.out.println("_____________________");
        list.remove(list.size-1);
        list.show();


    }
    public static void testQueue(){
        Queue<Integer> queue=new SingleHeadTailList<>(null);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        while(!queue.isEmpty()){
            System.out.println(queue.poll());
        }
    }
}
