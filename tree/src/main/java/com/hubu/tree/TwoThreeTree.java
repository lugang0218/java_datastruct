package com.hubu.tree;
public class TwoThreeTree {
    /**
     * 23树节点类
     */
    Node root=null;
    public void add(int value){
        Data data=new Data(value);
        //如果root为空 创建root，直接将数据添加到root节点
        Node currentNode=root;
        if(currentNode==null){
            currentNode=new Node();
            currentNode.add(data);
            root=currentNode;
        }
        //如果没有子节点，就插入到当前节点
        else if(currentNode.getNodeArray()==null){
            if(currentNode.isFull()){
                int index=currentNode.searchFullIndex(data);
                if(index==0){
                    //将array【0】向上合并
                    if(currentNode.getParent()==null){
                        Node parent=new Node();
                        currentNode.parent=parent;
                        parent.add(data);
                    }
                    currentNode.dataArray[index]=data;
                }
                else if(index==1){
                    //将array[1]向上合并
                    if(currentNode.getParent()==null){
                        Node parent=new Node();
                        currentNode.parent=parent;
                        parent.add(data);
                    }
                    currentNode.dataArray[index]=data;

                }
                else{
                    //将当前元素向上合并
                    if(currentNode.getParent()==null){
                        Node parent=new Node();
                        currentNode.parent=parent;
                        parent.add(data);
                    }
                }
            }
            else{
                currentNode.add(data);
            }
        }

    }
    static class Node{
        //最大子节点的数量
        static final int MAX_NODE_SIZE=3;

        //最大数据的数量
        static final int MAX_DATA_SIZE=MAX_NODE_SIZE-1;

        int dataSize=0;//数据的使用量


        int nodeSize;//子节点数组的使用量


        Data[] dataArray=new Data[MAX_DATA_SIZE];


        Node nodeArray[]=new Node[MAX_NODE_SIZE];



        Node parent;


        public Node getParent() {
            return parent;
        }

        public boolean isFull(){
            return dataSize==MAX_DATA_SIZE;
        }

        //从当前节点的dataArray中查找data
        public int  findData(int data){
            for(int i=0;i<dataSize;i++){
                if(dataArray[i]==null){
                    break;
                }
                else if(dataArray[i].getValue()!=data){
                    continue;
                }
                else{
                    return i;
                }

            }
            //如果寻找过程中出现null或者找到最后还能来到这儿，说明array不存在
            return -1;
        }




        //数组的范围[0,1]
        /**
         * 将数据添加到当前节点的数组中
         * @param data
         */
        public void add(Data data){
            int index=0;
            for(int i=MAX_DATA_SIZE-1;i>=0;i--){
                if(dataArray[i]==null){
                    continue;
                }
                else if(data.getValue()>dataArray[i].getValue()){
                    //i就是要插入的位置
                    index=i+1;
                    break;
                }
                else{
                    dataArray[i+1]=dataArray[i];
                    index=i;
                }
            }
            dataArray[index]=data;
            dataSize++;
        }

        public Node[] getNodeArray() {
            return nodeArray;
        }



        //从已满数组中获取插入位置
        public int searchFullIndex(Data data) {
            int findIndex=-1;
            for(int i=0;i<dataArray.length;i++){
                if(data.getValue()==dataArray[i].getValue()){
                    throw new RuntimeException("数据不能重复");
                }
                else if(data.getValue()<dataArray[i].getValue()){
                    findIndex=i;
                }
            }
            return findIndex;//如果返回-1 表明插入到末尾 1表明插入到中间 0表示插入到首部
        }
    }


    /**
     * 数据包装类
     */
    static class Data{
        private int value;
        public Data(int value){
            this.value=value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public static void main(String[] args) {
        TwoThreeTree twoThreeTree = new TwoThreeTree();
        twoThreeTree.add(12);
        twoThreeTree.add(11);
    }

}
