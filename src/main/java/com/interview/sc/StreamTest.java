package com.interview.sc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * The type Stream test.
 *
 * @author yangkuo
 * @date 2025 /7/4
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

        List<Integer> list = Arrays.asList(1, 2, 3);
        System.out.println("--------------------------------------------------");
        // 并行输出, 输出顺序不确定
//        list.parallelStream().forEach(n -> System.out.print(n + " "));  // 带空格
        list.parallelStream().forEach(System.out::print); // 不带空格
        System.out.println();
        System.out.println("--------------------------------------------------");
        // 顺序输出, 输出顺序一致
//        list.stream().forEach(n -> System.out.print(n + " "));  // 带空格
        list.stream().forEach(System.out::print); // 不带空格
        System.out.println();
    }

}
