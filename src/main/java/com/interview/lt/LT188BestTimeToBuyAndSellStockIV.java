package com.interview.lt;

import java.util.Arrays;

// 添加LT188买卖股票的最佳时机IV问题解决方案
public class LT188BestTimeToBuyAndSellStockIV {
    // 题目：买卖股票的最佳时机IV（Best Time to Buy and Sell Stock IV）
    // 难度：困难
    // 描述：最多进行k次交易，求最大利润。
    // 解法：动态规划，时间复杂度O(nk)，空间复杂度O(k)
    
    public int maxProfit(int k, int[] prices) {
        if (prices.length < 2 || k == 0) return 0;
        
        // 如果k足够大，变成无限次交易的情况
        if (k >= prices.length / 2) {
            int maxProfit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }
        
        // 定义两个数组：buy[i]表示第i次买入后的最大利润
        // sell[i]表示第i次卖出后的最大利润
        int[] buy = new int[k + 1];
        int[] sell = new int[k + 1];
        
        // 初始化
        Arrays.fill(buy, Integer.MIN_VALUE);
        Arrays.fill(sell, 0);
        
        for (int price : prices) {
            for (int i = 1; i <= k; i++) {
                buy[i] = Math.max(buy[i], sell[i - 1] - price);
                sell[i] = Math.max(sell[i], buy[i] + price);
            }
        }
        
        return sell[k];
    }
    
    public static void main(String[] args) {
        LT188BestTimeToBuyAndSellStockIV solution = new LT188BestTimeToBuyAndSellStockIV();
        int[] prices1 = {2,4,1};
        System.out.println("Test case 1: " + solution.maxProfit(2, prices1)); // 应该输出2
        
        int[] prices2 = {3,2,6,5,0,3};
        System.out.println("Test case 2: " + solution.maxProfit(2, prices2)); // 应该输出7
    }
}