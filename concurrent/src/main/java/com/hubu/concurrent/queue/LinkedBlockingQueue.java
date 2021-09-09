package com.hubu.concurrent.queue;

public class LinkedBlockingQueue<T> {













    private static class Node<T>{
        volatile T value;
        volatile Node<T> next;


        public Node(T value){
            UNSAFE.putObject(this,valueOffset,value);
        }


        boolean setValue(T oldValue,T newValue){
            return UNSAFE.compareAndSwapObject(this,valueOffset,oldValue,newValue);
        }


        void setNext(Node<T> next){
            UNSAFE.putObject(this,nextOffset,next);
        }

        boolean setNext(Node<T> oldNext,Node<T> newNext){
            return UNSAFE.compareAndSwapObject(this,nextOffset,oldNext,newNext);
        }


        private static final sun.misc.Unsafe UNSAFE;
        private static final long valueOffset;
        private static final long nextOffset;

        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> k = LinkedBlockingQueue.Node.class;
                valueOffset = UNSAFE.objectFieldOffset
                        (k.getDeclaredField("value"));
                nextOffset = UNSAFE.objectFieldOffset
                        (k.getDeclaredField("next"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }


    private volatile Node<T> head;


    private volatile Node<T> tail;



    public LinkedBlockingQueue(){
        head=tail=new Node<>(null);
    }

}
