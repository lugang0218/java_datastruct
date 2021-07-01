package com.hubu;
public class ArrayBinaryHeapTest {
    public static void main(String[] args) {
        ArrayBinaryHeap<Integer> heap=new ArrayBinaryHeap<>(null);
        heap.add(12);
        heap.add(13);
        heap.add(14);
        heap.add(15);
        heap.add(16);
        heap.add(17);
        heap.show();
    }
}
