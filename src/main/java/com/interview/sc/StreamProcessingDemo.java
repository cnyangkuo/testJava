package com.interview.sc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Stream处理演示类
 * 1. 基于Spliterator实现数据分割
 * 2. 展示流水线操作的惰性求值机制
 * 3. 使用ForkJoinPool进行并行处理
 */
public class StreamProcessingDemo {
    // 核心数据结构使用ArrayList（默认Spliterator实现）
    private final List<Integer> dataList;

    public StreamProcessingDemo() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 初始化测试数据
     * 使用Arrays.asList生成不可变列表，通过ArrayList构造器创建可变副本
     */
    public void initData() {
        Integer[] dataArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Collections.addAll(dataList, dataArray);
    }

    /**
     * 并行流处理示例
     * @return 处理后的结果集
     * 
     * 源码分析：
     * 1. parallelStream()底层使用ForkJoinPool.commonPool()
     * 2. filter操作创建状态保留的Stream对象
     * 3. collect使用ConcurrentHashMap进行结果合并
     */
    public List<Integer> parallelProcess() {
        return dataList.parallelStream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * 2)
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
    }

    /**
     * 自定义Spliterator实现
     * 演示如何控制数据分割策略
     */
    public Spliterator<Integer> customSplit() {
        return new Spliterators.AbstractIntSpliterator(
            dataList.size(),
            Spliterator.ORDERED) {
            private int index = 0;

            @Override
            public boolean tryAdvance(IntConsumer action) {
                if (index < dataList.size()) {
                    action.accept(dataList.get(index++));
                    return true;
                }
                return false;
            }
        };
    }
}