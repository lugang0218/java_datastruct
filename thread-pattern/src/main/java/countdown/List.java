package countdown;

public class List {
    class Node{
        //Node 实例
        String value;
        Node  next;
        public Node(String value,Node next){
            this.value=value;
            this.next=next;
        }

    }

    //c++指针
    private Node head=null;





    //尾插法
    public void add(String value){
        Node newNode=new Node(value,null);
        if(head==null){
            head=newNode;
        }
        else{
            Node current=head;
            while(current.next!=null){
                current=current.next;
            }
            current.next=newNode;
        }

    }


    //头插法

    public  void  beforeAdd(String value){
        Node newNode=new Node(value,null);
        if(head==null){
            head=newNode;
        }
        else{
            newNode.next=head;
            head=newNode;
        }
    }


    public void reverseNode(){

    }


    public void beginReverse(){
        head=reverse(head);
    }
    //递归反转
    public Node reverse(Node node){
        if(node==null||node.next==null){
            return node;
        }
        Node newNode=reverse(node.next);
        node.next.next=node;
        node.next=null;
        return newNode;
    }
    public void show(){
        Node current=head;
        while(current!=null){
            System.out.println(current.value);
            current=current.next;
        }
    }


    public static void main(String[] args) {
        List list=new List();
        list.add("a");
        list.add("b");
        list.add("c");
        list.beginReverse();
        list.show();
    }


}
