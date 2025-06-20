package com.interview.lt;

/**
 * 8. 字符串转换整数 (atoi)
 * 实现一个自己的字符串转整型函数。
 * 难度: 中等
 * 标签: 字符串
 */
public class LT008StringToIntegerAtoi {
    public int myAtoi(String s) {
        int i = 0;
        int sign = 1;
        int result = 0;
        
        // 跳过前导空格
        while (i < s.length() && s.charAt(i) == ' ') {
            i++;
        }
        
        // 处理符号
        if (i < s.length() && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            i++;
        }
        
        // 转换数字
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            
            // 检查溢出
            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            result = result * 10 + digit;
            i++;
        }
        
        return result * sign;
    }
    
    public static void main(String[] args) {
        LT008StringToIntegerAtoi solution = new LT008StringToIntegerAtoi();
        
        // 测试用例1
        String s1 = "42";
        int result1 = solution.myAtoi(s1);
        System.out.println("Test 1: " + result1 + " 期望输出: 42");
        
        // 测试用例2
        String s2 = "   -42";
        int result2 = solution.myAtoi(s2);
        System.out.println("Test 2: " + result2 + " 期望输出: -42");
        
        // 测试用例3
        String s3 = "4193 with words";
        int result3 = solution.myAtoi(s3);
        System.out.println("Test 3: " + result3 + " 期望输出: 4193");
        
        // 测试用例4 - 溢出情况
        String s4 = "2147483648";
        int result4 = solution.myAtoi(s4);
        System.out.println("Test 4: " + result4 + " 期望输出: 2147483647 (Integer.MAX_VALUE)");
    }
}