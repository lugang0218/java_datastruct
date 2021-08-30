package com.hubu.tree;
import com.hubu.tree.printer.BinaryTreeInfo;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinarySearchTree<T> extends AbstractTree<T> implements BinaryTreeInfo,Tree<T> {
    private Comparator<T> comparator;
    private Node<T> root;

    public BinarySearchTree(Printer<T> printer, Comparator<T> comparator) {
        super(printer, comparator);
    }
    public BinarySearchTree(Comparator<T> comparator) {
        super(null, comparator);
    }


    @Override
    public void add( T value){
        size++;
        root =add(this.root,null,value);
    }

    @Override
    public void clear() {

    }
    private  Node<T> add(Node<T> node,T value){
        if(node==null) {
            Node<T> newNode=new Node<>(value);
            return newNode;
        }
        else if(compare(value,node.value)==0){
            size--;
            node.value=value;
        }
        else if(compare(value,node.value)>0){
            node.right=add(node.right,value);
        }

        else if(compare(value,node.value)<0){
            node.left=add(node.left,value);
        }
        return node;
    }

    public void build(List<T> list){
        for(T item:list){
            add(item);
        }
    }
    private static <T> Node<T> predecessor(Node<T> node) {
        if (node == null)
            return null;
        else if (node.left != null) {
            Node<T> p = node.left;
            while (p.right != null)
                p = p.right;
            return p;
        } else {
            Node<T> p = node.parent;
            Node<T> ch = node;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }
    private static <T> Node<T> successor(Node<T> node) {
        if (node == null)
            return null;
        else if (node.right != null) {
            Node<T> p = node.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Node<T> p = node.parent;
            Node<T> ch = node;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }
    public void remove(T value){
        Node<T> node = getNode(root, value);
        if(node!=null) {
            size--;
            remove(node);
        }
    }
    private void remove(Node<T> node){
        if(node.left!=null&&node.right!=null){
            //寻找后继节点删除
            Node<T> s = successor(node);
            node.value=s.value;
            node=s;
        }

        Node<T> replacement=node.left!=null?node.left:node.right;
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
        }
        else if(node.parent==null) {
            root=null;
        }
        else {
            if (node.parent != null) {
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else if (node == node.parent.right) {
                    node.parent.right = null;
                }
                node.parent = null;
            }

        }
    }
    private  Node<T> getNode(Node<T> node,T value){
        if(node==null) return null;
        if(node.value.equals(value)){
            return node;
        }
        else if(compare(value,node.value)>0){
            return getNode(node.right,value);
        }
        else{
            return getNode(node.left,value);
        }
    }
    public T maxValue(){
        if(root==null) return null;
        return maxValue(root);
    }
    private T maxValue(Node<T> node){
        if(node.right==null) return node.value;
        return maxValue(node.right);
    }
    public T minValue(){
        if(root==null) return null;
        return minValue(root);
    }
    private T minValue(Node<T> node){
        if(node.left==null) return node.value;
        return minValue(node.left);
    }
    public Node<T> add(Node<T> node,Node<T> parent,T value){
        if(node==null){
            Node<T> newNode=new Node<>(value,parent);
            return newNode;
        }
        else if(compare(value,node.value)>0){
            node.right=add(node.right,node,value);
        }
        else if(compare(value,node.value)<0){
            node.left=add(node.left,node,value);
        }
        return node;
    }
    public boolean contains(T value){
        return contains(root,value);
    }
    private boolean contains(Node<T> node,T value){
        if(node==null) return false;

        if(node.value.equals(value)) {
            return true;
        }
        else if(compare(value,node.value)>0){
            return contains(node.right,value);
        }
        else{
            return contains(node.left,value);
        }
    }
    @Override
    public void afterAdd() {

    }

    public void preOrder(){
        preOrder(root);
    }

    @Override
    public void midOrder() {

    }

    private void preOrder(Node<T> node){
        if(node==null) return;
        System.out.println(node.value);
        preOrder(node.left);
        preOrder(node.right);
    }

    public  void levelOrder(){
        Node<T> current=root;
        if(current!=null){
            Queue<Node<T>> nodeQueue=new LinkedList<>();
            nodeQueue.offer(current);
            while(!nodeQueue.isEmpty()){
                int size=nodeQueue.size();
                for(int i=0;i<size;i++){
                    Node<T> node = nodeQueue.poll();
                    System.out.println(node.value);
                    if(node.left!=null){
                        nodeQueue.offer(node.left);
                    }
                    if(node.right!=null){
                        nodeQueue.offer(node.right);
                    }
                }
            }
        }

    }
    public void inOrder(){
        inOrder(root);
    }
    private void inOrder(Node<T> node){
        if(node==null) return;

        inOrder(node.left);
        System.out.println(node.value);
        inOrder(node.right);
    }

    public int height(){
        return height(root);
    }
    private int height(Node<T> node){
        if(node==null) return 0;
        else if(node.left==null&&node.right==null){
            return 1;
        }
        else{
            return Math.max(height(node.left),height(node.right))+1;
        }
    }
    public void postOrder(){
        postOrder(root);
    }
    private void postOrder(Node<T> node){
        if(node==null) return;

        postOrder(node.left);

        postOrder(node.right);

        System.out.println(node.value);
    }

    @Override
    public Object root() {
        return root;
    }
    @Override
    public Object left(Object node) {
        return ((Node)node).left;
    }
    @Override
    public Object right(Object node) {
        return ((Node)node).right;
    }
    @Override
    public Object string(Object node) {
        return ((Node)node).value;
    }

    static class Node<T> {
        T value=null;

        Node<T> left;
        Node<T> right;
        Node<T> parent;

        public Node(T value,Node<T> left,Node<T> parent,Node<T> right){
            this.value=value;

            this.left=left;

            this.parent=parent;

            this.right=right;
        }
        public Node(T value){
            this(value,null,null,null);
        }

        public Node(T value,Node<T> parent) {
            this(value, null, parent, null);
        }
    }
}
