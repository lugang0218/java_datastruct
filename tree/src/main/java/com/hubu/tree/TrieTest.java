package com.hubu.tree;
import java.util.List;
public class TrieTest {
    public static void main(String[] args) {
        Trie tree = new Trie();
        tree.add("hello");
        tree.add("helloabc");
        tree.add("heabc");
        tree.add("he");
        tree.add("hel");
        tree.add("hell");
        tree.add("hello");
        tree.add("abc");
        tree.add("abd");
        tree.add("agf");
        tree.add("a");
        tree.add("acm");
        tree.add("ab");
        List<String> list = tree.returnAllPrev("he");
        for (String item : list) {
            System.out.println(item);
        }
        System.out.println("以a开始的所有单词");
        List<String> list2 = tree.returnAllPrev("a");
        for (String item : list2) {
            System.out.println(item);
        }
    }
}
