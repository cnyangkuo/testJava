package com.practice.thread;

/**
 * @author yangkuo
 * @date 2025/7/4
 * @description
 */
public class ThreadStateDeepDive {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // (1) 调用 wait() 释放锁并进入 WAITING 状态
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long sum = 0;
                for (long i = 0; i < 1000000002L; i++) {
                    sum += i;
                }
                System.out.println("t1 sum: " + sum);
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                lock.notify(); // (3) 通知 t1，但 t2 退出同步块才释放锁
                // t2 在此处执行时，t1 被通知了，但因为锁还在 t2 手里，t1 无法立即运行，它会进入 BLOCKED 状态等待锁！
                long sum = 0;
                for (long i = 0; i < 1000000000L; i++) {
                    sum += i;
                }
                System.out.println("t2 sum: " + sum);
            }
        });

        System.out.println("t1 state after creation: " + t1.getState()); // NEW
        t1.start();
        Thread.sleep(100); // 确保 t1 启动并运行到 wait()
        System.out.println("t1 state after start and wait(): " + t1.getState()); // WAITING

        t2.start();
        Thread.sleep(100); // 确保 t2 启动并运行到 synchronized 块内
        System.out.println("t2 state inside synchronized after notify: " + t2.getState()); // RUNNABLE (可能在运行，也可能就绪)
        System.out.println("t1 state after t2 notify but before t2 releases lock: " + t1.getState()); // BLOCKED (等待 lock 的监视器锁)!!!

        Thread.sleep(1000); // 确保 t2 执行完同步块释放锁
        System.out.println("t1 state after t2 releases lock: " + t1.getState()); // RUNNABLE -> TERMINATED (很快执行完)
    }
}
