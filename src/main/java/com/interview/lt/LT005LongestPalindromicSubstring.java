package com.interview.lt;

/**
 * 5. 最长回文子串
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 难度: 中等
 * 标签: 双指针, 字符串, 动态规划
 */
public class LT005LongestPalindromicSubstring {
    // 使用中心扩展算法
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        int start = 0;
        int end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);   // 奇数长度的回文
            int len2 = expandAroundCenter(s, i, i + 1); // 偶数长度的回文
            int len = Math.max(len1, len2);
            
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        
        return s.substring(start, end + 1);
    }
    
    // 辅助函数：从中心向外扩展寻找回文
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
    
    public static void main(String[] args) {
        LT005LongestPalindromicSubstring solution = new LT005LongestPalindromicSubstring();
        
        // 测试用例1
        String s1 = "babad";
        String result1 = solution.longestPalindrome(s1);
        System.out.println("Test 1: " + result1 + " 期望输出: bab 或 aba");
        
        // 测试用例2
        String s2 = "cbbd";
        String result2 = solution.longestPalindrome(s2);
        System.out.println("Test 2: " + result2 + " 期望输出: bb");
        
        // 测试用例3
        String s3 = "a";
        String result3 = solution.longestPalindrome(s3);
        System.out.println("Test 3: " + result3 + " 期望输出: a");
        
        // 测试用例4
        String s4 = "ac";
        String result4 = solution.longestPalindrome(s4);
        System.out.println("Test 4: " + result4 + " 期望输出: a 或 c");
    }
}