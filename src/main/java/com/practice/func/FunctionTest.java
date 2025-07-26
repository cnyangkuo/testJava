package com.practice.func;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * Function接口核心用法演示
 * 包含default方法的典型应用场景
 * @author hanson
 */
public class FunctionTest {
    public static void main(String[] args) {
        // 1. 基础应用 - 字符串长度计算
        Function<String, Integer> lengthFunc1 = String::length;
        System.out.println("基础应用 - 长度计算: " + lengthFunc1.apply("Hello World"));
        
        // 2. andThen组合 - 链式转换, 顺序操作
        // 先取长度再转为描述字符串
        Function<String, Integer> lengthFunc = String::length;
        Function<Integer, String> describeFunc = x -> x + " chars";
        Function<String, String> combinedFunc = lengthFunc.andThen(describeFunc);
        System.out.println("andThen组合 - 链式转换1: " + combinedFunc.apply("Java"));
        System.out.println("andThen组合 - 链式转换2: " + (lengthFunc.andThen(describeFunc)).apply("Java2"));
        System.out.println("andThen组合 - 链式转换3: " + ((lengthFunc).andThen(x -> x + " chars")).apply("Java3"));

        // 3. compose组合 - 反向链式调用，逆序操作
        // 先转为字符串再取长度
        Function<Integer, String> toString = Object::toString;
        Function<String, Integer> parseLength = String::length;
        Function<Integer, Integer> composed = parseLength.compose(toString);
        System.out.println("compose组合 - 逆序操作: " + composed.apply(12345));

        // 4. 多级组合 - 链式调用
        // 数值转换管道：Integer -> Double -> String -> Length
        Function<Integer, Double> toDouble = x -> x * 1.0;
        Function<Double, String> formatToStr = x -> String.format("%.2f", x);
        Function<String, Integer> countDigits = s -> s.replaceAll("[^0-9]", "").length();
        
        Function<Integer, Integer> pipeline = toDouble
            .andThen(formatToStr)
            .andThen(countDigits);
        System.out.println("多级组合 - 数值转换管道: " + pipeline.apply(123));
        
        // 5. identity应用 - 恒等函数
        Function<String, String> identity = Function.identity();
        String input = "保持原样";
        System.out.println("identity应用 - 恒等转换: " + (identity.apply(input) == input));
        
        // 6. 条件组合 - 结合Predicate使用
        Function<String, Boolean> checkLength = s -> s.length() > 5;
        Function<String, String> validator = checkLength
            .andThen(b -> b ? "有效" : "无效");
        System.out.println("条件组合 - 长度验证: " + validator.apply("Short"));
        
        // 7. BiFunction复合场景 - 条件选择与identity组合
        // 根据布尔参数决定是否转换大小写，否则保持原样(identity)
        BiFunction<Boolean, String, String> conditionalTransform = (toUpper, text) -> 
            toUpper ? text.toUpperCase() : (String) Function.identity().apply(text);
        System.out.println("BiFunction复合场景 - 条件转换: " + 
            conditionalTransform.apply(true, "hello"));
        System.out.println("BiFunction复合场景 - 恒等保持: " + 
            conditionalTransform.apply(false, "world"));
        
        // 8. BinaryOperator与identity组合应用 - 链式处理
        // 创建字符串合并操作，通过identity进行结果传递
        BinaryOperator<String> merger = (s1, s2) -> s1 + s2;
        Function<String, String> identityFunc = Function.identity();
        BiFunction<String, String, String> processor = (prefix, suffix) ->
            identityFunc.apply(merger.apply(prefix, suffix));
        System.out.println("BinaryOperator组合 - 字符串合并: " + 
            processor.apply("Hello", "World"));
        
        // 9. 多级函数组合 - BiFunction链式调用
        // 1) 计算两个字符串的长度差
        BiFunction<String, String, Integer> lengthDiff = (s1, s2) -> Math.abs(s1.length() - s2.length());
        // 2) 根据长度差生成描述信息
        Function<Integer, String> diffDesc = diff -> "长度差为: " + diff;
        // 3) 组合操作：计算长度差并生成描述
        BiFunction<String, String, String> lengthDiffProcessor = lengthDiff.andThen(diffDesc);
        System.out.println("多级函数组合 - 长度差描述: " + lengthDiffProcessor.apply("Java", "Python"));
    }
}