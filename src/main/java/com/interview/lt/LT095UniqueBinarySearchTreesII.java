package com.interview.lt;

import java.util.ArrayList;
import java.util.List;

/**
 * 95. 不同的二叉搜索树 II 
 * 给定一个整数 n，请生成所有由 1 ... n 为节点组成的不同的二叉搜索树。
 * 难度: 中等
 * 标签: 树, 动态规划
 */
public class LT095UniqueBinarySearchTreesII {
    // 定义二叉树节点结构
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 使用动态规划生成所有可能的二叉搜索树
    public List<TreeNode> generateTrees(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        return generateTrees(1, n);
    }

    // 递归生成指定范围内的所有可能二叉搜索树
    private List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> trees = new ArrayList<>();
        
        // 如果起始值大于结束值，返回包含null的列表（表示空子树）
        if (start > end) {
            trees.add(null);
            return trees;
        }
        
        // 以每个数字作为根节点
        for (int i = start; i <= end; i++) {
            // 生成左子树的所有可能性
            List<TreeNode> leftTrees = generateTrees(start, i - 1);
            // 生成右子树的所有可能性
            List<TreeNode> rightTrees = generateTrees(i + 1, end);
            
            // 组合左右子树
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    trees.add(root);
                }
            }
        }
        
        return trees;
    }

    // 测试用例
    public static void main(String[] args) {
        LT095UniqueBinarySearchTreesII solution = new LT095UniqueBinarySearchTreesII();
        
        // 测试案例1
        System.out.println("测试案例1结果:");
        List<TreeNode> result1 = solution.generateTrees(3);
        System.out.println("共有 " + result1.size() + " 种不同的二叉搜索树");
        // 应该输出5种不同的二叉搜索树
        
        // 测试案例2
        System.out.println("\n测试案例2结果:");
        List<TreeNode> result2 = solution.generateTrees(1);
        System.out.println("共有 " + result2.size() + " 种不同的二叉搜索树");
        // 应该输出1种二叉搜索树
    }
}