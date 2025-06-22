package com.interview.lt;

// 添加LT191位1的个数问题解决方案
public class LT191Numberof1Bits {
    // 题目：位1的个数（Number of 1 Bits）
    // 难度：简单
    // 描述：编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中不同位置的1的数量。
    // 解法：使用位操作，利用n & (n-1)消除最右边的1
    
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= n - 1; // 消除最右边的1
            count++;
        }
        return count;
    }
    
    public static void main(String[] args) {
        LT191Numberof1Bits solution = new LT191Numberof1Bits();
        
        int n1 = 0b00000000000000000000000000001011;
        System.out.println("Test case 1: " + solution.hammingWeight(n1)); // 应该输出3
        
        int n2 = 0b00000000000000000000000010000000;
        System.out.println("Test case 2: " + solution.hammingWeight(n2)); // 应该输出1
        
        int n3 = 0b11111111111111111111111111111111;
        System.out.println("Test case 3: " + solution.hammingWeight(n3)); // 应该输出32
    }
}