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
 *
 * 动态规划问题通常有两种实现方式：
 * 自顶向下（Top-Down）和自底向上（Bottom-Up）。它们各有适用场景，有时也可以结合使用。下面我将详细分析这两种方法的特点、适用场景以及如何选择。
 *
 * ### 1. 自顶向下（记忆化搜索） 递归
 * **方法描述**：从原问题出发，逐步分解为子问题，利用递归和记忆化（Memoization）避免重复计算。
 * **特点**：
 * - **递归形式**：从大问题开始，递归地分解为小问题。
 * - **记忆化**：使用数组或哈希表存储已计算的子问题结果。
 * - **惰性计算**：只计算需要解决的子问题。
 * **适用场景**：
 * - **子问题空间不是全部需要**：当问题可能有大量子问题，但实际需要计算的子问题只占一部分时，自顶向下避免计算无用状态。
 * - **状态转移复杂或维度高**：当状态转移方程复杂，或者状态维度较高（如3维以上）时，自顶向下可以按需计算，避免处理所有状态。
 * - **问题边界不易确定**：如果问题的边界（如初始状态）不易确定，但终止状态明确，适合自顶向下从终止状态开始递归。
 * *经典例子**：
 - 斐波那契数列（Fibonacci）：计算第n项，但实际只需要计算部分子问题。
 - 树形DP：如二叉树中的最大路径和（子问题在子树中，自然递归结构）。
 *
 * ### 2. 自底向上（迭代法） 表格填充
 * **方法描述**：从最小的子问题开始，逐步构建更大的子问题，直到解决原问题。
 * **特点**：
 * - **迭代形式**：通常使用循环，从基础状态开始，逐步填充DP表。
 * - **顺序性**：需要明确子问题的依赖顺序，确保计算当前状态时依赖的子问题已计算。
 * - **计算所有状态**：会计算所有子问题，即使其中部分可能不会被用到。
 * **适用场景**：
 * - **子问题空间需要全部计算**：当问题要求计算所有子问题，或者需要填充整个DP表时。
 * - **状态转移简单且维度低**：状态维度较低（如1维或2维），且依赖关系明确，适合用循环迭代。
 * - **避免递归开销**：当问题规模较大时，递归可能导致栈溢出，自底向上可以避免递归深度问题。
 * - **需要优化空间**：自底向上更容易进行空间优化（如滚动数组）。
 * **经典例子**：
 * - 背包问题：需要填充整个二维DP表。
 * - 最长公共子序列（LCS）：需要计算所有子问题。
 *
 * ### 4. 自顶向下（Top-Down）和自底向上（Bottom-Up）的选择依据
 *
 * | **考虑因素**           | **自顶向下**                          | **自底向上**                          |
 *
 * |------------------------|--------------------------------------|---------------------------------------|
 * | **子问题计算范围**     | 只计算必要子问题                     | 计算所有子问题                        |
 * | **空间复杂度**         | 需要递归栈空间，记忆化存储空间       | 通常只需DP表空间，可优化（如滚动数组）|
 * | **时间复杂度**         | 同自底向上，但递归有额外开销         | 通常更高效                            |
 * | **实现难度**           | 较简单（递归思维自然）               | 需要设计循环顺序                      |
 * | **避免栈溢出**         | 深度大时可能栈溢出                   | 无递归栈，适合大规模问题              |
 * | **状态依赖关系**       | 适合复杂或高维依赖                   | 适合简单依赖和低维                    |
 *
 *
 * @author hanson
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

//        int maxLen = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
//            maxLen = Math.max(maxLen, dp[i]);
        }
        return Arrays.stream(dp).max().getAsInt();
//        return maxLen;
    }

    /**
     * 问题2：0-1背包问题
     * 思路分析：
     * 1. 创建二维dp数组，dp[i][w]表示前i个物品容量恰好为w时的最大价值
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

        /** 推导前
        // 创建dp数组 表示容量为w时，前i个物品的最大价值
        int[][] dp = new int[n+1][capacity+1];

        // 遍历物品
        // i-1的含义：物品数组索引从0开始（weights[0]是第一个物品），
        // 外层循环i从1开始（表示前1个物品）。
        //  weights[i-1]对应第i个物品的实际重量。
        for (int i = 1; i <= n; i++) {
            // 遍历容量
            for (int w = 0; w <= capacity; w++) {
                // 不选当前物品， 处理不选的情况是为了保留之前计算的最优解，而不仅仅是当前的选择
                dp[i][w] = dp[i-1][w];
                // 如果可以选当前物品,即当前容量w是否足够装下第i个物品。
                // 在剩余容量w - weights[i-1]中加上当前物品的价值values[i-1]
                if (w >= weights[i-1]) {
                    dp[i][w] = Math.max(dp[i][w], dp[i-1][w-weights[i-1]] + values[i-1]);
                }
            }
        }
        // 最终结果：dp[n][W]（前n个物品在容量W下的最大价值）
        return dp[n][capacity];
         */

        /** 推导后 优化后的状态转移方程
         */
         int[] dp = new int[capacity + 1];
         for (int i = 0; i < n; i++) {
             for (int w = capacity; w >= weights[i]; w--) {   // 倒序遍历容量 确保每个物品只被选择一次
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
             }
         }
         return dp[capacity];
    }

    /**
     * 问题4：完全背包问题
     * 思路分析：
     * 1. 创建一维dp数组，dp[w]表示容量恰好为w时的最大价值
     * 2. 状态转移方程：dp[w] = max(dp[w], dp[w-weight[i]] + value[i])
     * 3. 与0-1背包的区别：物品可无限次选择 → 正序遍历容量
     *
     * @param weights 物品重量数组
     * @param values 物品价值数组
     * @param capacity 背包容量
     * @return 最大价值
     */
    public static int completeKnapsackProblem(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length || capacity <= 0) return 0;

        int n = weights.length;
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < n; i++) {
            for (int w = weights[i]; w <= capacity; w++) {  // 正序遍历容量, 物品可选无限次
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[capacity];
    }
    /**
     * 总结：选择遍历方向的黄金法则
     *
     * 倒序遍历（0-1背包模式）： 当每个物品最多选1次
     * 应用场景：经典0-1背包、多重背包二进制优化、分组背包
     *
     * 正序遍历（完全背包模式）： 当物品可选无限次
     * 应用场景：完全背包、硬币兑换问题、无限资源分配
     *
     * 特例：组合vs排列
     * 组合问题（不考虑顺序，即无序）：物品循环在外层，容量在内层
     * 排列问题（考虑顺序， 即有序）：容量循环在外层，物品在内层
     *
     * 只需记住：倒序防重复，正序允累积。
     */

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

    /**
     * 硬币问题：给定不同面额的硬币(1,2,5)和总金额，计算最少硬币数量
     * 示例: 11元可用1+5+5=3枚硬币
     * @param coins 硬币面额数组
     * @param amount 总金额
     * @return 最少硬币数量，若无法组成则返回-1
     */
    public static int coinChange(int[] coins, int amount) {
        // 初始化动态规划数组，dp[i]表示组成i元所需的最少硬币数
        int[] dp = new int[amount + 1];
        // amount+1表示无解, 也可以用Integer.MAX_VALUE
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        // 自底向上计算每个金额的最优解
        for (int i = 1; i <= amount; i++) {
            // 尝试每种硬币组合
            for (int coin : coins) {
                if (coin <= i) {
                    // 状态转移方程：取当前解和使用该硬币后的解的最小值
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        // 如果值仍为amount+1表示无法组成
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        // 测试示例1：最长递增子序列
        int[] nums1 = {10,9,2,5,3,7,101,18};
        System.out.println("最长递增子序列长度: " + longestIncreasingSubsequence(nums1));  // 输出4

        // 测试示例2：0-1背包问题
        int[] weights = {2,3,4,5};
        int[] values = {3,4,5,6};
        int capacity = 8;
        System.out.println("背包最大价值: " + knapsackProblem(weights, values, capacity));  // 输出10

        // 测试示例4：完全背包问题
        System.out.println("完全背包最大价值: " + completeKnapsackProblem(weights, values, capacity));  // 输出6
        
        // 测试示例3：最大正方形
        char[][] matrix = {
            {'1','0','1','0','0'},
            {'1','0','1','1','1'},
            {'1','1','1','1','1'},
            {'1','0','0','1','0'}
        };
        System.out.println("最大正方形面积: " + maximalSquare(matrix));  // 输出4

        // 测试示例5：硬币问题
        int[] coins = {1,2,5};
        int target = 11;
        System.out.println("最少硬币数: " + coinChange(coins, target));  // 输出3
    }
}