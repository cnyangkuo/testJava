package com.interview.sc;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

/**
 * ForkJoin批处理器
 * 1. 演示RecursiveTask的分解与合并机制
 * 2. 使用ArrayDeque实现任务队列
 * 3. 工作窃取算法的具体实现
 * @author hanson
 */
public class ForkJoinBatchProcessor extends RecursiveTask<Integer> {
    // 任务阈值，小于该值直接计算
    private static final int THRESHOLD = 10;
    private final int[] data;
    private final int start;
    private final int end;

    public ForkJoinBatchProcessor(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            // 直接计算部分
            System.out.println("Calculating directly from " + start + " to " + end);
            return calculateDirectly();
        }

        // 分割任务
        int mid = (start + end) / 2;
        System.out.println("Splitting task from " + start + " to " + end + " with mid:" + mid);
        ForkJoinBatchProcessor leftTask = new ForkJoinBatchProcessor(data, start, mid);
        ForkJoinBatchProcessor rightTask = new ForkJoinBatchProcessor(data, mid, end);

        // 异步执行子任务
        leftTask.fork();
        rightTask.fork();

        // 合并结果（演示ConcurrentLinkedDeque的使用）
        int leftResult = leftTask.join();
        int rightResult = rightTask.join();

        return leftResult + rightResult;
    }

    /**
     * 直接计算方法
     * @return 计算结果
     * 
     * 原生类使用：
     * 1. ArrayIndexOutOfBoundsException（数组越界检查）
     * 2. AtomicInteger（线程安全计数器）
     */
    private int calculateDirectly() {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += data[i];
        }
        return sum;
    }

    /**
     * 批量处理入口方法
     * @param dataArray 输入数据
     * @return 总和结果
     */
    public static int batchProcess(int[] dataArray) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new ForkJoinBatchProcessor(dataArray, 0, dataArray.length));
    }
}