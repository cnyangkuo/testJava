package com.interview.sc;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * ForkJoin批处理器
 * 1. 演示RecursiveTask的分解与合并机制
 * 2. 使用ArrayDeque实现任务队列
 * 3. 工作窃取算法的具体实现
 *
 * Q：为什么fork()使用push到队列头部，而窃取从尾部进行？
 * A：设计考虑三点：
 * 缓存局部性：最新任务（LIFO）更可能还在CPU缓存中
 * 任务优先级：大任务拆分的小任务应优先执行（深度优先）
 * 减少竞争：窃取老任务（FIFO）减少工作线程与新任务的竞争
 *
 * Q：什么情况下该避免使用ForkJoinPool？
 * A：不适合场景包括：
 * I/O密集型任务：线程阻塞浪费资源
 * 同步操作过多：破坏无锁设计优势
 * 任务粒度不均：某些任务耗时过长导致窃取失效
 * 小任务过多：任务调度开销超过执行时间
 *
 *
 * @author hanson
 */
public class ForkJoinBatchProcessor extends RecursiveTask<Integer> {
    // 任务阈值，小于该值直接计算
    private static final int THRESHOLD = 4;
    private final int[] data;
    private final int start;
    private final int end;

    public ForkJoinBatchProcessor(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {
        // 生成一个从1到100的int[]
        int[] dataArray = IntStream.rangeClosed(1, 100).toArray();
        int result = ForkJoinBatchProcessor.batchProcess(dataArray);
        System.out.println("ForkJoin处理结果: " + result);
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