package com.interview.lt;

/**
 * 69. x 的平方根 
 * 给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
 * 难度: 简单
 * 标签: 数学, 二分查找
 */
public class LT069SqrtX {
    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        
        int left = 1;
        int right = x;
        int result = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // 避免溢出的判断方式
            if (mid <= x / mid) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        LT069SqrtX solution = new LT069SqrtX();
        
        // 测试用例1
        int x1 = 4;
        int result1 = solution.mySqrt(x1);
        System.out.println("Test 1: " + result1 + " 期望输出: 2");
        
        // 测试用例2
        int x2 = 8;
        int result2 = solution.mySqrt(x2);
        System.out.println("Test 2: " + result2 + " 期望输出: 2");
        
        // 测试用例3
        int x3 = 2147395599;
        int result3 = solution.mySqrt(x3);
        System.out.println("Test 3: " + result3 + " 期望输出: 46339");
    }
}