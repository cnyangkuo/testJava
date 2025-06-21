package com.interview.lt;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 113: Path Sum II
 * 题目描述: 找出所有从根节点到叶子节点的路径，使得路径上的节点值之和等于目标值。
 * 解法思路: 使用深度优先搜索遍历所有从根节点到叶子节点的路径，并记录满足条件的路径。
 */
public class LT113PathSumII {
    
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
     * 找出所有满足条件的路径
     * @param root 根节点
     * @param sum 目标路径和
     * @return 所有满足条件的路径列表
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(root, sum, path, result);
        return result;
    }
    
    /**
     * 深度优先搜索遍历二叉树
     * @param node 当前节点
     * @param sum 剩余目标和
     * @param path 当前路径
     * @param result 所有满足条件的路径列表
     */
    private void dfs(TreeNode node, int sum, List<Integer> path, List<List<Integer>> result) {
        if (node == null) {
            return;
        }
        
        // 将当前节点添加到路径中
        path.add(node.val);
        
        // 如果是叶子节点且路径和符合要求，则添加到结果中
        if (node.left == null && node.right == null && sum == node.val) {
            result.add(new ArrayList<>(path));
        } else {
            // 否则继续遍历左右子树
            dfs(node.left, sum - node.val, path, result);
            dfs(node.right, sum - node.val, path, result);
        }
        
        // 回溯，移除当前节点
        path.remove(path.size() - 1);
    }
    
    public static void main(String[] args) {
        // 测试用例
        LT113PathSumII solution = new LT113PathSumII();
        
        // 创建测试二叉树
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);
        
        // 测试不同的路径和
        System.out.println("Testing all paths with target sum:");
        List<List<Integer>> paths = solution.pathSum(root, 22);
        for (List<Integer> path : paths) {
            System.out.println(path);
        }
    }
}