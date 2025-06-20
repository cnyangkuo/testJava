package com.interview.lt;

/**
 * 121. 买卖股票的最佳时机
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示某支股票第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。如果你无法获取任何利润，返回 0。
 * 难度: 简单
 * 标签: 数组, 贪心算法
 */
public class LT121BestTimeToBuyAndSellStock {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        
        for (int price : prices) {
            // 寻找当前最低价格
            if (price < minPrice) {
                minPrice = price;
            } else {
                // 计算当前利润并更新最大利润
                maxProfit = Math.max(maxProfit, price - minPrice);
            }
        }
        
        return maxProfit;
    }
    
    public static void main(String[] args) {
        LT121BestTimeToBuyAndSellStock solution = new LT121BestTimeToBuyAndSellStock();
        
        // 测试用例1
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        int result1 = solution.maxProfit(prices1);
        System.out.println("Test 1: " + result1 + " 期望输出: 5");
        
        // 测试用例2
        int[] prices2 = {7, 6, 4, 3, 1};
        int result2 = solution.maxProfit(prices2);
        System.out.println("Test 2: " + result2 + " 期望输出: 0");
        
        // 测试用例3 - 单一价格
        int[] prices3 = {5};
        int result3 = solution.maxProfit(prices3);
        System.out.println("Test 3: " + result3 + " 期望输出: 0");
    }
}