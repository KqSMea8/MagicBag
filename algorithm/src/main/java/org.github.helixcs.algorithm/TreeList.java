package org.github.helixcs.algorithm;


/**
 * @Author: helix
 * @Time:9/11/18
 * @Site: http://iliangqunru.bitcron.com/
 * @See :http://threezj.com/2016/03/20/%E6%9F%A5%E6%89%BE%E7%AE%97%E6%B3%95%E4%B9%8B%E9%A1%BA%E5%BA%8F%E3%80%81%E4%BA%8C%E5%88%86%E3%80%81%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E3%80%81%E7%BA%A2%E9%BB%91%E6%A0%91%20%E8%AF%A6%E7%BB%86%E6%AF%94%E8%BE%83%E6%80%BB%E7%BB%93/
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

    private int getDeepth(TreeNode<T> currentNode){
        if(currentNode==null){return 0;}
        int left = getDeepth(currentNode.left);
        int right =  getDeepth(currentNode.right);
        if(left==right){return left+1;}
        else if(left>right){return left+1;}
        else {return right+1;}
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
        bt.add(11);

        boolean exist = bt.find(6);

        int smallestValue = bt.findSmallestWithRecursive(bt.root);

        int deepth = bt.getDeepth(bt.root);

        System.out.println(exist);
        System.out.println(smallestValue);
        System.out.println(deepth);
    }
}
