package com.hubu.tree2;
import com.hubu.tree.AbstractTree;
import com.hubu.tree.Printer;
import com.hubu.tree.Tree;
import java.util.HashMap;
import java.util.Map;
public class BinarySearchTree<T> extends AbstractTree<T> implements Tree<T> {
    private Map<Node<T>, Node<T>> unionNodeMap=new HashMap<>();
    private Node<T> root;
    public BinarySearchTree(Printer<T> printer) {
        super(printer);
    }


    /**
     * 左旋代码实现
     * @param node
     */
    private void leftRotate(Node<T> node){
        if(node==null)
            return;
        Node<T> rightNode=node.right;
        node.right=rightNode.left;
        rightNode.left=node;
        node=rightNode;

    }
    private void rightRotate(Node<T> node){
        if(node==null)
            return;
        Node<T> leftNode=node.left;

        node.left=leftNode.right;


        leftNode.right=node;

        node=leftNode;
    }





    @Override
    public void afterAdd() {

    }

    @Override
    public void add(T value) {
        if(root==null){
            root=new Node<>(value,null,null);
            unionNodeMap.put(root,null);
            size++;
            return ;
        }
        Node<T> parent=null;
        Node<T> current=root;
        int compareResult=0;
        while(current!=null){
            parent=current;
            compareResult=compare(value,current.data);
            if(compareResult==0){
                current.data=value;
                return ;
            }
            else if(compareResult>0){
                current=current.right;
            }
            else{
                current=current.left;
            }
        }
        Node<T> newNode=new Node(value,null,null);
        unionNodeMap.put(newNode,parent);
        if(compareResult>0){
            parent.right=newNode;
        }else{
            parent.left=newNode;
        }
        size++;
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
            return ;
        }
        doPreOrder(root);
    }

    @Override
    public void midOrder() {

    }

    @Override
    public void postOrder() {

    }
    public void doPreOrder(Node<T> node){
        System.out.println(node.data);
        if(node.left!=null){
            doPreOrder(node.left);
        }
        if(node.right!=null){
            System.out.println(node.right);
        }
    }

    static class Node<E>{
        E data;
        Node<E> left;
        Node<E> right;
        public Node(E data,Node<E> left,Node<E> right){
            this.data=data;
            this.left=left;
            this.right=right;
        }
    }
    //父节点通过参数返回 函数体返回当前节点
    public void testFindNode(T value) {
        NodeWrapper<T> wrapper = findNodeAndParentNode(value);
        System.out.println(wrapper.getCurrent().data);
        System.out.println(wrapper.getParent().data);

    }
    private NodeWrapper<T> findNodeAndParentNode(T value){
        if(root==null){
            return null;
        }
        Node<T> current = root;
        int compare=0;
        Node<T> findNode=null;
        Node<T> parent=null;
        while(current!=null){

            if(value.equals(current.data)){
                //当前节点就是需要查找的节点
                findNode=current;
                break;
            }

            //保存父节点
            parent=current;
            compare=compare(value, current.data);
            if(compare>0){
                current=current.right;
            }
            else{
                current=current.left;
            }
        }
        NodeWrapper<T> wrapper=new NodeWrapper<>(parent,findNode,null,null);
        return wrapper;
    }
    private  Node<T> successor(Node<T> node) {
        if (node == null)
            return null;
        else if (node.right != null) {
            Node<T> p = node.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Node<T> p = unionNodeMap.get(node);
            Node<T> ch = node;
            while (p != null && ch == p.right) {
                ch = p;
                p = unionNodeMap.get(p);
            }
            return p;
        }
    }
    private Node<T> predecessor(Node<T> node) {
        if (node == null)
            return null;
        else if (node.left != null) {
            Node<T> p = node.left;
            while (p.right != null)
                p = p.right;
            return p;
        } else {
            Node<T> p = unionNodeMap.get(node);
            Node<T> ch = node;
            while (p != null && ch == p.left) {
                ch = p;
                p = unionNodeMap.get(p);
            }
            return p;
        }
    }




    private Node<T> findNode(T value){
        if(root==null){
            return null;
        }
        Node<T> current = root;
        int compare=0;
       Node<T> findNode=null;
        while(current!=null){
            if(value.equals(current.data)){
                //当前节点就是需要查找的节点
                findNode=current;
                break;
            }
            compare=compare(value, current.data);
            if(compare>0){
                current=current.right;
            }
            else{
                current=current.left;
            }
        }
        return findNode;
    }
}















