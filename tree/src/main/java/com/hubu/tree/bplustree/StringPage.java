package com.hubu.tree.bplustree;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
class StringPage {
    //字节数目
    static class StringPageHeader{
        //记录的数目
        private int recordNumber;
        //当前页的使用情况
        private int useSize;
        //当前页是否写满了
        private boolean isFull;

        //前驱页
        private long prevPageNumber;

        //后继页
        private long nextPageNumber;

        AbstractMap.SimpleEntry<String,String> minRecord;
    }
    private int byteLength;
    private int pageSize;
    boolean isLeaf;
    boolean isRoot;
    List<Map.Entry<String, String>> entryList;
    List<StringPage> children;
    StringPage parent;
    StringPage prev;
    StringPage next;
    public StringPage(boolean isRoot, boolean isLeaf) {
        this.isRoot = isRoot;
        this.isLeaf = isLeaf;
        entryList = new ArrayList<>();
        if (!isLeaf) {
            this.children = new ArrayList<>();
        }
    }
}
