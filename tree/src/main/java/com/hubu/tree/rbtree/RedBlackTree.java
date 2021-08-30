package com.hubu.tree.rbtree;
import java.util.Comparator;
public class RedBlackTree<K,V>{
    private static final boolean RED=false;
    private static final boolean BLACK=true;
    private Comparator<K> comparator;
    private Node<K,V> root=null;
    private int size=0;
    private void leftRotate(Node<K,V> node){
        Node<K,V> rightNode=node.right;
        if(rightNode.left!=null){
            rightNode.left.parent=node;
        }
        rightNode.parent=node.parent;
        node.right=rightNode.left;
        if(node.parent==null){
            this.root=rightNode;
        }
        else if(node==node.parent.left){
            node.parent.left=rightNode;
        }
        else if(node==node.parent.right){
            node.parent.right=rightNode;
        }
        rightNode.left=node;
        node.parent=rightNode;
    }
    private void rightRotate(Node<K,V> node){
        Node<K,V> leftNode=node.left;
        if(leftNode.right!=null){
            leftNode.right.parent=node;
        }
        leftNode.parent=node.parent;
        node.left=leftNode.right;

        if(node.parent==null){
            root=leftNode;
        }
        else if(node.parent.left==node){
            node.parent.left=leftNode;
        }
        else if(node.parent.right==node){
            node.parent.right=leftNode;
        }
        leftNode.right=node;
        node.parent=leftNode;

    }
    public void put(K key,V value){
        if(root==null) {
            root=new Node(key,value,null,BLACK);
            size++;
            return ;
        }
        Node<K,V> current=root;
        Node<K,V> parent=null;
        int compareResult=0;
        while(current!=null){
            parent=current;
            compareResult=compare(key,current.key);
            if(compareResult>0){
                current=current.right;
            }else if(compareResult<0){
                current=current.left;
            }else {
                current.key=key;
                current.value=value;
                return ;
            }
        }
        size++;
        Node<K,V> newNode=new Node<K,V>(key,value,parent);
        if(compareResult>0){
            parent.right=newNode;
        }
        else if(compareResult<0){
            parent.left=newNode;
        }
        afterPut(newNode);
    }
    public int height(){
        return height(root);
    }
    private int height(Node<K,V> node){
        if(node==null) return 0;
        else if(node.left==null&&node.right==null) return 1;
        else return Math.max(height(node.left),height(node.right))+1;
    }
    public V get(K key){
        Node<K, V> node = getNode(root, key);
        return node!=null?node.value:null;
    }
    private Node<K,V> getNode(Node<K,V> node,K key){
        if(node==null) return null;
        if(compare(key,node.key)==0) return node;
        else if(compare(key,node.key)>0) return getNode(node.right,key);
        else return getNode(node.left,key);
    }
    public K maxKey(){
        Node<K, V> maxKeyNode = getMaxKeyNode(root);
        return maxKeyNode!=null?maxKeyNode.key:null;
    }
    public K minKey(){
        Node<K, V> minKeyNode = getMinKeyNode(root);
        return minKeyNode!=null?minKeyNode.key:null;
    }
    private Node<K,V> getMinKeyNode(Node<K,V> node){
        if(node==null) return null;
        else if(node.left==null) return node;
        else return getMaxKeyNode(node.left);
    }
    private Node<K,V> getMaxKeyNode(Node<K,V> node){
        if(node==null) return null;
        else if(node.right==null) return node;
        else return getMaxKeyNode(node.right);
    }
    public V maxKeyValue(){
        Node<K, V> maxKeyNode = getMaxKeyNode(root);
        return maxKeyNode!=null?maxKeyNode.value:null;
    }

    public V minKeyValue(){
        Node<K, V> minKeyNode = getMinKeyNode(root);
        return minKeyNode!=null?minKeyNode.value:null;
    }

    private static <K,V> Node<K,V> successor(Node<K,V> node) {
        if (node == null)
            return null;
        else if (node.right != null) {
            Node<K,V> p = node.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Node<K,V> p = node.parent;
            Node<K,V> ch = node;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private static <K,V> Node<K,V> predecessor(Node<K,V> node) {
        if (node == null)
            return null;
        else if (node.left != null) {
            Node<K,V> p = node.left;
            while (p.right != null)
                p = p.right;
            return p;
        } else {
            Node<K,V> p = node.parent;
            Node<K,V> ch = node;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    public V remove(K key){
        Node<K, V> node = getNode(root, key);
        if(node!=null) remove(node);
        return node!=null?node.value:null;
    }
    private void remove(Node<K,V> node){
        if(node==null) return ;
        if(node.left!=null&&node.right!=null){
            Node<K,V> p=successor(node);
            node.key=p.key;
            node.value=p.value;
            node=p;
        }
        Node<K,V> replacement=node.left!=null?node.left:node.right;
        if(replacement!=null){
            replacement.parent=node.parent;
            if(node.parent==null){
                root=replacement;
            }
            else if(node==node.parent.left){
                node.parent.left=replacement;
            }
            else if(node==node.parent.right){
                node.parent.right=replacement;
            }
            node.parent=node.left=node.right=null;
        }else if(node.parent==null){
            root=null;
        }else{
            if(node==node.parent.left){
                node.parent.left=null;
            }
            else if(node==node.parent.right){
                node.parent.right=null;
            }
        }
    }
    private void afterPut(Node<K,V> node){
        if(node!=null){
            node.color=RED;
            while(node!=root&&parentOf(node).color==RED){
                Node<K,V> parent=node.parent;
                Node<K,V> grand=parent.parent;
                if(parent==leftOf(grand)){
                    Node<K,V> uncle=rightOf(grand);
                    /**
                     * 空节点的颜色是黑色的,如果有兄弟节点
                     */
                    if(colorOf(uncle)==RED){
                        setColor(parent,BLACK);
                        setColor(uncle,BLACK);
                        setColor(grand,RED);
                        node=grand;
                    }
                    if(node==rightOf(parentOf(node))){
                        leftRotate(parentOf(node));
                    }
                    setColor(parentOf(parentOf(node)),RED);
                    setColor(parentOf(node),BLACK);
                    rightRotate(parentOf(parentOf(node)));
                }
                else{
                    Node<K,V> uncle=leftOf(grand);
                    /**
                     * 空节点的颜色是黑色的,如果有兄弟节点
                     */
                    if(colorOf(uncle)==RED){
                        setColor(parent,BLACK);
                        setColor(uncle,BLACK);
                        setColor(grand,RED);
                        node=grand;
                    }
                    if(node==leftOf(parentOf(node))){
                        rightRotate(parentOf(node));
                    }
                    setColor(parentOf(parentOf(node)),RED);
                    setColor(parentOf(node),BLACK);
                    leftRotate(parentOf(parentOf(node)));
                }
            }
        }
    }

    private Node<K, V> parentOf(Node<K, V> node) {
        return node!=null?node.parent:null;
    }

    private void setColor(Node<K, V> node, boolean color) {
        if(node!=null){
            node.color=color;
        }
    }
    private boolean colorOf(Node<K, V> node) {
        return node!=null?node.color:BLACK;
    }

    private Node<K, V> leftOf(Node<K, V> node) {
        return node!=null?node.left:null;
    }

    private Node<K,V> rightOf(Node<K,V> node){
        return node!=null?node.right:null;
    }

    public RedBlackTree(){
        this(null);
    }
    public RedBlackTree(Comparator<K> comparator){
        this.comparator=comparator;
    }

    public void midOrder(){
        midOrder(root);
    }
    private void midOrder(Node<K,V> node){
        if(node==null) return;
        midOrder(node.left);
        System.out.println(node.key+"---->"+node.value);
        midOrder(node.right);
    }
    private int compare(K key1,K key2){
        return comparator!=null?comparator.compare(key1,key2):((Comparable)key1).compareTo(key2);
    }

    private static final  class Node<K,V> {
        private K key;

        private V value;


        private Node<K,V> parent;

        private Node<K,V> left;

        private Node<K,V> right;


        private boolean color;

        public Node(K key,V value,
                    Node<K,V> parent,
                    Node<K,V> left,
                    Node<K,V> right,
                    boolean color){
            this.key=key;
            this.value=value;
            this.parent=parent;
            this.left=left;
            this.right=right;
            this.color=color;
        }
        public Node(K key,V value,Node<K,V> parent,boolean color){
            this(key,value,parent,null,null,color);
        }
        public Node(K key,V value,Node<K,V> parent){
            this(key,value,parent,null,null,BLACK);
        }
    }
}
