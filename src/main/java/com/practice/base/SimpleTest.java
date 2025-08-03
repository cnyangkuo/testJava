package com.practice.base;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示 ConcurrentHashMap compute 方法的线程安全操作
 * 包含原子计数器、读写锁优化等典型应用场景
 * 符合金融计算规范中的高精度计算要求
 * @author hanson
 */
public class SimpleTest {
    public static void main(String[] args) throws InterruptedException {
        SimpleTest test = new SimpleTest();
        test.testComputeForCounter();
        test.testComputeWithLock();
        test.testComputeConcurrency();

        ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();
    }
    
    /**
     * 使用 compute 实现原子计数器
     * 确保多线程环境下数值的原子性更新
     * 遵循防御性编程要求处理 null 值
     */
    public void testComputeForCounter() {
        ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();
        
        // 初始化键值对
        counter.put("request", 0);
        counter.putIfAbsent("request", 3); // 不会修改键值对
        counter.computeIfAbsent("request", (key) -> 5); // 不会修改键值对
        counter.computeIfPresent("request", (key, value) -> { value += 10; return value; } );

        // 多线程安全更新示例
        for (int i = 0; i < 10; i++) {
            counter.compute("request", (key, value) -> {
                // 处理 null 值情况
                if (value == null) {
                    return 1; // 初始值
                }
                return value + 1; // 原子递增
            });
        }
        System.out.println(counter.get("request"));
    }

    public void testComputeConcurrency() throws InterruptedException {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        int THREAD_COUNT = 100;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                map.compute("count", (k, v) -> v == null ? 1 : v + 1);
            }
            latch.countDown();
        };

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(task).start();
        }

        latch.await();
        System.out.println("count=" + map.get("count"));
    }
    
    /**
     * 使用读写锁优化汇率计算场景
     * 符合并发编程框架应用规范中的读写分离策略
     * 适用于读多写少的场景（如汇率服务）
     */
    public void testComputeWithLock() {
        ConcurrentHashMap<String, Double> rates = new ConcurrentHashMap<>();
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        
        // 写操作使用独占锁
        lock.writeLock().lock();
        try {
            rates.compute("USD/CNY", (key, rate) -> {
                // 复杂计算逻辑
                return calculateExchangeRate();
            });
        } finally {
            lock.writeLock().unlock();
        }
        
        // 读操作使用共享锁
        lock.readLock().lock();
        try {
            Double currentRate = rates.get("USD/CNY");
            // 使用 currentRate 进行业务逻辑处理
            System.out.println("汇率：" + currentRate);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    // 辅助方法：模拟汇率计算
    private Double calculateExchangeRate() {
        // 实际计算逻辑
        return 7.0;
    }
}
