package com.hubu.tree;

/**
 * 
 * 
 * 234树的实现
 * 
 * 
 */
public class TwoThreeFourTree {

    public static void main(String[] args) {
        TwoThreeFourTree tree=new TwoThreeFourTree();
        for(int i=0;i<10000;i++){
            tree.add(i);
        }
    }

    private Node root=new Node();


    public Node getNextChild(Node currentNode, Integer nowDataItem) {
        int size = currentNode.getSize();
        for (int j = 0; j < size; j++) {
            if (nowDataItem < currentNode.getDataItem(j)) {
                return currentNode.getChild(j);
            }
        }
        return currentNode.getChild(size - 1);
    }
    public void add(int value){
        Node current=root;
        while(true){
            /**
             *
             *
             * 拆分当前节点
             */
            if(current==null){
                System.out.println("hello world");
            }
            if(current.isFull()){
                split(current);
                current=current.getParent();
                current=getNextChild(current,value);
            }
            /**
             * 如果是叶子节点直接退出循环插入
             */
            else if(current.isLeafNode()){
                break;
            }
            /**
             * 获取下一个子节点进行插入
             */
            else{
                current=getNextChild(current,value);
            }
        }
        current.add(value);
    }

    private void  split(Node current) {
        /**
         * 准备右节点和父节点
         */
        Node rightNode = null;
        Node parentNode = null;

        Integer rightData = current.removeData();


        Integer parentData = current.removeData();


        Node lastNode = current.removeNode(2);

        Node secondLastNode = current.removeNode(3);


        rightNode=new Node();
        rightNode.add(rightData);

        rightNode.setChild(0,secondLastNode);
        rightNode.setChild(1,lastNode);



        if(current==root){
            root=new Node();
            root.setChild(0,current);
            parentNode=root;
        }
        else{
            parentNode=current.getParent();
        }

        parentNode.add(parentData);


        /**
         *
         * 获取到插入的位置
         */
        int index=parentNode.add(parentData);

        int size=parentNode.getSize();


        /**
         *
         * 拆分父节点
         */
        for (int j =  size- 1; j > index; j--) {
            Node temp = parentNode.removeNode(j);
            parentNode.setChild(j + 1, temp);
        }
        parentNode.setChild(index+1,rightNode);
    }

    static class Node {
        private static final int CHILD_NODE_COUNT = 4;
        private int size;
        private Node parent;
        private Node[] children = new Node[CHILD_NODE_COUNT];
        private Integer[] dataItems = new Integer[CHILD_NODE_COUNT - 1];

        public Node() {
            this.size = 0;
        }

        public boolean isFull() {

            return (size == CHILD_NODE_COUNT - 1);
        }

        public Node getParent() {

            return parent;
        }
        public Node getChild(int childIndex) {

            return children[childIndex];
        }
        public boolean isLeafNode() {

            return children[0] == null;
        }
        public int getSize() {

            return size;
        }
        public int getDataItem(int dataItemIndex) {

            return dataItems[dataItemIndex];
        }
        public void setChild(int childIndex, Node child) {
            if (child != null) {
                children[childIndex] = child;
                child.parent = this;
            }
        }
        public Node removeNode(int childIndex) {
            Node removeNode = children[childIndex];
            children[childIndex] = null;
            return removeNode;
        }
        public int findDataItemIndex(Integer dataItem) {
            for (int i = 0; i < CHILD_NODE_COUNT - 1; i++) {
                if (dataItems[i] == null) {
                    break;
                } else if (dataItems[i].equals(dataItem)) {
                    return i;
                }
            }
            return -1;
        }

        //插入数据项
        public int add(Integer newDataItem) {
            size++;
            for (int i = CHILD_NODE_COUNT - 2; i >= 0; i--) {
                if (dataItems[i] == null) {
                    continue;
                }
                if (newDataItem < dataItems[i]) {
                    dataItems[i + 1] = dataItems[i];
                } else {
                    dataItems[i + 1] = newDataItem;
                    return i + 1;
                }
            }
            dataItems[0] = newDataItem;
            return 0;
        }

        //删除大数据项
        public Integer removeData() {
            Integer removeDataItem = dataItems[size - 1];
            dataItems[size - 1] = null;
            size--;
            return removeDataItem;
        }
    }
}
