package com.interview.lt;

/**
 * 9. 回文数
 * 给你一个整数 x ，如果 x 是回文整数，返回 true ；否则返回 false。
 * 难度: 简单
 * 标签: 数学
 */
public class LT009PalindromeNumber {
    public boolean isPalindrome(int x) {
        // 负数不是回文，以0结尾的非零数字也不是回文
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        
        int reversedHalf = 0;
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }
        
        // 偶数位时直接相等，奇数位则去掉中间数字比较
        return x == reversedHalf || x == reversedHalf / 10;
    }
    
    public static void main(String[] args) {
        LT009PalindromeNumber solution = new LT009PalindromeNumber();
        
        // 测试用例1
        int x1 = 121;
        boolean result1 = solution.isPalindrome(x1);
        System.out.println("Test 1: " + result1 + " 期望输出: true");
        
        // 测试用例2
        int x2 = -121;
        boolean result2 = solution.isPalindrome(x2);
        System.out.println("Test 2: " + result2 + " 期望输出: false");
        
        // 测试用例3
        int x3 = 10;
        boolean result3 = solution.isPalindrome(x3);
        System.out.println("Test 3: " + result3 + " 期望输出: false");
    }
}