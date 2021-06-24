package com.hubu.tree;
import java.util.Comparator;
public class AvlTree <T> extends AbstractTree<T> implements Tree <T>{
    private Node<T> root=null;
    private Comparator comparator;
    public AvlTree(Printer<T> printer,Comparator<T> comparator){
        super(printer);
        this.comparator=comparator;
    }
    public AvlTree(Printer<T> printer) {
        super(printer);
    }

    @Override
    public void afterAdd() {

    }

    @Override
    public void add(T value){
        if(root==null){
            root=new Node<>(1,value,null,null,null);
            size++;
            afterAdd(root);
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
        Node<T> newNode=new Node<>(1,value,parent,null,null);
        if(compare>0){
            parent.right=newNode;
        }
        else{
            parent.left=newNode;
        }
        size++;
        afterAdd(newNode);
    }

    //node node是刚刚添加的节点 node节点一定是平衡的
    public void afterAdd(Node<T> node){
        while((node=node.parent)!=null){
            if(isBalance(node)){
                continue;
            }
            else{
                reBalance(node);
            }
        }
    }

    private void reBalance(Node<T> grand) {

        Node<T> parent=heightNode(grand);

        Node<T> child=heightNode(parent);



        if(parent==parent.parent.left){
            if(child==child.parent.left){
                rightRotate(grand);
            }
            else{
                //先对父节点进行左旋
                leftRotate(parent);

                //在对爷爷节点进行右旋
                rightRotate(grand);
            }
        }


        else{
            if(child==child.parent.right){
                leftRotate(grand);
            }
            else{
                //先对父节点进行右旋
                rightRotate(parent);

                //在对爷爷节点进行左旋
                leftRotate(grand);
            }
        }
    }

    //对node进行左旋
    private void leftRotate(Node<T> node){
        Node<T> rightNode=node.right;
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
    //对node进行右旋
    private void rightRotate(Node<T> node){
        Node<T> leftNode=node.left;
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


    //计算节点高的节点
    private Node<T> heightNode(Node<T> node){
        int leftHeight=height(node.left);
        int rightHeight=height(node.right);
        return leftHeight>=rightHeight?node.left: node.right;
    }

    //当前节点是否平衡
    private boolean isBalance(Node<T> node){
        if(node==null){
            return true;
        }
        return Math.abs(height(node.left)-height(node.right))<=1;
    }



    public int height(Node<T> node){
        if(node==null){
            return 0;
        }
        else if(node.left==null&&node.right==null){
            return 1;
        }
        else {
            return Math.max(height(node.left),height(node.right))+1;
        }
    }

    public void updateHeight(Node<T> node){
        Node<T> left=node.left;
        Node<T> right=node.right;
        int maxHeight=maxHeight(left,right);
        node.height=maxHeight+1;
    }

    private int maxHeight(Node<T> left,Node<T> right){
        if(left==null&&right==null){
            return 0;
        }
        else if(left!=null&&right!=null){
            return Math.max(left.height,right.height);
        }
        else{
            if(left!=null){
                return left.height;
            }
            else{
                return right.height;
            }
        }
    }
    private int compare(T value1, T value2) {
        return comparator.compare(value1,value2);
    }
    @Override
    public void clear() {

    }

    @Override
    public void remove(T value) {

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

    public void doPreOrder(Node<T> node){
        System.out.println(node.value);
        if(node.left!=null){
            doPreOrder(node.left);
        }
        if(node.right!=null){
            doPreOrder(node.right);
        }
    }

    public void doMidOrder(Node<T> node){
        if(node.left!=null){
            doMidOrder(node.left);
        }
        System.out.println(node.value);
        if(node.right!=null){
            doMidOrder(node.right);
        }
    }

    public void doPostOrder(Node<T> node){
        if(node.left!=null){
            doPostOrder(node.left);
        }
        if(node.right!=null){
            doPostOrder(node.right);
        }
        System.out.println(node.value);
    }

    static class Node<T>{
        int height=1;
        T value;
        Node<T> left;
        Node<T> right;
        Node<T> parent;

        public Node(int height,T value,Node<T> parent,Node<T> left,Node<T>right){
            this.height=height;
            this.value=value;
            this.parent=parent;
            this.left=left;
            this.right=right;
        }

        public Node(T value,Node<T> parent){
            this.height=1;
            this.value=value;
            this.parent=parent;
            this.left=null;
            this.right=null;
        }
    }
}
