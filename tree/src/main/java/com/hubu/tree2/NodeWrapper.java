package com.hubu.tree2;
/**
 * Node
 */
public class NodeWrapper<T>{
    private BinarySearchTree.Node<T> parent;
    private BinarySearchTree.Node<T> current;
    private BinarySearchTree.Node<T> leftChild;
    private BinarySearchTree.Node<T> rightChild;



    public NodeWrapper(BinarySearchTree.Node<T> parent,
                       BinarySearchTree.Node<T> current,
                       BinarySearchTree.Node<T> leftChild,
                       BinarySearchTree.Node<T> rightChild){



        this.parent=parent;
        this.leftChild=leftChild;
        this.rightChild=rightChild;
        this.current=current;
    }
    public BinarySearchTree.Node<T> getCurrent() {
        return current;
    }

    public BinarySearchTree.Node<T> getLeftChild() {
        return leftChild;
    }

    public BinarySearchTree.Node<T> getParent() {
        return parent;
    }

    public BinarySearchTree.Node<T> getRightChild() {
        return rightChild;
    }

    public void setParent(BinarySearchTree.Node<T> parent) {
        this.parent = parent;
    }

    public void setCurrent(BinarySearchTree.Node<T> current) {
        this.current = current;
    }

    public void setLeftChild(BinarySearchTree.Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(BinarySearchTree.Node<T> rightChild) {
        this.rightChild = rightChild;
    }
}
