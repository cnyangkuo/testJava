package com.practice.lt;

import java.util.Arrays;

/**
 * 动态规划相关面试题集合
 * 包含3个典型动态规划问题，每个问题都有详细注释说明
 *
 * 动态规划通常用于最优化问题，尤其是那些具有重叠子问题和最优子结构的问题。适用问题类型：
     * 最优化问题（最大值/最小值）
        * 示例：最长递增子序列、最小路径和、背包问题
     * 计数问题
        * 示例：不同路径数量、爬楼梯方案数
     * 存在性问题
        * 示例：单词拆分、跳跃游戏
     * 字符串处理
        * 示例：最长回文子串、编辑距离
 *
 *
 * 关键设计原则：
     * 状态必须能完整描述决策过程
     * 转移方程需保证最优子结构
     * 初始化条件要覆盖边界情况
     * 计算顺序应满足依赖关系
 *
 *
 * 状态定义
     * 一维：dp[i] 表示前i个元素的最优解（如斐波那契）
     * 二维：dp[i][j] 表示两个序列前i/j项的关系（如最长公共子序列）
     * 特殊状态：dp[i][k] 表示前i项进行k次操作的最优解（如买卖股票）
 *
 *
 * 二维dp数组的使用场景可能是在需要同时考虑两个变量的情况下，
 * 比如两个字符串的比较（最长公共子序列），或者棋盘类问题（不同路径II）。
     * 双序列比较
        * LCS（最长公共子序列）：dp[i][j] 表示text1前i字符和text2前j字符的匹配长度
     * 棋盘问题
         * 不同路径II：dp[i][j] 记录到达(i,j)位置的路径数
     * 受限条件组合
         * 背包问题：二维数组表示物品选取状态和容量限制
     * 区间DP
         * 石子合并：dp[i][j] 表示合并i到j堆石子的最小代价
 *
 *
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