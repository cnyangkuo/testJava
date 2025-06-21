package com.interview.lt;

import com.interview.lt.TreeNode;  // 正确的TreeNode类路径

/**
 * LeetCode 104: Maximum Depth of Binary Tree
 * 题目描述: 给定一个二叉树，找出其最大深度。
 * 解题思路: 使用递归方法，分别计算左右子树的最大深度，然后取较大值加1（当前节点的深度）
 */
public class LT104MaximumDepthOfBinaryTree {
    
    /**
     * 计算二叉树的最大深度
     * @param root 根节点
     * @return 最大深度
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        // 递归计算左子树深度
        int leftDepth = maxDepth(root.left);
        // 递归计算右子树深度
        int rightDepth = maxDepth(root.right);
        
        // 当前节点深度为左右子树较大值加1
        return Math.max(leftDepth, rightDepth) + 1;
    }
    
    public static void main(String[] args) {
        // 测试用例
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        
        LT104MaximumDepthOfBinaryTree solution = new LT104MaximumDepthOfBinaryTree();
        int depth = solution.maxDepth(root);
        System.out.println("Maximum depth of binary tree: " + depth);
    }
}