package com.interview.lt;

/**
 * 263. 丑数
 * 编写程序判断给定的数字是否是丑数。
 * 丑数就是质因数只包含 2, 3, 5 的正整数。
 * 难度: 简单
 * 标签: 数学
 */
public class LT263UglyNumber {
    public boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        
        // 不断除以2、3、5，直到无法再被整除
        for (int factor : new int[] {2, 3, 5}) {
            while (n % factor == 0) {
                n /= factor;
            }
        }
        
        // 如果最后结果为1，则是丑数
        return n == 1;
    }
    
    public static void main(String[] args) {
        LT263UglyNumber solution = new LT263UglyNumber();
        
        // 测试用例1
        int n1 = 6;
        boolean result1 = solution.isUgly(n1);
        System.out.println("Test 1: " + result1 + " 期望输出: true");
        
        // 测试用例2
        int n2 = 14;
        boolean result2 = solution.isUgly(n2);
        System.out.println("Test 2: " + result2 + " 期望输出: false");
        
        // 测试用例3
        int n3 = 30;
        boolean result3 = solution.isUgly(n3);
        System.out.println("Test 3: " + result3 + " 期望输出: true");
    }
}