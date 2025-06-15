package com.practice.algorithm;

import java.util.concurrent.*;

/**
 * 基于时间轮算法实现的高性能定时器
 * 工作原理：使用环形数组作为时间轮，每个槽位存储该时刻到期的任务集合
 * 时间复杂度：任务添加O(1)，执行O(n)（单个槽位任务数）
 * 空间复杂度：O(N)（N为时间轮大小）
 * @author yangkuo
 * @date 2025/6/15
 */
public class RingTimer {
    // 默认时间轮大小
    private static final int DEFAULT_WHEEL_SIZE = 60;
    // 环形数组
    private final DelayTask[][] wheel;
    // 当前指针位置
    private volatile int currentTick = 0;
    // 总槽位数
    private final int wheelSize;
    // 单个tick的时间间隔（毫秒）
    private final long tickDuration;
    // 执行器服务
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @SuppressWarnings("unchecked")
    public RingTimer() {
        this(DEFAULT_WHEEL_SIZE, 1000); // 默认每秒触发一次
    }

    @SuppressWarnings("unchecked")
    public RingTimer(int wheelSize, long tickDuration) {
        this.wheelSize = wheelSize;
        this.tickDuration = tickDuration;
        // 初始化二维数组结构
        wheel = (DelayTask[][]) new DelayTask[wheelSize][0];
        for (int i = 0; i < wheelSize; i++) {
            wheel[i] = new DelayTask[0];
        }
        
        // 启动时间轮推进任务
        executor.scheduleAtFixedRate(this::advanceWheel, tickDuration, tickDuration, TimeUnit.MILLISECONDS);
    }

    /**
     * 将任务加入时间轮
     * @param task 延迟任务
     * @param delay 延迟时间（毫秒）
     */
    public void schedule(DelayTask task, long delay) {
        // 计算需要经过的tick数
        int ticks = (int) Math.ceil(delay / (double)tickDuration);
        // 计算在时间轮中的索引
        int index = (currentTick + ticks) % wheelSize;
        
        // 线程安全地添加任务
        synchronized (this) {
            DelayTask[] oldTasks = wheel[index];
            DelayTask[] newTasks = new DelayTask[oldTasks.length + 1];
            System.arraycopy(oldTasks, 0, newTasks, 0, oldTasks.length);
            newTasks[oldTasks.length] = task;
            wheel[index] = newTasks;
        }
    }

    // 推进时间轮
    private void advanceWheel() {
        // 移动指针
        currentTick = (currentTick + 1) % wheelSize;
        // 获取当前需要执行的任务
        DelayTask[] tasks = wheel[currentTick];
        // 执行所有到期任务
        for (DelayTask task : tasks) {
            if (task != null) {
                task.run();
            }
        }
        // 清空已执行的槽位
        wheel[currentTick] = new DelayTask[0];
    }

    // 关闭定时器
    public void shutdown() {
        executor.shutdown();
    }

    // 延迟任务接口
    public interface DelayTask {
        void run();
    }

    public static void main(String[] args) throws InterruptedException {
        // 测试示例：创建时间轮
        RingTimer timer = new RingTimer();
        
        // 添加延迟任务
        timer.schedule(() -> System.out.println("Task 1 executed at " + System.currentTimeMillis()), 2000);
        timer.schedule(() -> System.out.println("Task 2 executed at " + System.currentTimeMillis()), 3000);
        timer.schedule(() -> System.out.println("Task 3 executed at " + System.currentTimeMillis()), 5000);
        
        // 保持运行足够长的时间以观察任务执行
        Thread.sleep(6000);
        timer.shutdown();
    }
}
