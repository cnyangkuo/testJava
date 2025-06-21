package com.interview.lt;

/**
 * 97. 交错字符串
 * 给定三个字符串 s1, s2 和 s3，请判断 s3 是否是由 s1 和 s2 交错形成的字符串。
 * 难度: 困难
 * 标签: 字符串, 动态规划
 */
public class LT097InterleavingString {
    // 使用动态规划解决交错字符串问题
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();
        
        // 如果长度不匹配，直接返回false
        if (len1 + len2 != len3) {
            return false;
        }
        
        // dp[i][j]表示s1前i个字符和s2前j个字符能否交错组成s3前i+j个字符
        boolean[][] dp = new boolean[len1 + 1][len2 + 1];
        dp[0][0] = true; // 空字符串可以由两个空字符串组成
        
        // 初始化第一行
        for (int j = 1; j <= len2; j++) {
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }
        
        // 初始化第一列
        for (int i = 1; i <= len1; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }
        
        // 填充dp数组
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                // 检查s1的当前字符是否匹配，并且前面的字符串是否可以交错形成
                boolean match1 = dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);
                // 检查s2的当前字符是否匹配，并且前面的字符串是否可以交错形成
                boolean match2 = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);
                
                dp[i][j] = match1 || match2;
            }
        }
        
        return dp[len1][len2];
    }

    // 测试用例
    public static void main(String[] args) {
        LT097InterleavingString solution = new LT097InterleavingString();
        
        // 测试案例1
        String s1_1 = "aabcc";
        String s1_2 = "dbbca";
        String s1_3 = "aadbbcbcac";
        System.out.println("测试案例1结果: " + solution.isInterleave(s1_1, s1_2, s1_3)); // 应该输出true
        
        // 测试案例2
        String s2_1 = "a";
        String s2_2 = "a";
        String s2_3 = "aa";
        System.out.println("\n测试案例2结果: " + solution.isInterleave(s2_1, s2_2, s2_3)); // 应该输出true
        
        // 测试案例3
        String s3_1 = "abc";
        String s3_2 = "def";
        String s3_3 = "abdecf";
        System.out.println("\n测试案例3结果: " + solution.isInterleave(s3_1, s3_2, s3_3)); // 应该输出true
    }
}