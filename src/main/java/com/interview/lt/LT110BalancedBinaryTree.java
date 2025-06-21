package com.interview.lt;

/**
 * LeetCode 110: Balanced Binary Tree
 * 题目描述: 判断一个二叉树是否是平衡二叉树。
 * 解法思路: 使用递归方法计算每个节点的高度，同时检查左右子树的高度差是否超过1。
 */
public class LT110BalancedBinaryTree {
    
    /**
     * 定义TreeNode类表示二叉树节点
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    
    /**
     * 判断二叉树是否是平衡二叉树
     * @param root 根节点
     * @return 是否是平衡二叉树
     */
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }
    
    /**
     * 检查二叉树的高度并验证是否平衡
     * @param node 当前节点
     * @return 树的高度，若不平衡返回-1
     */
    private int checkHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        // 检查左子树高度
        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) {
            return -1; // 左子树不平衡，直接返回
        }
        
        // 检查右子树高度
        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) {
            return -1; // 右子树不平衡，直接返回
        }
        
        // 如果左右子树高度差超过1则不平衡
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        
        // 返回当前节点的高度
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    public static void main(String[] args) {
        // 测试用例
        LT110BalancedBinaryTree solution = new LT110BalancedBinaryTree();
        
        // 创建一个平衡二叉树
        TreeNode balancedRoot = new TreeNode(3);
        balancedRoot.left = new TreeNode(9);
        balancedRoot.right = new TreeNode(20);
        balancedRoot.right.left = new TreeNode(15);
        balancedRoot.right.right = new TreeNode(7);
        
        // 测试平衡二叉树
        System.out.println("Testing balanced binary tree:");
        System.out.println(solution.isBalanced(balancedRoot)); // 应该返回true
        
        // 创建一个非平衡二叉树
        TreeNode unbalancedRoot = new TreeNode(1);
        unbalancedRoot.left = new TreeNode(2);
        unbalancedRoot.right = new TreeNode(2);
        unbalancedRoot.left.left = new TreeNode(3);
        unbalancedRoot.left.right = new TreeNode(3);
        unbalancedRoot.left.left.left = new TreeNode(4);
        unbalancedRoot.left.left.right = new TreeNode(4);
        
        // 测试非平衡二叉树
        System.out.println("Testing unbalanced binary tree:");
        System.out.println(solution.isBalanced(unbalancedRoot)); // 应该返回false
    }
}