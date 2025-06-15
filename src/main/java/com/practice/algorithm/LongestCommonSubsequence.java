package com.practice.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yangkuo
 * @date 2024/4/7
 * 判断一个问题是否可以使用动态规划（Dynamic Programming, DP）来解答，通常需要考虑以下几个关键特征：
 *
 * 最优子结构： 问题的最优解应能够通过组合其子问题的最优解获得。这意味着，为了找到原问题的最优解，我们必须确保对
 * 每个子问题都选择了最优的解决方案。如果一个复杂问题可以拆解为若干个相互独立、规模较小的子问题，且原问题的最优解
 * 能够通过这些子问题的最优解直接构建出来，那么就满足了最优子结构的条件。
 *
 * 重叠子问题： 在求解过程中，问题的解空间中存在多个子问题被多次重复计算。这意味着在递归或迭代解题的过程中，会出现
 * 相同的子问题需要反复解决的情况。如果能够识别并记录这些子问题的答案，避免重复计算，就能显著提高算法效率。
 *
 * 无后效性（或称无环性）： 一旦某个子问题的解被计算出来，它就不会因为后续其他子问题的计算结果而改变。换句话说，
 * 子问题的解只依赖于其本身的输入参数，而不依赖于问题的计算顺序或未来状态的选择。这一点保证了我们可以安全地通过
 * 填表或记忆化的方式来存储子问题答案，而无需担心这些答案会在后续计算中变得无效。
 *
 * 明确的状态和状态转移关系： 能够清晰地定义问题的状态（通常用一个或多个变量表示）以及不同状态下之间的转移关系
 * （即如何从一个状态过渡到另一个状态）。状态转移方程（或称递推关系）描述了如何根据较小规模子问题的解来计算较大规模子问题的解。
 *
 * 目标函数和优化方向： 动态规划通常用于求解最优化问题，即寻找某个目标函数（如最大值、最小值、总代价等）的最优解。
 * 问题需要有明确的优化目标和方向，以便在各个子问题之间进行比较和选择。
 *
 * 判断步骤：
 *
 * 分析问题结构： 确认问题是否可以分解为一系列相互独立的子问题，以及这些子问题是否具有明显的递归关系或层次结构。
 *
 * 寻找最优子结构： 探究是否存在一种方式，使得原问题的最优解可以通过组合子问题的最优解得到。如果能找到这种构造方法，
 * 说明问题具备最优子结构。
 *
 * 检查重叠子问题： 观察解题过程中是否有多次重复计算相同的子问题。如果有，且这些子问题的数量随问题规模呈指数增长，
 * 那么使用动态规划可以极大地减少计算量。
 *
 * 验证无后效性： 确认子问题的解一旦确定，是否不受后续计算的影响，即是否仅取决于子问题自身的输入参数。
 *
 * 定义状态和状态转移方程： 明确描述问题状态的变量，并建立状态之间的转移关系。这通常表现为一个递推公式或表格填充规则。
 *
 * 确认优化目标： 确认问题是要最大化或最小化某个目标函数，或者寻找满足特定条件的可行解。
 *
 * 如果以上特征均得到满足，那么问题很可能是适合使用动态规划来解决的。反之，若问题不具备这些特征，可能需要考虑其他算法策略，
 * 如贪心算法、回溯搜索、分治算法、图论算法等。
 * @description
 */
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        String X = "ABCBDAB";  // 示例输入1
        String Y = "BDCABA";   // 示例输入2
        String result = longestCommonSubsequence(X, Y);
        System.out.println("最长公共子序列是: " + result);
        // 输出: 最长公共子序列是: BDAB 或 BCBA（可能有多个结果）
        int[] cost = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
//        int[] cost = {10,15,20};
        System.out.println("最小费用: " + minCostClimbingStairsWithPath(cost));
        System.out.println("达到楼梯顶部的最低花费: " + minCostClimbingStairs(cost));
    }

    /**
     * 动态规划求解最长公共子序列（LCS）
     * @param X 第一个字符串
     * @param Y 第二个字符串
     * @return 最长公共子序列字符串
     */
    public static String longestCommonSubsequence(String X, String Y) {
        int m = X.length();  // 字符串X的长度
        int n = Y.length();  // 字符串Y的长度

        // 创建动态规划表 dp，大小为 (m+1) x (n+1)
        int[][] dp = new int[m + 1][n + 1];

        // 填充动态规划表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前字符相等，则LCS长度加1
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 否则取左或上的最大值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // 回溯构造LCS字符串
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                // 字符相同，加入LCS
                lcs.append(X.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // 向上走
                i--;
            } else {
                // 向左走
                j--;
            }
        }

        // 由于回溯是逆序构造的，需要反转
        return lcs.reverse().toString();
    }

    /**
     * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用，
     * 即可选择向上爬一个或者两个台阶。
     *
     * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
     *
     * 请你计算并返回达到楼梯顶部的最低花费。
     * 示例：
     * 输入：cost = [1,100,1,1,1,100,1,1,100,1]
     * 解释：你将从下标为 0 的台阶开始。
     * - 支付 1 ，向上爬两个台阶，到达下标为 2 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 4 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 6 的台阶。
     * - 支付 1 ，向上爬一个台阶，到达下标为 7 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 9 的台阶。
     * - 支付 1 ，向上爬一个台阶，到达楼梯顶部。
     * 总花费为 6 。
     * 输出：6
     */
    public static int minCostClimbingStairsWithPath(int[] cost) {
        int n = cost.length;
        if (n == 0) return 0;
        if (n == 1) return cost[0];

        // 创建动态规划数组, dp 表示到达每个台阶的最低花费, prev 表示到达该台阶的前一个台阶
        int[] dp = new int[n];      // 记录到达每个台阶的最小成本
        int[] prev = new int[n];    // 记录达到该台阶的前一个台阶索引（路径信息）
        // 两个数组协同工作：
        // - dp[i] 存储到达第i阶的最小累计成本
        // - prev[i] 存储从哪个台阶走到第i阶（用于重建路径）

        // 初始化起点
        dp[0] = cost[0];
        prev[0] = -1; // 0号台阶无前驱
        dp[1] = cost[1];
        prev[1] = -1; // 1号台阶无前驱

        // 动态规划填充dp和prev数组
        for (int i = 2; i < n; i++) {
            if (dp[i - 1] < dp[i - 2]) {
                dp[i] = cost[i] + dp[i - 1];
                prev[i] = i - 1;     // 记录是从i-1阶走一步上来的
            } else {
                dp[i] = cost[i] + dp[i - 2];
                prev[i] = i - 2;    // 记录是从i-2阶走两步上来的
            }
        }

        // 确定终点（顶楼的前一个台阶）
        int current;
        int minCost;
        if (dp[n - 1] < dp[n - 2]) {
            minCost = dp[n - 1];
            current = n - 1;
        } else {
            minCost = dp[n - 2];
            current = n - 2;
        }

        // 通过prev数组回溯最优路径
        List<Integer> path = new ArrayList<>();
        while (current != -1) {
            path.add(current);
            current = prev[current];
        }
        Collections.reverse(path); // 反转得到从起点到终点的路径

        // 输出路径详情
        System.out.println("路径台阶索引: " + path);
        System.out.print("费用顺序: ");
        for (int idx : path) {
            System.out.print(cost[idx] + " ");
        }
        System.out.println();

        return minCost;
    }

    public static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        if (n == 0) return 0;
        if (n == 1) return cost[0];

        int prev2 = cost[0]; // dp[i-2]
        int prev1 = cost[1]; // dp[i-1]

        for (int i = 2; i < n; i++) {
            int current = Math.min(prev2, prev1) + cost[i];
            prev2 = prev1;
            prev1 = current;
        }

        return Math.min(prev2, prev1);
    }

}