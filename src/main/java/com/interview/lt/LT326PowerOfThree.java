package com.interview.lt;

/**
 * 326. 3的幂
 * 给定一个整数 n，判断它是否是 3 的幂。
 * 难度: 简单
 * 标签: 数学
 */
public class LT326PowerOfThree {
    public boolean isPowerOfThree(int n) {
        if (n <= 0) {
            return false;
        }
        
        // 不断除以3，直到无法整除
        while (n % 3 == 0) {
            n /= 3;
        }
        
        // 如果最后结果为1，则是3的幂
        return n == 1;
    }
    
    public static void main(String[] args) {
        LT326PowerOfThree solution = new LT326PowerOfThree();
        
        // 测试用例1
        int n1 = 27;
        boolean result1 = solution.isPowerOfThree(n1);
        System.out.println("Test 1: " + result1 + " 期望输出: true");
        
        // 测试用例2
        int n2 = 45;
        boolean result2 = solution.isPowerOfThree(n2);
        System.out.println("Test 2: " + result2 + " 期望输出: false");
        
        // 测试用例3
        int n3 = 9;
        boolean result3 = solution.isPowerOfThree(n3);
        System.out.println("Test 3: " + result3 + " 期望输出: true");
    }
}