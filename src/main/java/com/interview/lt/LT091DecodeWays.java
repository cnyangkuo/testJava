package com.interview.lt;

/**
 * 91. 解码方法
 * 一条包含从消息通过将字母转为 A-1, B-2,...Z-26 的方式加密。
 * 给定一个非空字符串，含有数字，计算当前消息可以解码的方式数。
 * 难度: 中等
 * 标签: 动态规划, 字符串
 */
public class LT091DecodeWays {
    // 使用动态规划解决解码方法问题
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[n] = 1; // 空字符串有一种解码方式
        dp[n - 1] = (s.charAt(n - 1) != '0') ? 1 : 0;
        
        for (int i = n - 2; i >= 0; i--) {
            // 如果当前字符不是0，则可以单独解码
            if (s.charAt(i) != '0') {
                dp[i] = dp[i + 1];
            }
            
            // 如果两位数在10到26之间，则可以组合解码
            int twoDigit = Integer.parseInt(s.substring(i, i + 2));
            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i + 2];
            }
        }
        
        return dp[0];
    }

    // 测试用例
    public static void main(String[] args) {
        LT091DecodeWays solution = new LT091DecodeWays();
        
        // 测试案例1
        String s1 = "12";
        System.out.println("测试案例1结果: " + solution.numDecodings(s1)); // 应该输出2
        
        // 测试案例2
        String s2 = "226";
        System.out.println("测试案例2结果: " + solution.numDecodings(s2)); // 应该输出3
        
        // 测试案例3
        String s3 = "0";
        System.out.println("测试案例3结果: " + solution.numDecodings(s3)); // 应该输出0
    }
}