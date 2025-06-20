package com.interview.lt;

/**
 * 7. 整数反转
 * 给出一个 32 位的有符号整数，将整数中的数字部分反转。
 * 难度: 中等
 * 标签: 数学
 */
public class LT007ReverseInteger {
    public int reverse(int x) {
        long result = 0;
        
        while (x != 0) {
            int digit = x % 10;
            result = result * 10 + digit;
            x /= 10;
            
            // 检查是否溢出
            if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
                return 0;
            }
        }
        
        return (int) result;
    }
    
    public static void main(String[] args) {
        LT007ReverseInteger solution = new LT007ReverseInteger();
        
        // 测试用例1
        int x1 = 123;
        int result1 = solution.reverse(x1);
        System.out.println("Test 1: " + result1 + " 期望输出: 321");
        
        // 测试用例2
        int x2 = -123;
        int result2 = solution.reverse(x2);
        System.out.println("Test 2: " + result2 + " 期望输出: -321");
        
        // 测试用例3
        int x3 = 120;
        int result3 = solution.reverse(x3);
        System.out.println("Test 3: " + result3 + " 期望输出: 21");
        
        // 测试用例4 - 溢出情况
        int x4 = 1534236469; // Integer.MAX_VALUE=2147483647
        int result4 = solution.reverse(x4);
        System.out.println("Test 4: " + result4 + " 期望输出: 0 (溢出情况)");
    }
}