package com.wxy.wjl.testspringboot2.tree;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 二叉树遍历
 * 二叉树的访问次序可以分为四种：前序遍历 中序遍历 后序遍历 层序遍历
 * 其中后序遍历分为递归和非递归
 */
public class Traversal {

    public static void main(String[] args) {
        TreeNode<String> treeNode1=new TreeNode<>();
        TreeNode<String> treeNode2=new TreeNode<>();
        TreeNode<String> treeNode3=new TreeNode<>();
        TreeNode<String> treeNode4=new TreeNode<>();
        TreeNode<String> treeNode5=new TreeNode<>();
        treeNode1.data="根节点";
        treeNode2.data="二层左节点";
        treeNode3.data="二层右节点";
        treeNode4.data="三层左节点";
        treeNode5.data="三层右节点";
        treeNode1.left=treeNode2;
        treeNode1.right=treeNode3;
        treeNode2.left=treeNode4;
        treeNode2.right=treeNode5;

        System.out.println("前序遍历：----");
        preTraversal(treeNode1);
        System.out.println("");
        System.out.println("中序遍历：----");
        inOrderTraversal(treeNode1);
        System.out.println("");
        System.out.println("后序遍历递归：----");
        postOrderTraversal(treeNode1);
        System.out.println("");
        System.out.println("后序遍历非递归：----");
        postOrderTraversal2(treeNode1);
        System.out.println("");
        System.out.println("层次遍历：----");
        levelTraversal(treeNode1);

    }


    /**
     * 前序遍历 ：根->左->右  递归
     * @param tree
     */
    public static void preTraversal(TreeNode tree){
        if(tree == null){
            return;
        }
        /**
         * 做处理节点的操作：此处为输出节点data
         */
        System.out.print(tree.data+"--->");
        preTraversal(tree.left);
        preTraversal(tree.right);
    }

    /**
     * 中序遍历 ：左->根->右  递归
     * @param tree
     */
    public static void inOrderTraversal(TreeNode tree){
        if(tree == null){
            return;
        }
        inOrderTraversal(tree.left);
        /**
         * 做处理节点的操作：此处为输出节点data
         */
        System.out.print(tree.data+"--->");
        inOrderTraversal(tree.right);
    }

    /**
     * 后序遍历 ：左->右->根  递归
     * @param tree
     */
    public static void postOrderTraversal(TreeNode tree){
        if(tree == null){
            return;
        }
        postOrderTraversal(tree.left);
        postOrderTraversal(tree.right);
        /**
         * 做处理节点的操作：此处为输出节点data
         */
        System.out.print(tree.data+"--->");

    }
    /**
     * 后序遍历 ：左->右->根  非递归写法  双栈法
     * @param tree
     */
    public static void postOrderTraversal2(TreeNode tree){
        if(tree == null){
            return;
        }
        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(tree);
        while(!s1.isEmpty()){
            tree = s1.pop();
            s2.push(tree);
            if(tree.left != null){
                s1.push(tree.left);
            }
            if(tree.right != null){
                s1.push(tree.right);
            }
        }
        while(!s2.isEmpty()){
            System.out.print(s2.pop().data + "--->");
        }
    }


    /**
     * 层次遍历 ：从上到下逐层遍历  非递归写法
     * @param tree
     */
    public static void levelTraversal(TreeNode tree){
        if (tree == null){
            return;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        TreeNode current = null;
        queue.offer(tree); // 根节点入队
        while (!queue.isEmpty()){ // 只要队列中有元素，就可以一直执行，非常巧妙的利用了队列的特性
            current = queue.poll(); // 出队队头元素
            queue2.offer(current);
            // 左子树不为空，入队
            if (current.left != null)
                queue.offer(current.left);
            // 右子树不为空，入队
            if (current.right != null)
                queue.offer(current.right);
        }
        while(!queue2.isEmpty()){
            System.out.print(queue2.poll().data+"--->");
        }
    }





}
