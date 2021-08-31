package com.hubu.trie;
/**
 * 前缀树的简单实现 只能用于小写字母 且不能包含空格 这种设计方式十分局限
 */
public class PrefixTrie {
    private Node root=new Node('/');
    public void add(String value){
        Node node=root;
        for(int i=0;i<value.length();i++){
            char ch = value.charAt(i);
            if(node.children==null) {
                node.children = new Node[26];
                Node newNode = new Node(ch);
                node.children[ch - 'a'] = newNode;
                node = newNode;
            }else if(node.children[ch-'a']!=null){
                node=node.children[ch-'a'];
            }else{
                Node newNode=new Node(ch);
                node.children[ch-'a']=newNode;
            }
        }
        node.isWorld=true;
    }
    public boolean contains(String value){
        Node node=root;
        for(int i=0;i<value.length();i++){
            char ch=value.charAt(i);
            if(node.children[ch-'a']==null){
                return false;
            }
            node=node.children[ch-'a'];
        }
        return node.isWorld;
    }
    static class Node{
        char value;
        int childSize;
        boolean isWorld=false;
        Node [] children;
        public Node(char ch){
            this.value=ch;
        }
    }
}
