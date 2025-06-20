package com.interview.lt;

/**
 * 70. 爬楼梯
 * 假设你正在爬楼梯。需要 n 阶才能到达顶部。
 * 每次你可以选择爬 1 或 2 个台阶。有多少种不同的方法可以到达顶部？
 * 难度: 简单
 * 标签: 数学, 动态规划
 */
public class LT070ClimbingStairs {
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        
        int first = 1; // f(n-2)
        int second = 2; // f(n-1)
        
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        
        return second;
    }
    
    public static void main(String[] args) {
        LT070ClimbingStairs solution = new LT070ClimbingStairs();
        
        // 测试用例1
        int n1 = 2;
        int result1 = solution.climbStairs(n1);
        System.out.println("Test 1: " + result1 + " 期望输出: 2");
        
        // 测试用例2
        int n2 = 3;
        int result2 = solution.climbStairs(n2);
        System.out.println("Test 2: " + result2 + " 期望输出: 3");
        
        // 测试用例3
        int n3 = 45;
        int result3 = solution.climbStairs(n3);
        System.out.println("Test 3: " + result3 + " 期望输出: 1836311903");
    }
}