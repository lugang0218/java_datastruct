package com.hubu.tree;


/**
 *
 *
 * B+树模拟实现
 *
 *
 *
 */

public class MyBPlusTree {

    private int order=0;



    private Node root=null;
    public MyBPlusTree(){

        /**
         * 默认3阶
         */
        this(3);
    }
    public MyBPlusTree(int order){
        this.order=order;
        this.root=new LeafNode(order+1);
    }





    public void add(int value){
        root.add(value);
    }



    abstract static class Node{
        abstract void add(int  value);
    }


    /**
     *
     *
     * 非叶子节点类，后面调整，只用于存储关键字key
     */
    static class BPlusNode extends Node{
        int maxNumber;
        int size =0;
        int []dataArray=null;
        Node []children=null;
        private Node parent=null;



        public BPlusNode(int maxNumber){
            this.maxNumber=maxNumber;
            this.dataArray=new int[this.maxNumber];
            this.children=new BPlusNode[this.maxNumber];
        }

        @Override
        void add(int value) {

        }


        /**
         *
         *
         * 子节点添加满拆分之后递归的向上合并
         * @param left 传递进来的左节点
         * @param right 传递进来的右节点
         * @param value 插入当前父节点的value，后面可以调整为关键字key，
         *              因为父节点不存储具体的数据，负责存储索引
         */
        void add(Node left,Node right,int value){



            /**
             * 如果是第一次添加
             */
            if(this.size==0){
                for(int i=0;i<this.maxNumber;i++){
                    this.children[i]=new BPlusNode(this.maxNumber);
                }
                this.dataArray[0]=value;
                this.children[0]=left;
                this.children[1]=right;
            }
        }
    }

    /**
     *
     * 叶子节点类，用于存储数据
     */
    static class LeafNode extends Node{
        /**
         * 数据项的最大个数，包含溢出，比如三阶B+树的最大为4，用于标记，
         * 达到4就要拆分当前节点,同时也可以代表子节点的最大个数
         */
        private int maxNumber;


        int size =0;


        private  int []dataArray=null;


        private LeafNode []children=null;

        private Node parent=null;




        //右指针为空
        LeafNode left=null;



        //左指针为空
        LeafNode right=null;

        public LeafNode(int maxNumber){
            this.maxNumber=maxNumber;
            this.dataArray=new int[maxNumber];
            this.children=new LeafNode[maxNumber];
        }


        /**
         *
         *
         *
         * 从叶子节点添加value，现在不考虑键值key的情况，直插入int类型的值
         * @param value
         */
        @Override
        void add(int value){
            int i=0;
            while(i<maxNumber){
                /**
                 * 找到合适的插入位置
                 */

                if(i>=size){
                    break;
                }
                else if(value<dataArray[i]){
                    break;
                }
                else{
                    i++;
                }
            }
            /**
             *
             * i就是要插入的位置，移动后面的元素，插入i
             */
            for(int k=size;k>i;k--){
                dataArray[k]=dataArray[k-1];
            }
            dataArray[i]=value;
            size++;
            /**
             *
             * 如果元素个数达到了maxNumber,就要进行节点的拆分
             */



            if(size ==maxNumber){


                /**
                 *
                 *
                 * 获取分裂到父节点的值
                 */

                int toParentValue=this.dataArray[2];

                /**
                 *
                 * 分裂右节点
                 */
                int mid=maxNumber/2;
                LeafNode rightNode=new LeafNode(this.maxNumber);
                int count=0;
                for(int j=mid;j<maxNumber;j++){
                    rightNode.dataArray[count++]=dataArray[j];
                    rightNode.size++;
                }

                /**
                 * 将当前节点变为左节点,当前节点的size缩小范围访问
                 */
                int []oldDataArray=this.dataArray;
                this.dataArray=new int[this.maxNumber];

                /**
                 * 重新调整size
                 */
                this.size=maxNumber-mid;
                for(int k=0;k<size;k++){
                    this.dataArray[k]=oldDataArray[k];
                }
                /**
                 * 设置左右指针
                 */
                this.right=rightNode;
                rightNode.left=this;

                /**
                 * 创建父节点
                 *
                 */
                if(this.parent==null){
                    BPlusNode parent=new BPlusNode(maxNumber);
                    this.parent=parent;
                    right.parent=parent;
                }
                ((BPlusNode)parent).add(left,right,toParentValue);
            }
        }
        /**
         *
         * 该方法用于子节点添加满之后，向父节点添加
         */
    }
}
