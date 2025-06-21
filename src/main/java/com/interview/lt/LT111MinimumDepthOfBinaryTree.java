package com.interview.lt;

/**
 * LeetCode 111: Minimum Depth of Binary Tree
 * 题目描述: 给定一个二叉树，找出其最小深度。
 * 解法思路: 使用递归方法，分别计算左右子树的最小深度，并考虑只有一个子节点的情况。
 */
public class LT111MinimumDepthOfBinaryTree {
    
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
     * 计算二叉树的最小深度
     * @param root 根节点
     * @return 最小深度
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        // 如果左子树为空，则只计算右子树的深度
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }
        
        // 如果右子树为空，则只计算左子树的深度
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }
        
        // 左右子树都不为空时，取较小的深度
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }
    
    public static void main(String[] args) {
        // 测试用例
        LT111MinimumDepthOfBinaryTree solution = new LT111MinimumDepthOfBinaryTree();
        
        // 创建一个普通二叉树
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        
        // 测试普通二叉树的最小深度
        System.out.println("Testing normal binary tree:");
        System.out.println(solution.minDepth(root)); // 应该返回2
        
        // 创建一个单边二叉树
        TreeNode singleSideRoot = new TreeNode(1);
        singleSideRoot.left = new TreeNode(2);
        singleSideRoot.left.left = new TreeNode(3);
        singleSideRoot.left.left.left = new TreeNode(4);
        
        // 测试单边二叉树的最小深度
        System.out.println("Testing single-side binary tree:");
        System.out.println(solution.minDepth(singleSideRoot)); // 应该返回4
    }
}