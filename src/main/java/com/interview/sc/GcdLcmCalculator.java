package com.interview.sc;

public class GcdLcmCalculator {
    /**
     * 计算两个正整数的最大公约数
     * @param a 第一个正整数
     * @param b 第二个正整数
     * @return 最大公约数
     * @throws IllegalArgumentException 如果任一参数小于等于0
     */
    public static int gcd(int a, int b) {
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("参数必须为正整数");
        }
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * 计算两个正整数的最小公倍数
     * @param a 第一个正整数
     * @param b 第二个正整数
     * @return 最小公倍数
     * @throws IllegalArgumentException 如果任一参数小于等于0
     */
    public static int lcm(int a, int b) {
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("参数必须为正整数");
        }
        return a * b / gcd(a, b);
    }

    public static void main(String[] args) {
        // 测试用例
        int num1 = 12, num2 = 18;
        System.out.println(num1 + "和" + num2 + "的最大公约数为: " + gcd(num1, num2)); // 输出6
        System.out.println(num1 + "和" + num2 + "的最小公倍数为: " + lcm(num1, num2)); // 输出36
    }
}