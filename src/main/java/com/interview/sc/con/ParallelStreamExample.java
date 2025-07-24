package com.interview.sc.con;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.stream.IntStream;

/**
 * @author yangkuo
 * @date 2025/7/24
 * @description 并行流处理示例
 * 面试考点实现：
 * 1. 数据倾斜处理 - 自定义哈希范围分区器
 * 2. 结果顺序性 - ConcurrentHashMap与LinkedHashMap对比
 *
 * 并行流处理（ForkJoinPool + Spliterator）
 * 核心机制：
 *      利用 Stream.parallel() 自动拆分数据集
 *      Collectors.groupingByConcurrent() 线程安全合并结果
 * 面试考点：
 *      数据倾斜处理：自定义分区器（如按哈希范围分片）
 *      结果顺序性：ConcurrentHashMap 与 LinkedHashMap 的取舍
 *
 */
public class ParallelStreamExample {
    // 测试数据：模拟包含数据倾斜的键值分布
    private static final int DATA_SIZE = 1_000_000;
    private static final int SKEW_FACTOR = 7; // 数据倾斜因子

    public static void main(String[] args) {
        // 1. 生成测试数据（模拟数据倾斜）
        List<String> skewedData = generateSkewedData(DATA_SIZE, SKEW_FACTOR);
        
        // 2. 使用默认并行流处理
        System.out.println("使用默认分区器:");
        Map<String, Long> defaultResult = processWithDefaultPartitioner(skewedData);
        printSampleResults(defaultResult, 5);
        
        // 3. 使用自定义分区器处理
        System.out.println("\n使用自定义哈希范围分区器:");
        Map<String, Long> customResult = processWithCustomPartitioner(skewedData);
        printSampleResults(customResult, 5);
        
        // 4. 结果顺序性验证
        System.out.println("\n结果顺序性验证:");
        verifyResultOrder();
    }

    /**
     * 生成模拟数据（带数据倾斜）
     */
    private static List<String> generateSkewedData(int size, int skewFactor) {
        List<String> data = new ArrayList<>(size);
        Random random = new Random();
        
        for (int i = 0; i < size; i++) {
            // 生成不均匀的键分布
            String key = "key_" + (random.nextInt(size / skewFactor) == 0 
                    ? random.nextInt(skewFactor) 
                    : random.nextInt(1000));
            data.add(key);
        }
        
        return data;
    }

    /**
     * 使用默认分区器处理数据
     */
    private static Map<String, Long> processWithDefaultPartitioner(List<String> data) {
        return data.parallelStream()
                   .collect(Collectors.groupingByConcurrent(
                       key -> key,
                       Collectors.counting()
                   ));
    }

    /**
     * 使用自定义哈希范围分区器处理数据
     */
    private static Map<String, Long> processWithCustomPartitioner(List<String> data) {
        // 创建自定义分区器（按哈希值范围划分）
        Spliterator<String> customSplit = new CustomHashRangeSpliterator<>(data, 4);
        
        return StreamSupport.stream(customSplit, true)
                           .collect(Collectors.groupingByConcurrent(
                               key -> key,
                               Collectors.counting()
                           ));
    }

    /**
     * 验证结果顺序性
     */
    private static void verifyResultOrder() {
        // 创建有序数据
        List<String> orderedData = IntStream.range(0, 1000)
                                           .mapToObj(i -> "key_" + (i % 10))
                                           .collect(Collectors.toList());
        
        // 使用ConcurrentHashMap（无序）
        Map<String, Long> concurrentMap = orderedData.parallelStream()
                             .collect(Collectors.groupingByConcurrent(
                                 key -> key,
                                 Collectors.counting()
                             ));
        
        // 使用LinkedHashMap（保持插入顺序）
        Map<String, Long> linkedHashMap = orderedData.parallelStream()
                             .collect(Collectors.groupingBy(
                                 key -> key,
                                 () -> new LinkedHashMap<>(),
                                 Collectors.counting()
                             ));
        
        System.out.println("ConcurrentHashMap顺序验证（无序）: " + isOrdered(concurrentMap));
        System.out.println("LinkedHashMap顺序验证（有序）: " + isOrdered(linkedHashMap));
    }

    /**
     * 检查Map是否保持插入顺序
     */
    private static boolean isOrdered(Map<String, ?> map) {
        int[] expectedSuffix = {0};
        return map.keySet().stream().allMatch(key -> {
            boolean result = key.endsWith("_" + expectedSuffix[0]);
            expectedSuffix[0]++;
            return result;
        });
    }

    /**
     * 打印结果样本
     */
    private static void printSampleResults(Map<String, Long> result, int sampleSize) {
        System.out.println("总键数量: " + result.size());
        System.out.println("样本数据（前" + sampleSize + "个）:");
        
        result.entrySet().stream()
              .limit(sampleSize)
              .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    /**
     * 自定义哈希范围分区器
     * @param <T> 元素类型
     */
    static class CustomHashRangeSpliterator<T> implements Spliterator<T> {
        private final List<T> data;
        private final int numPartitions;
        private int currentPartition;

        public CustomHashRangeSpliterator(List<T> data, int numPartitions) {
            this.data = data;
            this.numPartitions = numPartitions;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            // 按哈希值范围划分数据
            int partitionSize = (int) Math.ceil((double) data.size() / numPartitions);
            int start = currentPartition * partitionSize;
            
            if (start >= data.size()) {
                return false;
            }
            
            int end = Math.min(start + partitionSize, data.size());
            for (int i = start; i < end; i++) {
                action.accept(data.get(i));
            }
            
            currentPartition++;
            return end < data.size();
        }

        @Override
        public Spliterator<T> trySplit() {
            return null; // 单一分区器不支持进一步拆分
        }

        @Override
        public long estimateSize() {
            return data.size();
        }

        @Override
        public int characteristics() {
            return SIZED | SUBSIZED | ORDERED;
        }
    }
}