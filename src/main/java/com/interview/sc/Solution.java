package com.interview.sc;

/**
 * @author yangkuo
 * @date 2025/7/4
 * @description
 */
public class Solution {
    public static void main(String[] args) {
        String result1 = connectStrings("Hello", "World", String::concat); // 等价于 (a, b) -> a.concat(b)
        System.out.println(result1);
        String result2 = connectStrings("你", "好", (a, b) -> a + b);
        System.out.println(result2);
    }

    public static String connectStrings(String a, String b, StringConnector connector) {
        return connector.connect(a, b);
    }

    // 自定义函数式接口示例2
    @FunctionalInterface
    interface StringConnector {
        String connect(String a, String b);
    }

}
