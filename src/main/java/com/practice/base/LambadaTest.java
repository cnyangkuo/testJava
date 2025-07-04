package com.practice.base;
import java.util.function.*;

/**
 * Lambda表达式完整示例集
 * 涵盖各种使用场景和语法特性
 * @author hanson
 */
public class  LambadaTest {
    public static void main(String[] args) {
        // 1. 无参数Lambda示例 - Runnable接口
        // 修复：通过Runnable接口正确使用Lambda表达式
        // 原错误写法：Thread t1 = () -> System.out.println("无参数Lambda执行");
        // 新写法：使用Runnable接口作为Lambda表达式目标类型
        Runnable task = () -> System.out.println("无参数Lambda执行");
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(() -> System.out.println("无参数Lambda执行2"));
        t1.start();
        t2.start();

        // 2. 单参数Lambda示例 - Consumer接口, 只有入参，没有返回值
        // 自动类型推断，可省略参数类型
        Consumer<String> printConsumer = str -> System.out.println("消费型接口: " + str);
        printConsumer.accept("Hello World");

        // 3. 带返回值的Lambda - Function接口, 有入参和返回值
        // 单表达式可省略return关键字
        Function<String, Integer> strToInt = Integer::parseInt;
        System.out.println("字符串转整数: " + strToInt.apply("1234"));

        // 4. 多参数Lambda示例 - BiFunction接口
        // 需要显式声明参数类型，使用表达式体
        BiFunction<Integer, Integer, Double> calc = (a, b) -> {
            double result = Math.sqrt(a * a + b * b);
            return result;
        };
        System.out.println("双参数计算结果: " + calc.apply(3, 4));

        // 5. 方法引用示例 - 静态方法引用
        // 使用类名::方法名的语法替代Lambda表达式
        Supplier<Double> piSupplier = Math::random; // 注意：此处为示例，实际应使用Math.PI
        System.out.println("方法引用获取随机数: " + piSupplier.get());

        // 6. 变量捕获示例 - effectively final特性
        // Lambda表达式只能访问final或等效不可变的局部变量
        int factor = 10; // 实际不可变变量
        Function<Integer, Integer> multiplier = x -> x * factor;
        System.out.println("变量捕获示例: " + multiplier.apply(5));

        // 7. 构造方法引用示例 - 通过构造方法创建对象
        // 使用类名::new的语法创建对象实例
        Function<String, StringBuilder> builder = StringBuilder::new;
        System.out.println("构造方法引用: " + builder.apply("测试文本"));

        // 8. 条件判断示例 - Predicate接口
        // 使用Lambda表达式实现条件判断逻辑
        Predicate<Integer> isEven = x -> x % 2 == 0;
        System.out.println("数字是否为偶数: " + isEven.test(12));

        // 9. 集合遍历示例 - 使用Lambda遍历集合
        // 替代传统循环，更简洁的集合操作方式
        java.util.List<String> fruits = java.util.Arrays.asList("apple", "banana", "cherry");
        fruits.forEach(fruit -> System.out.println("集合元素: " + fruit));

        // 10. 自定义函数式接口示例
        // 实现自定义接口的Lambda表达式
        Calculator adder = (a, b) -> a + b;
        System.out.println("自定义接口计算: " + adder.calculate(15, 25));

        // 11. 自定义函数式接口示例2 - 使用Lambda表达式连接字符串
        // 核心原理：隐含的调用者参数
        // 语法形式：ClassName::instanceMethodName
        // 本质：将函数式接口的第一个参数作为实例方法的调用者（this），其余参数作为方法的入参。
        StringConnector connector = String::concat;
        System.out.println("自定义接口连接字符串: " + connector.connect("Hello", "World"));

        BinaryOperator<String> operator = String::concat;
        // 等价于：
        // BinaryOperator<String> operator = (s1, s2) -> s1.concat(s2);
        // BinaryOperator<String> 需要两个 String 参数并返回 String ; String::concat 将：
        // 第一个参数 s1 作为 concat 的调用者 (this)
        // 第二个参数 s2 作为 concat 的入参
        System.out.println("operator接口测试: " + operator.apply("Hello", "World"));


    }


    // 自定义函数式接口示例2
    @FunctionalInterface
    interface StringConnector {
        String connect(String a, String b);
    }

    // 自定义函数式接口示例
    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
    }
}
