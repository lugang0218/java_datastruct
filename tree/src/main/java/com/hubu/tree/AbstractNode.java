package com.hubu.tree;
public abstract class AbstractNode<T> {
    private T value;
    private AbstractNode<T> left;
    private AbstractNode<T> parent;
    private AbstractNode<T> right;
    public AbstractNode(T value,AbstractNode<T> parent,AbstractNode<T> left,AbstractNode<T> right){
        this.left=left;
        this.value =value;
        this.right=right;
        this.parent=parent;
    }

    public AbstractNode<T> getLeft() {
        return left;
    }

    public AbstractNode<T> getParent() {
        return parent;
    }

    public AbstractNode<T> getRight() {
        return right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setLeft(AbstractNode<T> left) {
        this.left = left;
    }

    public void setParent(AbstractNode<T> parent) {
        this.parent = parent;
    }

    public void setRight(AbstractNode<T> right) {
        this.right = right;
    }
}
