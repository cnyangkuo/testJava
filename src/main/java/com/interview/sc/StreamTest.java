package com.interview.sc;

import java.util.stream.Stream;
/**
 * @author yangkuo
 * @date 2025/7/4
 * @description
 */
public class StreamTest {

    public static void main(String[] args) {
        // 验证惰性求值：无终止操作时，无输出
        Stream.of("a", "b").filter(s -> {
            System.out.println("filter: " + s); // 无终止操作，不执行
            return true;
        });

        Stream.of("c", "d").filter(s -> {
            System.out.println("filter: " + s); // 输出 filter: a, filter: b
            return true;
        }).count(); // 终止操作触发执行

    }

}
