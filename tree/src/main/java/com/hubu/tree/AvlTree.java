package com.hubu.tree;

public class AvlTree<T> extends AbstractTree<T> implements Tree<T>{
    private AvlNode<T> root=null;
    public AvlTree(Printer<T> printer) {
        super(printer);
    }


    @Override
    public void add(T value) {
       if(root==null){
           root=new AvlNode<>(value,null);
       }
    }

    @Override
    public void clear() {

    }

    @Override
    public void remove(T value) {

    }

    @Override
    public void preOrder() {

    }

    @Override
    public void midOrder() {

    }

    @Override
    public void postOrder() {

    }
    static class AvlNode<T>{
        //树的高度
        private int height=1;
        private T value;
        private AvlNode<T> parent;
        private AvlNode<T> left;
        private AvlNode<T> right;

        public AvlNode(int height, T value, AvlNode<T> parent, AvlNode<T> left, AvlNode<T> right) {
            this.height = height;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
        public AvlNode(T value, AvlNode<T> parent){
            this(1,value,parent,null, null);
        }
        public AvlNode(int height,T value,AvlNode<T> parent){
            this(height,value,parent,null,null);
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public AvlNode<T> getParent() {
            return parent;
        }

        public void setParent(AvlNode<T> parent) {
            this.parent = parent;
        }

        public AvlNode<T> getLeft() {
            return left;
        }

        public void setLeft(AvlNode<T> left) {
            this.left = left;
        }

        public AvlNode<T> getRight() {
            return right;
        }

        public void setRight(AvlNode<T> right) {
            this.right = right;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }




















}
