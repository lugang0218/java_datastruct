package com.hubu.bfs;

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
public class IsSameTree {


    public boolean isSymmetric(TreeNode root) {
        if(root==null||(root.left==null&&root.right==null)){
            return true;
        }
        return doIsSymmetric(root.left,root.right);
    }

    private boolean doIsSymmetric(TreeNode p,TreeNode q) {
        if(p==null&&q==null){
            return true;
        }
        if(p==null||q==null||p.val!=q.val){
            return false;
        }
        return doIsSymmetric(p.left,q.right)&&doIsSymmetric(p.right,q.left);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null&&q==null){
            return true;
        }
        if(p!=null&&q!=null){
            if(p.val==q.val){
                if(p.left!=null&&q.left!=null){
                    return isSameTree(p.left,q.left);
                }
                if(p.right!=null&&q.right!=null){
                    return isSameTree(p.right,q.right);
                }
                if(p.left==null&&q.left==null){
                    return isSameTree(p.right,q.right);
                }
                if(p.right==null&&q.right==null){
                    return isSameTree(p.left,q.left);
                }
                return false;
            }
            else{
                return false;
            }

        }
        else{
            return false;
        }
    }




    public static void main(String[] args) {
        TreeNode treeNode1=new TreeNode(1);
        TreeNode treeNode2=new TreeNode(2);
        TreeNode treeNode3=new TreeNode(2);
        treeNode1.left=treeNode2;
        treeNode1.right=treeNode3;
        IsSameTree isSameTree=new IsSameTree();
        isSameTree.isSymmetric(treeNode1);
    }
}
