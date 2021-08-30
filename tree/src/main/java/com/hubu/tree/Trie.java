package com.hubu.tree;
import java.util.*;
public class Trie {
    private Node root=new Node();
    public void add(String value){
        if(value==null||value.equals("")){
            return ;
        }
        Node node=root;
        for(int i=0;i<value.length();i++){
            Character itemKey=value.charAt(i);
            if(node.children==null){
                node.children=new HashMap<>();
                Node newNode=new Node();
                node.children.put(itemKey,newNode);
                node=newNode;
            }
            else if(node.children.containsKey(itemKey)){
                node=node.children.get(itemKey);
                continue;
            }
            else{
                Node newNode=new Node();
                node.children.put(itemKey,newNode);
                node=newNode;
            }
        }
        node.isWord=true;
    }
    private boolean isEmpty(String value){
        return value==null||value.equals("")||value.length()==0;
    }
    /**
     *
     * 逻辑删除
     * @param value
     */
    public void remove(String value){
        Node node=root;
        if(isEmpty(value)) return ;
        for(int i=0;i<value.length();i++){
            Character itemKey=value.charAt(i);
            if(itemKey==null){
                return ;
            }
            node=node.children.get(itemKey);
        }
        node.isWord=false;
    }
    /**
     *
     * 返回以value开头的所有字符串
     * @param value
     * @return
     */
    public List<String> returnAllPrev(String value){
        Node node = getPrefixNode(value);
        List<Character> list=new ArrayList<>();
        List<String> stringList=new ArrayList<>();
        dfs(node,stringList,list,value);
        return stringList;
    }
    private Node getPrefixNode(String value){
        Node node=root;
        for(int i=0;i<value.length();i++){
            Character itemKey=value.charAt(i);
            node=node.children.get(itemKey);
        }
        return node;
    }

    private void dfs(Node node,List<String> stringList, List<Character> list,String value){
        if(node.isWord&&node.children!=null){
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(value);
            for(int i=0;i<list.size();i++){
                stringBuffer.append(list.get(i));
            }
            stringList.add(stringBuffer.toString());
        }
        if(node.children==null){
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(value);

            for(int i=0;i<list.size();i++){
                stringBuffer.append(list.get(i));
            }
            stringList.add(stringBuffer.toString());
            return ;
        }
        Set<Character> keySet = node.children.keySet();
        for(Character item:keySet){
            //当前节点的value存储在子节点集合的Map的关键字key中
            Node next = node.children.get(item);
            list.add(item);
            dfs(next,stringList,list,value);
            list.remove(list.size()-1);
        }
    }
    static class Node{
        //判断到当前节点是否是一个完整的单词
        boolean isWord;
        Map<Character,Node> children=null;
    }


    /**
     *
     * 是否包含以value为前缀的字符串
     * @param value
     * @return
     */
    public boolean prefix(String value){
        if(value==null||value.equals("")||value.length()==0){
            return false;
        }
        Node node=root;
        for(int i=0;i<value.length();i++){
            Character itemKey=value.charAt(i);
            if(!node.children.containsKey(itemKey)){
                return false;
            }
            node=node.children.get(itemKey);
        }
        return true;
    }
    public boolean contains(String value){
        if(value==null||value.equals("")||value.length()==0){
            return false;
        }
        Node node=root;
        for(int i=0;i<value.length();i++){
            Character itemKey=value.charAt(i);
            if(!node.children.containsKey(itemKey)){
                return false;
            }
            node=node.children.get(itemKey);
        }
        return node.isWord;
    }
}
