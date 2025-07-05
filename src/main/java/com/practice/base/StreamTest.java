package com.practice.base;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * The type Stream test. Stream核心机制：流水线（Stage + Sink）+ 惰性求值 + ForkJoin 并行。
 * 深入源码建议参考 java.util.stream.ReferencePipeline 和 Sink 接口的实现

 中间操作：中间操作只是一种标记，只有结束操作才会触发实际计算

 无状态：指元素的处理不受前面元素的影响；
 有状态：有状态的中间操作必须等到所有元素处理之后才知道最终结果，比如排序是有状态操作，在读取所有元素之前并不能确定排序结果。

 Q：Stream 与集合的区别？
 A：集合存储数据；Stream 不存储数据，按需计算，支持惰性求值和并行处理

 Q：中间操作 vs 终止操作？
 A：
 类型	    返回值	    触发执行	    示例
 中间操作	Stream	    否	        filter(), map()
 终止操作	非 Stream	是	        collect(), count()

 Q：如何避免多次迭代？
 A：通过 Sink 链在单次遍历中执行所有操作。例如 list.stream().filter().map().collect() 只遍历一次

 Q：并行流线程安全问题？
 A：共享变量需同步（如 synchronized），或使用无状态操作。

 Q：为何 Stream 不能被复用？
 A：终止操作后流即关闭，再操作抛 IllegalStateException：
 Stream<String> s = Stream.of("a");
 s.forEach(System.out::println);
 s.count(); // 抛出 IllegalStateException :cite[4]

 Q：Spring 事务中慎用 parallelStream？
 A：并行流使用多线程，但 Spring 事务基于 ThreadLocal 绑定连接，子线程无法继承主线程事务：
 @Transactional
 public void batchInsert(List<Order> list) {
    list.parallelStream().forEach(orderMapper::save); // 事务失效
 }

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

//        // 错误示例：并行流修改共享变量
//        List<Integer> result = new ArrayList<>();
//        Stream.iterate(0, i -> i+1).parallel().limit(10)
//                .forEach(i -> result.add(i)); // 可能抛出 ArrayIndexOutOfBoundsException

        // 正确方案：用 `collect` 代替
        List<Integer> safeList = Stream.iterate(0, i -> i+1)
                .parallel().limit(10)
                .collect(Collectors.toList());
        System.out.println(safeList);

        // 为什么需要特化流，比如LongStream等：
        // 避免 Long 对象的装箱/拆箱开销
        // 提升数值计算效率（比通用流快 2-5 倍），
        // 应用场景选择： 1. 数据量 > 10,000 时优先使用特化流； 2. 需要 sum(), average() 等特化操作时必选。
        // 对比示例
        Stream<Long> boxed = Stream.of(1L, 2L, 3L);
        LongStream unboxed = boxed.flatMapToLong(LongStream::of); // 更高效
        unboxed.forEach(System.out::print);
        System.out.println();

        // 输入：Stream<String>
        Stream<String> words = Stream.of("apple", "banana", "cherry");
        // 转换为 LongStream
        IntStream asciiCodes = words.flatMapToInt(CharSequence::chars);
        // 输出：所有字符的 ASCII 码
        asciiCodes.forEach(System.out::print);
        System.out.println();



        // 1. 基础操作链式调用示例
        List<String> filtered = Arrays.asList("apple", "banana", "cherry", "date", "pipeline")
            .stream()
            .filter(s -> s.length() > 5)  // 过滤长度>5的字符串
            .map(String::toUpperCase)     // 转换为大写
            .limit(2)            // 限制最多取2个
            .sorted()
            .collect(Collectors.toList()); // 收集结果
        System.out.println("基础操作结果: " + filtered);

        // 2. reduce聚合操作示例
        Optional<Integer> sum = Arrays.asList(1, 2, 3, 4, 5)
            .stream()
            .reduce((a, b) -> a + b);
        System.out.println("reduce求和结果: " + sum.orElse(0));

        // 3. 分组groupingBy示例 - 按字符串长度分组
        Map<Integer, List<String>> grouped = Arrays.asList("one", "two", "three", "four")
            .stream()
            .collect(Collectors.groupingBy(String::length));
        System.out.println("按长度分组结果: " + grouped);

        // 4. partitioningBy分区示例 - 按条件分区
        Map<Boolean, List<Integer>> partitioned = Arrays.asList(1, 2, 3, 4, 5)
            .stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("奇偶数分区结果: " + partitioned);

        // 5. 惰性求值特性演示
        List<Integer> lazyResult = Arrays.asList(1, 2, 3, 4, 5)
            .stream()
            .filter(n -> {
                System.out.print("惰性求值执行过滤 ");
                return n % 2 == 0;
            })
            .map(n -> {
                System.out.print("惰性求值执行映射 ");
                return n * 2;
            })
            .limit(1)
            .collect(Collectors.toList());
        System.out.println("\n惰性求值结果: " + lazyResult);

        // 6. 并行流parallelStream示例
        int sumParallel = Arrays.asList(1, 2, 3, 4, 5)
            .parallelStream()
            .mapToInt(Integer::intValue)
            .sum();
        System.out.println("并行流求和结果: " + sumParallel);

        // 7. flatMap扁平化处理示例
        List<String> flatResult = Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("c", "d"))
            .stream()
            .flatMap(List::stream)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("扁平化处理结果: " + flatResult);

        // 8. 自定义收集器示例 - 使用StringBuilder拼接
        String joined = Arrays.asList("Java", "Stream", "API")
            .stream()
            .collect(Collectors.reducing(
                new StringBuilder(),
                s -> new StringBuilder(s),
                (sb1, sb2) -> sb1.append("-").append(sb2)
            ))
            .toString();
        System.out.println("自定义收集器结果: " + joined);

        // 9. 短路操作示例 - findFirst和anyMatch
        boolean hasMatch = Arrays.asList(1, 2, 3, 4, 5)
            .stream()
            .anyMatch(n -> {
                System.out.print("短路操作检查 " + n + " ");
                return n > 3;
            });
        System.out.println("\n存在大于3的数: " + hasMatch);

        // 10. 数值流特化示例 - IntStream操作
        int rangeSum = IntStream.range(1, 6)  // [1,2,3,4,5)
            .sum();
        System.out.println("数值流求和结果: " + rangeSum);
    }

}
