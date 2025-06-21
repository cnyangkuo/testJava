package com.interview.lt;

/**
 * 98. 验证二叉搜索树
 * 给定一个二叉树，判断它是否是二叉搜索树（BST）。
 * 难度: 中等
 * 标签: 树, 深度优先搜索
 */
public class LT098ValidateBinarySearchTree {
    // 定义二叉树节点结构
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 使用中序遍历验证二叉搜索树
    private long prev = Long.MIN_VALUE; // 用于记录前一个节点的值
    
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true; // 空树是有效的BST
        }
        
        // 递归验证左子树
        if (!isValidBST(root.left)) {
            return false;
        }
        
        // 检查当前节点是否大于前一个节点
        if (root.val <= prev) {
            return false;
        }
               
        prev = root.val; // 更新前一个节点的值
        
        // 递归验证右子树
        return isValidBST(root.right);
    }

    // 测试用例
    public static void main(String[] args) {
        LT098ValidateBinarySearchTree solution = new LT098ValidateBinarySearchTree();
        
        // 测试案例1: 有效BST
        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(3);
        System.out.println("测试案例1结果: " + solution.isValidBST(root1)); // 应该输出true
        
        // 测试案例2: 无效BST
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(3);
        root2.right.right = new TreeNode(6);
        System.out.println("\n测试案例2结果: " + solution.isValidBST(root2)); // 应该输出false
        
        // 测试案例3: 单个节点
        TreeNode root3 = new TreeNode(1);
        System.out.println("\n测试案例3结果: " + solution.isValidBST(root3)); // 应该输出true
    }
}