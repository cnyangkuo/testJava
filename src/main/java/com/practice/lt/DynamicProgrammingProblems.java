package com.practice.lt;

import java.util.Arrays;

/**
 * 动态规划相关面试题集合
 * 包含3个典型动态规划问题，每个问题都有详细注释说明
 */
public class DynamicProgrammingProblems {
    /**
     * 问题1：最长递增子序列（LeetCode 300）
     * 思路分析：
     * 1. 创建dp数组记录以每个位置结尾的最长递增子序列长度
     * 2. 对于每个元素，检查其前面所有比它小的元素
     * 3. 状态转移方程：dp[i] = max(dp[j] + 1) for all j < i and nums[j] < nums[i]
     * 
     * @param nums 输入数组
     * @return 最长递增子序列长度
     */
    public static int longestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        
        int max = 0;
        for (int length : dp) {
            max = Math.max(max, length);
        }
        return max;
    }
    
    /**
     * 问题2：0-1背包问题
     * 思路分析：
     * 1. 创建二维dp数组，dp[i][w]表示前i个物品容量为w时的最大价值
     * 2. 状态转移分为两种情况：
     *    - 不选当前物品：dp[i][w] = dp[i-1][w]
     *    - 选当前物品：dp[i][w] = dp[i-1][w-weight[i]] + value[i]
     * 
     * @param weights 物品重量数组
     * @param values 物品价值数组
     * @param capacity 背包容量
     * @return 最大价值
     */
    public static int knapsackProblem(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length || capacity <= 0) return 0;
        
        int n = weights.length;
        int[][] dp = new int[n+1][capacity+1];
        
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                // 不选当前物品
                dp[i][w] = dp[i-1][w];
                // 如果可以选当前物品
                if (w >= weights[i-1]) {
                    dp[i][w] = Math.max(dp[i][w], dp[i-1][w-weights[i-1]] + values[i-1]);
                }
            }
        }
        return dp[n][capacity];
    }
    
    /**
     * 问题3：最大正方形（LeetCode 221）
     * 思路分析：
     * 1. 创建dp数组，dp[i][j]表示以(i,j)为右下角的最大正方形边长
     * 2. 状态转移：
     *    - 如果matrix[i][j] == '1'，dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
     *    - 否则dp[i][j] = 0
     * 
     * @param matrix 字符矩阵
     * @return 最大正方形面积
     */
    public static int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int maxSide = 0;
        int[][] dp = new int[rows][cols];
        
        // 初始化第一行和第一列
        for (int i = 0; i < rows; i++) {
            dp[i][0] = matrix[i][0] == '1' ? 1 : 0;
            maxSide = Math.max(maxSide, dp[i][0]);
        }
        for (int j = 0; j < cols; j++) {
            dp[0][j] = matrix[0][j] == '1' ? 1 : 0;
            maxSide = Math.max(maxSide, dp[0][j]);
        }
        
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1])) + 1;
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        return maxSide * maxSide;
    }
    
    public static void main(String[] args) {
        // 测试示例1：最长递增子序列
        int[] nums1 = {10,9,2,5,3,7,101,18};
        System.out.println("最长递增子序列长度: " + longestIncreasingSubsequence(nums1));  // 输出4
        
        // 测试示例2：0-1背包问题
        int[] weights = {2,3,4,5};
        int[] values = {3,4,5,6};
        int capacity = 5;
        System.out.println("背包最大价值: " + knapsackProblem(weights, values, capacity));  // 输出7
        
        // 测试示例3：最大正方形
        char[][] matrix = {
            {'1','0','1','0','0'},
            {'1','0','1','1','1'},
            {'1','1','1','1','1'},
            {'1','0','0','1','0'}
        };
        System.out.println("最大正方形面积: " + maximalSquare(matrix));  // 输出4
    }
}