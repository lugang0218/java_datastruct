package com.hubu.tree;
import java.util.List;
public class TrieTest {
    public static void main(String[] args) {
        Trie tree = new Trie();
        tree.add("hello");
        List<String> a = tree.getAllWorldsByPrefix("a");
        if(a!=null){
            System.out.println("查找到");
        }
    }
}
