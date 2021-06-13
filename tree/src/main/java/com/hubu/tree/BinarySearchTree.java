package com.hubu.tree;
import java.util.Comparator;
import java.util.LinkedList;
public class  BinarySearchTree<T> extends AbstractTree<T> implements Tree<T>{
    private Comparator comparator;
    protected Node<T> root;
    public BinarySearchTree(Printer<T> printer,Comparator<T> comparator){
        super(printer);
        this.comparator=comparator;
    }
    public void add(T value){
        if(root==null){
            root=new Node<>(value,null,null,null);
            size++;
            return;
        }
        int compare=0;
        Node<T> current=root;
        Node<T> parent=null;
        while(current!=null) {
            parent = current;
            compare = compare(value, current.value);
            if (compare > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        Node<T> newNode=new Node<>(value,parent,null,null);
        if(compare>0){
            parent.right=newNode;
        }
        else{
            parent.left=newNode;
        }
        size++;
    }

    @Override
    public void clear() {

    }

    @Override
    public void remove(T value) {
        Node<T> currentNode = findCurrentNode(value);
        if(currentNode==null){
            return ;
        }
        Node<T> parentNode=currentNode.parent;
        //当前节点没有任何子节点
        if(currentNode.hasNoChild()){
            //如果是根节点，并且左右子节点都为空 此时就只有一个根节点，将其置空
            if(parentNode==null){
                root=null;
                return;
            }
            if(parentNode.left==currentNode){
                parentNode.left=null;
            }
            else{
                parentNode.right=null;
            }
            size--;
        }

        //当前节点有一个子节点
        else if(currentNode.hasOneChild()){
           //如果是根节点root并且只有一个子节点
            if(parentNode==null){
                //如果是左子节点
                if(currentNode.left!=null){
                    root=currentNode.left;
                    root.parent=null;
                }
                //如果是右子节点
                else{
                    root=currentNode.right;
                    root.parent=null;
                }

                return;
            }

            //如果当前节点是parent的左子节点
            if(parentNode.left==currentNode){

                //当前节点的子节点在左边
                if(currentNode.left!=null){
                    currentNode.left.parent=currentNode.parent;
                    currentNode.parent.left=currentNode.left;
                    currentNode.parent=null;
                }

                //当前节点的子节点在右边
                else{

                    currentNode.right.parent=currentNode.parent;
                    currentNode.parent.left=currentNode.right;
                    currentNode.parent=null;
                }
            }
            //当前节点是parent的右子节点
            else{
                //当前节点的子节点在左边
                if(currentNode.left!=null){
                    currentNode.left.parent=currentNode.parent;
                    currentNode.parent.right=currentNode.left;
                    currentNode.parent=null;
                }

                //当前节点的子节点在右边
                else{
                    currentNode.right.parent=currentNode.parent;
                    currentNode.parent.right=currentNode.right;
                    currentNode.parent=null;
                }
            }
            size--;
        }

        //当前节点有两个子节点
        else {
            Node<T> right=currentNode.right;
            //右子树的最小节点
            Node<T> lastLeftChild=null;
            while(right!=null){
                lastLeftChild=right;
                right=right.left;
            }
            currentNode.value= lastLeftChild.value;
            //删除当前节点
            if(lastLeftChild.parent.left==lastLeftChild){
                lastLeftChild.parent.left=null;
            }
            else{
                lastLeftChild.parent.right=null;
            }
            size--;
        }
    }
    public int compare(T value1,T value2){
        return comparator.compare(value1,value2);
    }
    //添加节点
    public boolean isEmpty(){
        return size==0;
    }

    @Override
    public void preOrder() {
        if(root==null){
            return;
        }
        doPreOrder(root);
    }

    @Override
    public void midOrder() {
        if(root==null){
            return;
        }
        doMidOrder(root);
    }

    @Override
    public void postOrder() {
        if(root==null){
            return;
        }
        doPostOrder(root);
    }

    public void doPreOrder(BinarySearchTree.Node<T> node){
        System.out.println(node.value);
        if(node.left!=null){
            doPreOrder(node.left);
        }
        if(node.right!=null){
            doPreOrder(node.right);
        }
    }

    public void doMidOrder(BinarySearchTree.Node<T> node){
        if(node.left!=null){
            doMidOrder(node.left);
        }
        System.out.println(node.value);
        if(node.right!=null){
            doMidOrder(node.right);
        }
    }

    public void doPostOrder(BinarySearchTree.Node<T> node){
        if(node.left!=null){
            doPostOrder(node.left);
        }
        if(node.right!=null){
            doPostOrder(node.right);
        }
        System.out.println(node.value);
    }
    private Node<T> findParentNode(T value){
        if(root==null){
            return null;
        }
        Node<T> currentNode = findCurrentNode(value);
        if(currentNode==null){
            return null;
        }
        return currentNode.parent;
    }
    private Node<T> findCurrentNode(T value){
        if(root==null){
            return null;
        }
        Node<T> current = root;
        int compare=0;
        Node<T> findNode=null;
        while(current!=null){
            if(value.equals(current.value)){
                //当前节点就是需要查找的节点
                findNode=current;
                break;
            }
            compare=compare(value, current.value);
            if(compare>0){
                current=current.right;
            }
            else{
                current=current.left;
            }
        }
        return findNode;
    }
    public boolean contains(T value){
        if(root==null){
            return false;
        }
        return findCurrentNode(value)!=null;
    }
    public boolean hasTwoChild(T value){
        Node<T> node = findCurrentNode(value);
        if(node==null){
            return false;
        }
        return node.hasTwoChild();
    }
    public boolean hasOneChild(T value){
        Node<T> node = findCurrentNode(value);
        if(node==null){
            return false;
        }
        return node.hasOneChild();
    }
    public boolean hasNoChild(T value) {
        Node<T> node = findCurrentNode(value);
        if (node == null) {
            return false;
        }
        return node.hasNoChild();
    }
    public boolean hasTwoChild(Node<T>node) {
        if(node==null) {
            return false;
        }
        return node.hasTwoChild();
    }
    public boolean hasOneChild(Node<T>node) {
        if(node==null) {
            return false;
        }
        return node.hasOneChild();
    }
    public boolean hasNoChild(Node<T>node) {
        if(node==null) {
            return false;
        }
        return node.hasNoChild();
    }
    //求解树的高度
    public int height(){
        if(root==null){
            return 0;
        }
        return doHeight(root);
    }
    private int doHeight(Node<T> node){
        if(node==null){
            return 0;
        }
        else if(node.left==null&&node.right==null){
            return 1;
        }
        return Math.max(doHeight(node.left),doHeight(node.right))+1;
    }
    //层序遍历树节点
    public void level(){
        if(root==null){
            return;
        }
        else{
            doLevel(root);
        }
    }

    private void doLevel(Node<T> node) {
        LinkedList<Node<T>> queue=new LinkedList<>();
        queue.offer(node);
        while(!queue.isEmpty()){
            Node<T> current = queue.poll();
            System.out.println(current.value);
            if(current.left!=null){
                queue.offer(current.left);

            }
            if(current.right!=null){
                queue.offer(current.right);
            }
        }
    }
    static class Node<T>{
        T value;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;
        public Node(T value,Node<T> left,Node<T> right){
            this(value,null,left,right);
        }
        public Node(T value,Node<T> parent,Node<T> left,Node<T> right){
            this.parent=parent;
            this.left=left;
            this.right=right;
            this.value=value;
        }
        public Node(T value,Node<T> parent){
            this(value,parent,null,null);
        }
        public Node(T value){
            this(value,null,null);
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }
        public Node<T> getParent() {
            return parent;
        }
        public T getValue() {
            return value;
        }
        public boolean hasTwoChild(){
            return left!=null&&right!=null;
        }
        public boolean hasNoChild(){
            return left==null&&right==null;
        }
        public boolean hasOneChild(){
            return (left!=null&&right==null)||(left==null&&right!=null);
        }
    }
}
