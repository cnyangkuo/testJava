package com.interview.lt;

// 添加LT199二叉树的右视图问题解决方案
import java.util.ArrayList;
import java.util.List;

// 定义TreeNode类
//class TreeNode {
//    int val;
//    TreeNode left;
//    TreeNode right;
//
//    TreeNode(int x) {
//        val = x;
//    }
//}

public class LT199BinaryTreeRightSideView {
    // 题目：二叉树的右视图（Binary Tree Right Side View）
    // 难度：中等
    // 描述：给定一棵二叉树，返回从根节点从顶部到底部的视角，看到的节点值列表。
    // 解法：深度优先搜索(DFS)，优先访问右子树
    
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result, 0);
        return result;
    }
    
    private void dfs(TreeNode<Integer> node, List<Integer> result, int depth) {
        if (node == null) {
            return;
        }
        
        // 如果当前深度等于结果列表的大小，说明这是这一层的第一个节点
        if (depth == result.size()) {
            result.add(node.val);
        }
        
        // 先访问右子树
        dfs(node.right, result, depth + 1);
        // 再访问左子树
        dfs(node.left, result, depth + 1);
    }
    
    public static void main(String[] args) {
        // 创建测试用的二叉树
        //       1
        //      / \
        //     2   3
        //      \   \
        //       5   4
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(4);
        
        LT199BinaryTreeRightSideView solution = new LT199BinaryTreeRightSideView();
        List<Integer> result = solution.rightSideView(root);
        System.out.println("Test case 1: " + result); // 应该输出[1, 3, 4]
    }
}