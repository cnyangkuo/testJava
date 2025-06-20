package com.interview.lt;

/**
 * 66. 加一
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数字末尾加一。
 * 难度: 简单
 * 标签: 数组, 数学
 */
public class LT066PlusOne {
    public int[] plusOne(int[] digits) {
        // 从最后一位开始处理
        for (int i = digits.length - 1; i >= 0; i--) {
            // 如果不是9，直接加一返回
            if (digits[i] != 9) {
                digits[i]++;
                return digits;
            }
            // 如果是9，变为0
            digits[i] = 0;
        }
        
        // 如果所有位都是9，则需要扩展数组
        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }
    
    public static void main(String[] args) {
        LT066PlusOne solution = new LT066PlusOne();
        
        // 测试用例1
        int[] digits1 = {1, 2, 3};
        int[] result1 = solution.plusOne(digits1);
        System.out.print("Test 1: [");
        for (int i = 0; i < result1.length; i++) {
            System.out.print(result1[i]);
            if (i < result1.length - 1) System.out.print(", ");
        }
        System.out.println("] 期望输出: [1, 2, 4]");
        
        // 测试用例2
        int[] digits2 = {9, 9, 9};
        int[] result2 = solution.plusOne(digits2);
        System.out.print("Test 2: [");
        for (int i = 0; i < result2.length; i++) {
            System.out.print(result2[i]);
            if (i < result2.length - 1) System.out.print(", ");
        }
        System.out.println("] 期望输出: [1, 0, 0, 0]");
    }
}