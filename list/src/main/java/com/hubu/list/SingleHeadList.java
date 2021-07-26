package com.hubu.list;
import com.hubu.list.util.Printer;
public class SingleHeadList<T> extends AbstractList<T> implements List<T> {

    private SingleList.Node<T> head=new SingleList.Node<>(null,null);

    public SingleHeadList(Printer printer) {
        super(printer);
    }

    @Override
    public void clear() {

    }


    @Override
    public boolean add(T value) {
//        addFromHead(value);
        addFromTail(value);
        return true;
    }
    public void addFromHead(T value){
        SingleList.Node next=head.next;
        SingleList.Node<T> newNode=new SingleList.Node<>(value,next);
        head.next=newNode;
        size++;
    }

    private SingleList.Node<T> findNode(int index){
        SingleList.Node<T> current=head.next;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        return current;
    }
    public void addFromTail(T value){
        SingleList.Node<T> current=head;
        while(current.next!=null){
            current=current.next;
        }
        current.next=new SingleList.Node<>(value,null);
    }
    @Override
    public T get(int index) {
        //todo check index

        SingleList.Node<T> current=head.next;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        return current.value;
    }
    @Override
    public T set(int index, T newValue) {
        return null;
    }
    @Override
    public boolean add(int index, T value) {
        return false;
    }
    @Override
    public T remove(int index) {
        size--;
        T value;
        if(index==0){
            value=head.next.value;
            head.next=head.next.next;
        }
        else{
            SingleList.Node<T> prevNode=findNode(index-1);
            value=prevNode.next.value;
            prevNode=prevNode.next.next;
        }
        return value;
    }
}
