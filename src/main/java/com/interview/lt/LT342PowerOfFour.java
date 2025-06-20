package com.interview.lt;

/**
 * 342. 4的幂
 * 给定一个整数 n，判断它是否是 4 的幂。
 * 难度: 简单
 * 标签: 位运算, 数学
 */
public class LT342PowerOfFour {
    public boolean isPowerOfFour(int n) {
        // 4的幂必须满足：
        // 1. 是2的幂（n > 0 且 (n & (n - 1)) == 0）
        // 2. 在4的幂次表中，(n - 1) % 3 == 0
        return n > 0 && (n & (n - 1)) == 0 && (n - 1) % 3 == 0;
    }
    
    public static void main(String[] args) {
        LT342PowerOfFour solution = new LT342PowerOfFour();
        
        // 测试用例1
        int n1 = 16;
        boolean result1 = solution.isPowerOfFour(n1);
        System.out.println("Test 1: " + result1 + " 期望输出: true");
        
        // 测试用例2
        int n2 = 5;
        boolean result2 = solution.isPowerOfFour(n2);
        System.out.println("Test 2: " + result2 + " 期望输出: false");
        
        // 测试用例3
        int n3 = 1;
        boolean result3 = solution.isPowerOfFour(n3);
        System.out.println("Test 3: " + result3 + " 期望输出: true");
    }
}