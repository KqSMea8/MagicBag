package org.github.helixcs.algorithm;

import javax.transaction.TransactionRequiredException;

/**
 * @Author: helix
 * @Time:9/11/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class TreeList<T extends Comparable> {

    // 树结构
    private static class TreeNode<T> {
        private T data;
        private TreeNode<T> left;
        private TreeNode<T> right;
        private TreeNode<T> parent;

        public TreeNode() {
        }

        public TreeNode(T data) {
            this.data = data;
        }

        public TreeNode(T data, TreeNode<T> parent) {
            this.data = data;
            this.parent = parent;
        }

        public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public TreeNode(TreeNode<T> left, TreeNode<T> right, TreeNode<T> parent) {
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    TreeNode<T> root;


    private TreeNode<T> addWithRecursive(TreeNode<T> currentNode , T value){
        // 当根节点为空，返回新的根节点
        if(currentNode==null){return new TreeNode(value);}
        int cmp= currentNode.data.compareTo(value);
        if(cmp>0){
            currentNode.left = addWithRecursive(currentNode.left,value);
        }else if(cmp<0){
            currentNode.right = addWithRecursive(currentNode.right,value);
        }else {
            return currentNode;
        }
        return currentNode;

    }

    private boolean findWithRecursive(TreeNode<T> currentNode, T value){
        if(null==currentNode){return false;}
        int cmp = currentNode.data.compareTo(value);
        if(currentNode.data==value||cmp==0){
            return true;
        }
        else if(cmp>0){
            return findWithRecursive(currentNode.left, value);
        }
        else if(cmp<0){
            return findWithRecursive(currentNode.right,value);
        }
        return false;
    }

    // 遍历获取最小值
    private int findSmallest(){
        TreeNode<Integer> t = (TreeNode<Integer>) root;
        while (t.left!=null){
            t= t.left;
        }
        return t.data;
    }

    private int findSmallestWithRecursive(TreeNode<Integer> integerTreeNode){
        if(integerTreeNode.left!=null){
            return findSmallestWithRecursive(integerTreeNode.left);
        }
        return integerTreeNode.data;
    }


    private void add(T data){
         this.root = addWithRecursive(this.root,data);
    }

    private boolean find(T data){
        return  findWithRecursive(this.root, data);

    }

    public static void main(String[] args) {
        TreeList<Integer> bt = new TreeList<>();
        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);

        boolean exist = bt.find(6);

        int smallestValue = bt.findSmallestWithRecursive(bt.root);
        System.out.println(exist);
        System.out.println(smallestValue);
    }
}
