package com.practice.lz;

/**
 * 动态规划常见问题实现
 * 考察点：状态转移、边界条件、优化思路
 */
public class DPProblems {
    /**
     * 题目1：最长递增子序列（LIS）
     * 核心思想：dp[i]表示以nums[i]结尾的LIS长度
     * @param nums 输入数组
     * @return 最长递增子序列长度
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int n = nums.length;
        int[] dp = new int[n];
        int maxLen = 1;
        
        // 初始化
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }
        
        // 状态转移
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        
        return maxLen;
    }
    
    /**
     * 题目2：编辑距离
     * 核心思想：二维DP，处理插入、删除、替换操作
     * @param word1 源字符串
     * @param word2 目标字符串
     * @return 最少操作次数
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        // 创建DP表
        int[][] dp = new int[m + 1][n + 1];
        
        // 初始化边界条件
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        
        // 状态转移
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 替换/插入/删除
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j - 1], dp[i - 1][j]), dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * 题目3：背包问题（0-1）
     * 核心思想：一维DP优化空间
     * @param weights 物品重量数组
     * @param values 物品价值数组
     * @param capacity 背包容量
     * @return 最大可装价值
     */
    public int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        // 创建DP表 表示当前容量可装价值
        int[] dp = new int[capacity + 1];
        
        // 状态转移
        // 外层循环为物品，从0开始，到物品i结束
        for (int i = 0; i < n; i++) {
            // 内层循环为背包容量，从物品i的容量开始，到背包容量结束
            // 逆序遍历，是为了避免重复计算； j >= weights[i] 表示当前物品可加入背包
            for (int j = capacity; j >= weights[i]; j--) {
                // 当前物品可加入背包，则更新dp[j]为当前物品价值 + 剩余容量可装价值 与 不加入背包的 二者中较大的值
                boolean flag = (dp[j] < dp[j - weights[i]] + values[i]);
                dp[j] = Math.max(dp[j], dp[j - weights[i]] + values[i]);
//                if (flag)  {
//                    System.out.println("i=" + i + " 更新 j=" + j + " dp[" + (capacity) + "]: " + java.util.Arrays.toString(dp));
//                }
//                // 打印 当前dp数组
//                System.out.println("i=" + i + " j=" + j + " dp[" + (capacity) + "]: " + java.util.Arrays.toString(dp));
            }
//            // 打印 当前dp数组
//            System.out.println("i=" + i + " dp[" + (capacity) + "]: " + java.util.Arrays.toString(dp));
        }

        /**
         * i=0 更新 j=8 dp[8]: [0, 0, 0, 0, 0, 0, 0, 0, 3]
         * i=0 更新 j=7 dp[8]: [0, 0, 0, 0, 0, 0, 0, 3, 3]
         * i=0 更新 j=6 dp[8]: [0, 0, 0, 0, 0, 0, 3, 3, 3]
         * i=0 更新 j=5 dp[8]: [0, 0, 0, 0, 0, 3, 3, 3, 3]
         * i=0 更新 j=4 dp[8]: [0, 0, 0, 0, 3, 3, 3, 3, 3]
         * i=0 更新 j=3 dp[8]: [0, 0, 0, 3, 3, 3, 3, 3, 3]
         * i=0 更新 j=2 dp[8]: [0, 0, 3, 3, 3, 3, 3, 3, 3]
         * i=0 dp[8]: [0, 0, 3, 3, 3, 3, 3, 3, 3]
         *
         * i=1 更新 j=8 dp[8]: [0, 0, 3, 3, 3, 3, 3, 3, 7]
         * i=1 更新 j=7 dp[8]: [0, 0, 3, 3, 3, 3, 3, 7, 7]
         * i=1 更新 j=6 dp[8]: [0, 0, 3, 3, 3, 3, 7, 7, 7]
         * i=1 更新 j=5 dp[8]: [0, 0, 3, 3, 3, 7, 7, 7, 7]
         * i=1 更新 j=4 dp[8]: [0, 0, 3, 3, 4, 7, 7, 7, 7]
         * i=1 更新 j=3 dp[8]: [0, 0, 3, 4, 4, 7, 7, 7, 7]
         * i=1 dp[8]: [0, 0, 3, 4, 4, 7, 7, 7, 7]
         *
         * i=2 更新 j=8 dp[8]: [0, 0, 3, 4, 4, 7, 7, 7, 9]
         * i=2 更新 j=7 dp[8]: [0, 0, 3, 4, 4, 7, 7, 9, 9]
         * i=2 更新 j=6 dp[8]: [0, 0, 3, 4, 4, 7, 8, 9, 9]
         * i=2 更新 j=4 dp[8]: [0, 0, 3, 4, 5, 7, 8, 9, 9]
         * i=2 dp[8]: [0, 0, 3, 4, 5, 7, 8, 9, 9]
         *
         * i=3 更新 j=8 dp[8]: [0, 0, 3, 4, 5, 7, 8, 9, 10]
         * i=3 dp[8]: [0, 0, 3, 4, 5, 7, 8, 9, 10]
         */

        // 返回最大可装价值
        return dp[capacity];
    }
    
    // 测试用例
    public static void main(String[] args) {
        DPProblems dp = new DPProblems();

        // 测试用例1：最长递增子序列
        int[] nums1 = {10,9,2,5,3,7,101,18};
        System.out.println("测试最长递增子序列:");
        System.out.println("输入数组: " + java.util.Arrays.toString(nums1));
        System.out.println("最长递增子序列长度: " + dp.lengthOfLIS(nums1));

        // 测试用例2：编辑距离
        String word1 = "horse";
        String word2 = "ros";
        System.out.println("\n测试编辑距离:");
        System.out.println("源字符串: " + word1);
        System.out.println("目标字符串: " + word2);
        System.out.println("最少操作次数: " + dp.minDistance(word1, word2));

        // 测试用例3：背包问题
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 8;
        System.out.println("\n测试背包问题:");
        System.out.println("物品重量: " + java.util.Arrays.toString(weights));
        System.out.println("物品价值: " + java.util.Arrays.toString(values));
        System.out.println("背包容量: " + capacity);
        System.out.println("最大可装价值: " + dp.knapsack(weights, values, capacity));
    }
}