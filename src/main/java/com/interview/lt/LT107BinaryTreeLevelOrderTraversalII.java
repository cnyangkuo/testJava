package com.interview.lt;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 107: Binary Tree Level Order Traversal II
 * 题目描述: 给定一个二叉树，返回其节点值的层序遍历（从底向上的每个层级）。
 * 解法思路: 使用广度优先搜索进行层序遍历，最后反转结果列表。
 */
public class LT107BinaryTreeLevelOrderTraversalII {
    
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
     * 二叉树层序遍历，从底向上
     * @param root 根节点
     * @return 层序遍历结果
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        // 使用队列存储每一层的节点
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            // 获取当前层的节点数量
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < size; i++) {
                // 处理当前层的每个节点
                TreeNode node = queue.remove(0);
                level.add(node.val);
                
                // 将下一层节点加入队列
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            
            // 将当前层的结果加入结果列表
            result.add(level);
        }
        
        // 反转结果列表，实现从底向上的遍历
        List<List<Integer>> reversedResult = new ArrayList<>();
        for (int i = result.size() - 1; i >= 0; i--) {
            reversedResult.add(result.get(i));
        }
        
        return reversedResult;
    }
    
    public static void main(String[] args) {
        // 测试用例
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        
        LT107BinaryTreeLevelOrderTraversalII solution = new LT107BinaryTreeLevelOrderTraversalII();
        List<List<Integer>> traversal = solution.levelOrderBottom(root);
        
        // 打印结果
        System.out.println("Binary tree level order traversal from bottom:");
        for (List<Integer> level : traversal) {
            System.out.println(level);
        }
    }
}