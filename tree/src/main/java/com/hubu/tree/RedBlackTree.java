package com.hubu.tree;
import com.hubu.tree.printer.BinaryTreeInfo;
import com.sun.crypto.provider.BlowfishKeyGenerator;

import java.util.Comparator;
public class RedBlackTree<K,V> implements BinaryTreeInfo {
    private int size;
    private Node<K,V> root=null;
    private Comparator<K> comparator;
    public RedBlackTree(Comparator<K> comparator){
        this.comparator=comparator;
    }
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
    private void rotateLeft(Node <K,V> p) {
        if (p != null) {
            Node <K,V> r = p.right;
            p.right = r.left;
            if (r.left != null)
                r.left.parent = p;
            r.parent = p.parent;
            if (p.parent == null)
                root = r;
            else if (p.parent.left == p)
                p.parent.left = r;
            else
                p.parent.right = r;
            r.left = p;
            p.parent = r;
        }
    }

    public Node<K, V> getRoot() {
        return root;
    }

    private void rotateRight(Node <K,V> p) {
        if (p != null) {
             Node <K,V> l = p.left;
            p.left = l.right;
            if (l.right != null) l.right.parent = p;
            l.parent = p.parent;
            if (p.parent == null)
                root = l;
            else if (p.parent.right == p)
                p.parent.right = l;
            else p.parent.left = l;
            l.right = p;
            p.parent = l;
        }
    }
    public Node<K,V> parentNode(Node<K,V> node){
        return node!=null?node.parent:null;
    }
    public void preOrder(){
        if(this.root==null){
            return ;
        }
        else doPreOrder(root);
    }

    private void doPreOrder(Node<K, V> node) {
        System.out.println("key:"+node.key+","+"value"+node.value);
        if(node.left!=null){
            doPreOrder(node.left);
        }
        if(node.right!=null){
            doPreOrder(node.right);
        }
    }


    public void put(K key,V value){
        size++;
        //根节点不用做任何操作
        if(root==null){
            root=new Node<>(key,value,null,null,null,true);
            return;
        }
        int compare=0;
        Node<K,V> current=root;
        Node<K,V> parent=null;
        while(current!=null) {
            parent = current;
            compare = compare(key, current.key);
            if (compare > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        Node<K,V> newNode=new Node<K,V>(key,value,parent,null,null,true);
        if(compare>0){
            parent.right=newNode;
        }
        else{
            parent.left=newNode;
        }
        putAfter(newNode);
    }

    private Node<K,V> leftOf(Node<K,V> node) {
        return node != null ? node.left : null;
    }

    private Node<K,V> rightOf(Node<K,V> node) {
        return node != null ? node.right : null;
    }

    private void setColor(Node<K,V> node, boolean color){
        if(node!=null){
            node.color=color;
        }
    }
    //空节点的颜色是黑的
    private boolean colorOf(Node node) {
        return node == null ? BLACK : node.color;
    }
    //插入之后调整，能来到这儿的节点一定不是根节点
    private void putAfter(Node<K,V> node){
        node.color=RED;
        //插入的节点都是红色节点
        while(node!=null&&node!=root&&node.parent.color== RED){
            //裂变情况
            //如果父亲是爷爷的左孩子
            Node<K,V> parent=parentNode(node);
            Node<K,V> grand=parent.parent;
            //如果父亲节点是爷爷节点的左节点
            if(parent==leftOf(grand)){
                //获取叔叔节点
                Node<K,V> uncle=parentNode(parentNode(node)).right;

                if(colorOf(uncle)==RED){
                    setColor(uncle,BLACK);
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //爷爷节点继续递归操作
                    node=grand;
                }

                else{

                    //如果当前节点是父亲的右节点 并且没有叔叔节点
                    if(node==rightOf(parent)){
                        //沿着父节点左旋先左旋一下变成标准的左三情况
                        leftRotate(parent);
                    }

                    //将parent变黑 爷爷变红
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //沿着爷爷右旋
                    rightRotate(grand);
                }

            }
            //如果父亲是爷爷节点的右节点
            else{
                //获取叔叔节点
                Node<K,V> uncle=parentNode(parentNode(node)).left;

                if(colorOf(uncle)==RED){
                    setColor(uncle,BLACK);
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //爷爷节点继续递归操作
                    node=grand;
                }

                else{

                    //如果当前节点是父亲的左节点 并且没有叔叔节点
                    if(node==leftOf(parent)){
                        //沿着父节点左旋先右旋一下变成标准的右三情况
                        rightRotate(parent);
                    }

                    //将parent变黑 爷爷变红
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //沿着爷爷左旋
                    leftRotate(grand);
                }
            }
        }
        root.color=BLACK;
    }
    private int compare(K key1, K key2) {
        return comparator.compare(key1,key2);
    }
    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<K,V>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<K,V>)node).right;
    }

    @Override
    public Object string(Object node) {
        String value=((Node<K,V>)node).value.toString();
        return ((Node<K,V>)node).color==BLACK?"黑"+value:"红"+value;
    }

    static class Node<K,V>{
        K key;
        V value;
        Node<K,V> parent;
        Node<K,V> left;
        Node<K,V> right;
        boolean color = BLACK;
        public Node(K key,V value,Node<K,V> parent,Node<K,V> left,Node<K,V> right,boolean color){
            this.key=key;
            this.value=value;
            this.parent=parent;
            this.left=left;
            this.right=right;
            this.color=color;
        }


        public boolean isColor() {
            return color;
        }

        public V getValue() {
            return value;
        }

        public Node<K, V> getLeft() {
            return left;
        }

        public Node<K, V> getRight() {
            return right;
        }

        public Node(K key , V value, Node<K,V> parent){
            this.color=BLACK;
            this.key=key;
            this.value=value;
            this.parent=parent;
            this.right=null;
            this.left=null;
        }
    }
    final Node<K,V> getFirstNode() {
        Node<K,V> p = root;
        if (p != null)
            while (p.left != null)
                p = p.left;
        return p;
    }
    final Node<K,V> getLastNode() {
        Node<K,V> p = root;
        if (p != null)
            while (p.right != null)
                p = p.right;
        return p;
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
            Node <K,V> ch = node;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }
    private void deleteEntry(Node<K,V> p) {
        size--;
        if (p.left != null && p.right != null) {
            Node <K,V> s = successor(p);
            p.key = s.key;
            p.value = s.value;
            p = s;
        }
        Node <K,V> replacement = (p.left != null ? p.left : p.right);

        if (replacement != null) {
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left  = replacement;
            else
                p.parent.right = replacement;
            p.left = p.right = p.parent = null;
        } else if (p.parent == null) {
            root = null;
        } else {
            if (p.parent != null) {
                if (p == p.parent.left)
                    p.parent.left = null;
                else if (p == p.parent.right)
                    p.parent.right = null;
                p.parent = null;
            }
        }
    }
}
