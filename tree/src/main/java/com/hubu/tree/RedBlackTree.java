package com.hubu.tree;
import com.hubu.tree.printer.BinaryTreeInfo;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
public class RedBlackTree<K,V> implements BinaryTreeInfo {
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
        size++;
        //根节点不用做任何操作
        if(root==null){
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
            //相等更新值就行不需要调整
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
    //空节点的颜色是黑的
    private boolean colorOf(Node node) {
        return node == null ? BLACK : node.color;
    }
    //插入之后调整，能来到这儿的节点一定不是根节点
    private void putAfter(Node<K,V> node){
        //插入的节点都是红色节点
        node.color=RED;
        while(node!=null&&node!=root&&node.parent.color== RED){
            //裂变情况
            //如果父亲是爷爷的左孩子
            Node<K,V> parent=parentNode(node);
            Node<K,V> grand=parent.parent;
            //如果父亲节点是爷爷节点的左节点
            if(parent==leftOf(grand)){
                //获取叔叔节点
                Node<K,V> uncle=parentNode(parentNode(node)).right;
                //RED说明叔叔节点一定不为空 空节点颜色是黑色的 与四节点进行合并 需要分裂 递归操作
                if(colorOf(uncle)==RED){
                    //将叔叔节点设置成为黑色
                    setColor(uncle,BLACK);

                    //将parent设置成为黑色
                    setColor(parent,BLACK);

                    //将爷爷节点设置成为红色
                    setColor(grand,RED);
                    //爷爷节点继续递归操作
                    node=grand;
                }
                else{

                    //如果当前节点是父亲的右节点 并且没有叔叔节点
                    if(node==rightOf(parent)){
                        //沿着父节点左旋先左旋一下变成标准的左三情况
                        leftRotate(parent);
                    }

                    //将parent变黑 爷爷变红 退出循环
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //沿着爷爷右旋
                    rightRotate(grand);
                }

            }
            //如果父亲是爷爷节点的右节点
            else{
                //获取叔叔节点
                Node<K,V> uncle=parentNode(parentNode(node)).left;

                if(colorOf(uncle)==RED){
                    setColor(uncle,BLACK);
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //爷爷节点继续递归操作
                    node=grand;
                }

                else{

                    //如果当前节点是父亲的左节点 并且没有叔叔节点
                    if(node==leftOf(parent)){
                        //沿着父节点左旋先右旋一下变成标准的右三情况
                        rightRotate(parent);
                    }

                    //将parent变黑 爷爷变红
                    setColor(parent,BLACK);
                    setColor(grand,RED);
                    //沿着爷爷左旋
                    leftRotate(grand);
                }
            }
        }
        root.color=BLACK;
    }



    private void removeFix(Node<K,V> node){
        while(node!=root&&colorOf(node)==BLACK){
            //如果当前node是左孩子
            if(node==leftOf(parentNode(node))){
                //获取兄弟节点
                Node<K,V> slider=rightOf(parentNode(node));

                //找当真正的兄弟节点(如果此时的兄弟节点是红色 说明真正的兄弟节点在下边一层 因为红色节点会向上一层合并) 需要旋转
                if (colorOf(slider) == RED) {
                    setColor(slider, BLACK);
                    setColor(parentNode(node), RED);
                    leftRotate (parentNode(node));

                    //让slider指向真正的兄弟节点
                    slider = rightOf(parentNode(node));
                }

                //如果兄弟节点的左右节点都为空 牺牲兄弟节点 递归处理
                if(colorOf(leftOf(slider))==BLACK&&colorOf(rightOf(slider))==BLACK){
                    setColor(slider, RED);//让兄弟节点改成红色
                    node = parentNode(node);
                }

                //兄弟节点能够借节点
                else{
                    //如果是三节点 旋转操作一下
                    //如果右节点为空 说明一定有左节点
                    if(colorOf(rightOf(slider))==BLACK){
                        //先将兄弟节点变红
                        setColor(slider,RED);
                        //兄弟节点的左节点变黑
                        setColor(leftOf(slider),BLACK);
                        //对兄的节点进行右旋
                        rightRotate(slider);

                        //更新兄弟节点
                        slider=rightOf(parentNode(node));
                    }
                    //三节点和四节点的公共代码来到这儿
                    setColor(slider,colorOf(parentNode(node)));//将兄弟节点染成父亲节点的颜色
                    setColor(parentNode(node),BLACK);//将父亲染成黑色
                    setColor(rightOf(slider), BLACK);//将兄弟节点的右孩子染成黑色
                    rightRotate(parentNode(node));//围着父亲右旋
                    //退出循环
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

    /**
     * 如果有comparator 就调用comparator比较器进行比较
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
        return ((Node<K,V>)node).color==BLACK?"黑"+value:"红"+value;
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

    static class Node<K,V>{
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
        //替换节点不为空
        if (replacement != null) {
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
            //调整需要找兄弟节点的节点 即p.left==p.right==null p不能自我修复 需要寻找兄弟节点
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
     * put key和value都不允许为空
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
    //删除之后做调整
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
                    rightRotate(parentNode(x));
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


    //求树的高度

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