package com.practice.lt;

import java.util.Arrays;

/**
 * 贪心算法相关面试题集合
 * 包含3个典型的贪心算法问题
 */
public class GreedyAlgorithmsProblems {
    /**
     * 问题1：跳跃游戏（LeetCode 55）
     * 思路分析：
     * 1. 维护当前能到达的最远位置
     * 2. 遍历数组每个位置，如果当前位置能到达且跳跃后更远则更新最远位置
     * 3. 最终判断最远位置是否到达终点
     * 
     * @param nums 非负整数数组表示最大跳跃步数
     * @return 是否能到达终点
     */
    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        
        int maxReach = 0;
        for (int i = 0; i < nums.length; i++) {
            // 如果当前位置可到达
            if (i <= maxReach) {
                maxReach = Math.max(maxReach, i + nums[i]);
                // 提前终止：已到达终点
                if (maxReach >= nums.length - 1) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 问题2：买卖股票的最佳时机II（LeetCode 122）
     * 思路分析：
     * 1. 只要后一天价格高于前一天就交易
     * 2. 累计所有上涨趋势的利润
     * 3. 时间复杂度O(n)，空间复杂度O(1)
     * 
     * @param prices 股票价格数组
     * @return 最大利润
     */
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            // 只要后一天价格更高就交易
            if (prices[i] > prices[i-1]) {
                profit += prices[i] - prices[i-1];
            }
        }
        return profit;
    }
    
    /**
     * 问题3：分发糖果（LeetCode 135）
     * 思路分析：
     * 1. 从左到右遍历，确保评分更高的孩子获得更多糖果（当右边孩子评分更高时）
     * 2. 从右到左遍历，确保评分更高的孩子获得更多糖果（当左边孩子评分更高时）
     * 3. 最终每个孩子获得的糖果数取两个方向的最大值
     * 
     * @param ratings 孩子评分数组
     * @return 分发的最少糖果总数
     */
    public static int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) return 0;
        
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1); // 每个孩子至少1颗糖果
        
        // 从左到右遍历
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i-1]) {
                candies[i] = candies[i-1] + 1;
            }
        }
        
        // 从右到左遍历
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i+1]) {
                candies[i] = Math.max(candies[i], candies[i+1] + 1);
            }
        }
        
        // 计算总糖果数
        int total = 0;
        for (int count : candies) {
            total += count;
        }
        return total;
    }
    
    public static void main(String[] args) {
        // 测试示例1：跳跃游戏
        int[] nums1 = {2,3,1,1,4};
        System.out.println("能否到达终点: " + canJump(nums1));  // 输出true
        
        // 测试示例2：买卖股票的最佳时机II
        int[] prices = {7,1,5,3,6,4};
        System.out.println("最大利润: " + maxProfit(prices));  // 输出7
        
        // 测试示例3：分发糖果
        int[] ratings = {1,0,2};
        System.out.println("最少糖果总数: " + candy(ratings));  // 输出5
    }
}