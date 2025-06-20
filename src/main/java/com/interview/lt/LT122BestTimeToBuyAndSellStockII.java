package com.interview.lt;

/**
 * 122. 买卖股票的最佳时机 II
 * 给定一个数组 prices 表示股票价格，你可以多次买卖股票（买一次卖一次算一笔交易）。
 * 设计一个算法来计算你所能获取的最大利润。但不可同时参与多笔交易。
 * 难度: 简单
 * 标签: 数组, 贪心算法
 */
public class LT122BestTimeToBuyAndSellStockII {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        
        // 只要后一天价格高于前一天就进行交易
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }
        
        return maxProfit;
    }
    
    public static void main(String[] args) {
        LT122BestTimeToBuyAndSellStockII solution = new LT122BestTimeToBuyAndSellStockII();
        
        // 测试用例1
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        int result1 = solution.maxProfit(prices1);
        System.out.println("Test 1: " + result1 + " 期望输出: 7");
        
        // 测试用例2
        int[] prices2 = {1, 2, 3, 4, 5};
        int result2 = solution.maxProfit(prices2);
        System.out.println("Test 2: " + result2 + " 期望输出: 4");
        
        // 测试用例3
        int[] prices3 = {7, 6, 4, 3, 1};
        int result3 = solution.maxProfit(prices3);
        System.out.println("Test 3: " + result3 + " 期望输出: 0");
    }
}