package com.interview.lt;

/**
 * 96. 不同的二叉搜索树
 * 给定一个整数 n，请计算由 1 到 n 为节点组成的不同的二叉搜索树的数量。
 * 难度: 中等
 * 标签: 树, 动态规划
 */
public class LT096UniqueBinarySearchTrees {
    // 使用动态规划计算不同二叉搜索树数量
    public int numTrees(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("输入必须是非负整数");
        }
        
        if (n == 0 || n == 1) {
            return 1; // 空树和单节点树都只有一种结构
        }
        
        int[] dp = new int[n + 1];
        dp[0] = 1; // 空子树有一种结构
        dp[1] = 1; // 单节点子树有一种结构
        
        // 计算从2到n的所有情况
        for (int i = 2; i <= n; i++) {
            // 以每个j作为根节点
            for (int j = 1; j <= i; j++) {
                // 左子树有j-1个节点，右子树有i-j个节点
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        
        return dp[n];
    }

    // 测试用例
    public static void main(String[] args) {
        LT096UniqueBinarySearchTrees solution = new LT096UniqueBinarySearchTrees();
        
        // 测试案例1
        int n1 = 3;
        System.out.println("测试案例1结果: " + solution.numTrees(n1)); // 应该输出5
        
        // 测试案例2
        int n2 = 1;
        System.out.println("\n测试案例2结果: " + solution.numTrees(n2)); // 应该输出1
        
        // 测试案例3
        int n3 = 4;
        System.out.println("\n测试案例3结果: " + solution.numTrees(n3)); // 应该输出14
    }
}