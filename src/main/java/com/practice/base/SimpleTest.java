package com.practice.base;

/**
 * @author yangkuo
 * @date 2025/7/1
 * @description
 */
public class SimpleTest {
    public static void main(String[] args) {
        int a = 9;
        int b = a++;
        System.out.println(" a=" + a + " b=" + b);
        int c = ++a;
        System.out.println(" a=" + a + " c=" + c);
        int d = c--;
        System.out.println(" c=" + c + " d=" + d);
        int e = --d;
        System.out.println(" d=" + d + " e=" + e);
    }
}
