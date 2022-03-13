package com.hubu.tree.bplustree;

public class BPlusTreeChecker<K,V> {
    private boolean isValid = true;
    private BPlusTree<K,V> tree;
    public BPlusTreeChecker(BPlusTree<K,V> tree){
        this.tree = tree;
    }
    public boolean  isValid(){
        isValid(tree.getRoot());
        return isValid;
    }
    private void isValid(BPlusTree.Node<K,V> node){
        if(node.isLeaf){
            if(node.isRoot){
                for(int i=0;i<node.entryList.size()-1;i++){

                    int compare=tree.compare(node.entryList.get(i).getKey(),node.entryList.get(i+1).getKey());
                    if(compare>0){
                        isValid=false;
                    }
                }
            }
            else{
                if(node.entryList.size()<tree.minSize(tree.getOrder())||node.entryList.size()>tree.maxSize(tree.getOrder())){
                    isValid=false;
                }
                for(int i=0;i<node.entryList.size()-1;i++){
                    int compare=tree.compare(node.entryList.get(i).getKey(),node.entryList.get(i+1).getKey());
                    if(compare>0){

                        isValid=false;

                    }
                }
            }
        }
        else{
            //检查当前节点
            if(node.entryList.size()+1!=node.children.size()){
                isValid=false;
            }
            if(!node.isRoot){
                if(node.entryList.size()<tree.minSize(tree.getOrder())||node.entryList.size()>tree.maxSize(tree.getOrder())){
                    isValid= false;
                }
            }
            if(node.children.size()>tree.maxSize(tree.getOrder()+1)){

                isValid= false;
            }
            for(int i=0;i<node.entryList.size()-1;i++){
                int compare=tree.compare(node.entryList.get(i).getKey(),node.entryList.get(i+1).getKey());
                if(compare>0){
                    System.out.println("error number");
                    isValid= false;
                    break;
                }
            }
            for(BPlusTree.Node<K,V> item:node.children){
                if(item.parent!=node){
                    isValid=false;
                    break;
                }
            }
            /**
             * 递归检查所有的子节点
             */
            for(BPlusTree.Node item:node.children){
                isValid(item);
            }
        }
    }
}
