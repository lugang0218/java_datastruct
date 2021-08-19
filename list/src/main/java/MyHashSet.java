class MyHashSet {
    /** Initialize your data structure here. */
    static class Node{
        public int val;
        public Node next;
        public Node(int val,Node next){
            this.val=val;
            this.next=next;
        }
    }

    private Node head=null;
    public MyHashSet() {
        
    }
    
    public void add(int key) {
        head=new Node(key,head);
    }
    public void remove(int key) {
        while(head!=null&&head.val==key){
            head=head.next;
        }   
        Node cur=head;
        while(cur.next!=null){
            if(cur.next.val==key){
                cur=cur.next.next;
                return;
            }
            cur=cur.next;
        }
    }
    
    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        Node current=head;
        while(current!=null){
            if(current.val==key){
                return true;
            }
            current=current.next;
        }
        return false;
    }


    //["MyHashSet","add","add","contains","contains","add","contains","remove","contains"]
    //[[],[1],[2],[1],[3],[2],[2],[2],[2]]
    public static void main(String[] args) {
        MyHashSet set=new MyHashSet();
        set.add(1);
        set.add(2);
        set.contains(1);
        set.contains(3);
        set.add(2);
        System.out.println(set.contains(2));
        set.remove(2);
        System.out.println(set.contains(2));
    }
}