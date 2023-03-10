package com.hubu.tree;
import com.hubu.tree.printer.BinaryTreeInfo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
public class RedBlackTree<K,V> implements BinaryTreeInfo , Serializable {
    private int size;
    private Node<K,V> root=null;
    private Comparator<K> comparator;
    public RedBlackTree(Comparator<K> comparator){
        this.comparator=comparator;
    }
    private void leftRotate(Node<K,V> node){
        Node<K,V> rightNode=node.right;
        if(rightNode.left!=null){
            rightNode.left.parent=node;
        }
        rightNode.parent=node.parent;
        node.right=rightNode.left;
        if(node.parent==null){
            this.root=rightNode;
        }
        else if(node==node.parent.left){
            node.parent.left=rightNode;
        }
        else if(node==node.parent.right){
            node.parent.right=rightNode;
        }
        rightNode.left=node;
        node.parent=rightNode;
    }

    public int size(){
        return size;
    }
    private void rightRotate(Node<K,V> node){
        Node<K,V> leftNode=node.left;
        if(leftNode.right!=null){
            leftNode.right.parent=node;
        }
        leftNode.parent=node.parent;
        node.left=leftNode.right;

        if(node.parent==null){
            root=leftNode;
        }
        else if(node.parent.left==node){
            node.parent.left=leftNode;
        }
        else if(node.parent.right==node){
            node.parent.right=leftNode;
        }
        leftNode.right=node;
        node.parent=leftNode;

    }
    public Node<K, V> getRoot() {
        return root;
    }
    public Node<K,V> parentNode(Node<K,V> node){
        return node!=null?node.parent:null;
    }
    public void preOrder(){
        if(this.root==null){
            return ;
        }
        else doPreOrder(root);
    }

    private void doPreOrder(Node<K, V> node) {
        System.out.println("key:"+node.key+","+"value"+node.value);
        if(node.left!=null){
            doPreOrder(node.left);
        }
        if(node.right!=null){
            doPreOrder(node.right);
        }
    }
    public void put(K key,V value){

        //??????????????????????????????
        if(root==null){
            size++;
            root=new Node<>(key,value,null,null,null,true);
            return;
        }
        int compare=0;
        Node<K,V> current=root;
        Node<K,V> parent=null;
        while(current!=null) {
            parent = current;
            compare = compare(key, current.key);
            if (compare > 0) {
                current = current.right;
            }
            //????????????????????????????????????
            else if(compare==0){
                current.value=value;
                return ;
            }
            else {
                current = current.left;
            }
        }
        Node<K,V> newNode=new Node<K,V>(key,value,parent,null,null,true);
        if(compare>0){
            parent.right=newNode;
        }
        else{
            parent.left=newNode;
        }
        putAfter(newNode);
        size++;
    }

    private Node<K,V> leftOf(Node<K,V> node) {
        return node != null ? node.left : null;
    }

    private Node<K,V> rightOf(Node<K,V> node) {
        return node != null ? node.right : null;
    }

    private void setColor(Node<K,V> node, boolean color){
        if(node!=null){
            node.color=color;
        }
    }
    //???????????????????????????
    private boolean colorOf(Node node) {
        return node == null ? BLACK : node.color;
    }
    //??????????????????????????????????????????????????????????????????
    private void afterPut(Node<K,V> node){
        if(node!=null){
            node.color=RED;
            while(node!=root&&parentNode(node).color==RED){
                if(parentNode(node)==leftOf(parentNode(parentNode(node)))){
                    Node<K,V> uncle=rightOf(parentNode(parentNode(node)));
                    if(colorOf(uncle)==RED){
                        setColor(uncle,BLACK);
                        setColor(parentNode(node),BLACK);
                        setColor(parentNode(parentNode(node)),RED);
                        node=parentNode(parentNode(node));
                    }

                    else{
                        if(node==rightOf(parentOf(node))){
                            leftRotate(parentNode(node));
                        }
                        setColor(parentNode(node),BLACK);
                        setColor(parentNode(parentNode(node)),RED);
                        rightRotate(parentNode(parentNode(node)));
                    }
                }

                else {
                    Node<K,V> uncle=leftOf(parentNode(parentNode(node)));
                    if(colorOf(uncle)==RED){
                        setColor(uncle,BLACK);
                        setColor(parentNode(node),BLACK);
                        setColor(parentNode(parentNode(node)),RED);
                        node=parentNode(parentNode(node));
                    }

                    else{
                        if(node==leftOf(parentNode(node))){
                            rightRotate(parentNode(node));
                        }
                        setColor(parentNode(node),BLACK);
                        setColor(parentNode(parentNode(node)),RED);
                        leftRotate(parentNode(parentNode(node)));
                    }
                }
            }
        }
    }

    private Node<K, V> parentOf(Node<K, V> node) {
        return node!=null?node.parent:null;
    }

    private void removeFix(Node<K,V> node){
        while(node!=root&&colorOf(node)==BLACK){
            //????????????node????????????
            if(node==leftOf(parentNode(node))){
                //??????????????????
                Node<K,V> slider=rightOf(parentNode(node));

                //???????????????????????????(???????????????????????????????????? ?????????????????????????????????????????? ???????????????????????????????????????) ????????????
                if (colorOf(slider) == RED) {
                    setColor(slider, BLACK);
                    setColor(parentNode(node), RED);
                    leftRotate (parentNode(node));

                    //???slider???????????????????????????
                    slider = rightOf(parentNode(node));
                }

                //?????????????????????????????????????????? ?????????????????? ????????????
                if(colorOf(leftOf(slider))==BLACK&&colorOf(rightOf(slider))==BLACK){
                    setColor(slider, RED);//???????????????????????????
                    node = parentNode(node);
                }

                //???????????????????????????
                else{
                    //?????????????????? ??????????????????
                    //????????????????????? ????????????????????????
                    if(colorOf(rightOf(slider))==BLACK){
                        //????????????????????????
                        setColor(slider,RED);
                        //??????????????????????????????
                        setColor(leftOf(slider),BLACK);
                        //???????????????????????????
                        rightRotate(slider);

                        //??????????????????
                        slider=rightOf(parentNode(node));
                    }
                    //????????????????????????????????????????????????
                    setColor(slider,colorOf(parentNode(node)));//??????????????????????????????????????????
                    setColor(parentNode(node),BLACK);//?????????????????????
                    setColor(rightOf(slider), BLACK);//???????????????????????????????????????
                    rightRotate(parentNode(node));//??????????????????
                    //????????????
                    node=root;
                }
            }

            else { // symmetric
                Node <K,V> sib = leftOf(parentNode(node));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentNode(node), RED);
                    rightRotate(parentNode(node));
                    sib = leftOf(parentNode(node));
                }

                if (colorOf(rightOf(sib)) == BLACK &&
                        colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    node = parentNode(node);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        leftRotate(sib);
                        sib = leftOf(parentNode(node));
                    }
                    setColor(sib, colorOf(parentNode(node)));
                    setColor(parentNode(node), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rightRotate(parentNode(node));
                    node = root;
                }
            }
        }
        setColor(node,BLACK);
    }
    private void putAfter(Node<K,V> node){
        //?????????????????????????????????
        node.color=RED;
        while(node!=null&&node!=root&&node.parent.color== RED){
            //????????????
            //?????????????????????????????????
            Node<K,V> parent=parentNode(node);
            Node<K,V> grand=parent.parent;
            //?????????????????????????????????????????????
            if(parent==leftOf(grand)){
                //??????????????????
                Node<K,V> uncle=parentNode(parentNode(node)).right;
                //RED????????????????????????????????? ??????????????????????????? ???????????????????????? ???????????? ????????????
                if(colorOf(uncle)==RED){
                    //?????????????????????????????????
                    setColor(uncle,BLACK);

                    //???parent??????????????????
                    setColor(parent,BLACK);

                    //?????????????????????????????????
                    setColor(grand,RED);
                    //??????????????????????????????
                    node=grand;
                }
                else{

                    //??????????????????????????????????????? ????????????????????????
                    if(node==rightOf(parent)){
                        //???????????????????????????????????????????????????????????????
                        leftRotate(parent);
                    }

                    //???parent?????? ???????????? ????????????
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //??????????????????
                    rightRotate(grand);
                }

            }
            //???????????????????????????????????????
            else{
                //??????????????????
                Node<K,V> uncle=parentNode(parentNode(node)).left;

                if(colorOf(uncle)==RED){
                    setColor(uncle,BLACK);
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //??????????????????????????????
                    node=grand;
                }

                else{

                    //??????????????????????????????????????? ????????????????????????
                    if(node==leftOf(parent)){
                        //???????????????????????????????????????????????????????????????
                        rightRotate(parent);
                    }

                    //???parent?????? ????????????
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //??????????????????
                    leftRotate(grand);
                }
            }
        }
        root.color=BLACK;
    }
    /**
     * ?????????comparator ?????????comparator?????????????????????
     * @param key1
     * @param key2
     * @return
     */
    private int compare(K key1, K key2) {

        if(comparator!=null){
            return comparator.compare(key1,key2);
        }
        return ((Comparable<K>)key1).compareTo(key2);

    }
    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<K,V>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<K,V>)node).right;
    }

    @Override
    public Object string(Object node) {
        String value=((Node<K,V>)node).value.toString();
        return ((Node<K,V>)node).color==BLACK?"???"+value:"???"+value;
    }

    public V set(K key, V value) {
        Node<K, V> node = findNode(key);
        if(node==null){
            throw new RuntimeException("Not have such key");
        }
        V oldValue=node.value;
        node.value=value;
        return oldValue;
    }

    static class Node<K,V> implements Serializable{
        K key;
        V value;
        Node<K,V> parent;
        Node<K,V> left;
        Node<K,V> right;
        boolean color = BLACK;
        public Node(K key,V value,Node<K,V> parent,Node<K,V> left,Node<K,V> right,boolean color){
            this.key=key;
            this.value=value;
            this.parent=parent;
            this.left=left;
            this.right=right;
            this.color=color;
        }
        public boolean isColor() {
            return color;
        }

        public V getValue() {
            return value;
        }

        public Node<K, V> getLeft() {
            return left;
        }

        public Node<K, V> getRight() {
            return right;
        }

        public Node(K key , V value, Node<K,V> parent){
            this.color=BLACK;
            this.key=key;
            this.value=value;
            this.parent=parent;
            this.right=null;
            this.left=null;
        }
    }
    final Node<K,V> getFirstNode() {
        Node<K,V> p = root;
        if (p != null)
            while (p.left != null)
                p = p.left;
        return p;
    }
    final Node<K,V> getLastNode() {
        Node<K,V> p = root;
        if (p != null)
            while (p.right != null)
                p = p.right;
        return p;
    }
    private static <K,V> Node<K,V> successor(Node<K,V> node) {
        if (node == null)
            return null;
        else if (node.right != null) {
            Node<K,V> p = node.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Node<K,V> p = node.parent;
            Node<K,V> ch = node;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private static <K,V> Node<K,V> predecessor(Node<K,V> node) {
        if (node == null)
            return null;
        else if (node.left != null) {
            Node<K,V> p = node.left;
            while (p.right != null)
                p = p.right;
            return p;
        } else {
            Node<K,V> p = node.parent;
            Node <K,V> ch = node;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }
    public void remove(K key){
        if(key==null){
            throw new NullPointerException("key="+key);
        }
        Node<K,V> node=findNode(key);
        if(node!=null){
            remove(node);
        }
    }
    public boolean containsKey(K key){
        return findNode(key)!=null;
    }
    public boolean containsValue(V value){
        if(this.root==null){
            return false;
        }
        Node<K,V> current=this.root;
        Queue<Node<K,V>> nodeQueue=new LinkedList<>();


        nodeQueue.offer(current);
        while(!nodeQueue.isEmpty()){
            Node<K, V> node = nodeQueue.poll();
            if(node.getValue().equals(value)){
                return true;
            }
            if(node.left!=null){
                nodeQueue.offer(node.left);
            }
            if(node.right!=null){
                nodeQueue.offer(node.right);
            }
        }
        return false;
    }
    private Node<K,V> findNode(K key){
        Node<K,V> current=root;
        int compare=0;
        while(current!=null){
            compare=compare(key,current.key);
            if(compare>0){
                current=current.right;
            }
            else if(compare==0){
                return current;
            }
            else{
                current=current.left;
            }
        }
        return null;
    }
    private void remove(Node<K,V> p) {
        size--;
        if (p.left != null && p.right != null) {
            Node <K,V> s = successor(p);
            p.key = s.key;
            p.value = s.value;
            p = s;
        }
        Node <K,V> replacement = (p.left != null ? p.left : p.right);
        //?????????????????????
        if (replacement != null) {
            //?????????????????????
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left  = replacement;
            else
                p.parent.right = replacement;
            p.left = p.right = p.parent = null;

            if(p.color==BLACK){
                afterRemove(replacement);
            }
        } else if (p.parent == null) {
            root = null;
        } else {
            //???????????????????????????????????? ???p.left==p.right==null p?????????????????? ????????????????????????
            if(p.color==BLACK){
                afterRemove(p);
            }
            if (p.parent != null) {
                if (p == p.parent.left)
                    p.parent.left = null;
                else if (p == p.parent.right)
                    p.parent.right = null;
                p.parent = null;
            }
        }
    }
    /**
     * put key???value??????????????????
     * @param key
     * @return
     */
    public V get(K key){
        Node<K, V> node = findNode(key);
        if(node!=null){
            return node.value;
        }
        return null;
    }
    //?????????????????????
    private void afterRemove(Node<K,V> x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentNode(x))) {
                Node<K,V> sib = rightOf(parentNode(x));
                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentNode(x), RED);
                    leftRotate (parentNode(x));
                    sib = rightOf(parentNode(x));
                }
                if (colorOf(leftOf(sib))  == BLACK &&
                        colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentNode(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rightRotate(sib);
                        sib = rightOf(parentNode(x));
                    }
                    setColor(sib, colorOf(parentNode(x)));
                    setColor(parentNode(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    leftRotate(parentNode(x));
                    x = root;
                }
            }
            else { // symmetric
                Node <K,V> sib = leftOf(parentNode(x));
                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentNode(x), RED);
                    rightRotate(parentNode(x));
                    sib = leftOf(parentNode(x));
                }

                if (colorOf(rightOf(sib)) == BLACK &&
                        colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentNode(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        leftRotate(sib);
                        sib = leftOf(parentNode(x));
                    }
                    setColor(sib, colorOf(parentNode(x)));
                    setColor(parentNode(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rightRotate(parentNode(x));
                    x = root;
                }
            }
        }
        setColor(x, BLACK);
    }
    public int height(){
        if(root==null){
            return 0;
        }
        return doHeight(root);
    }
    //???????????????
    private int doHeight(Node<K,V> node){
        if(node==null){
            return 0;
        }
        else if(node.left==null&&node.right==null){
            return 1;
        }
        return Math.max(doHeight(node.left),doHeight(node.right))+1;
    }
}