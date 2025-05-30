package com.practice;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author yangkuo
 * @date 2025/5/26
 * @description
 */
public class LightweightLockExample {
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main:" + ClassLayout.parseInstance(lock).toPrintable());
        // 线程 A：先执行同步块
        Thread threadA = new Thread(() -> {
            System.out.println("threadA00:" + ClassLayout.parseInstance(lock).toPrintable());
            synchronized (lock) {
                System.out.println("Thread A 获取锁，执行同步代码块");
                System.out.println("threadA01:" + ClassLayout.parseInstance(lock).toPrintable());
                try {
                    Thread.sleep(1000); // 模拟业务逻辑耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread A 释放锁");
                System.out.println("threadA02:" + ClassLayout.parseInstance(lock).toPrintable());
            }

            System.out.println("threadA10:" + ClassLayout.parseInstance(lock).toPrintable());
//            synchronized (lock) {
//                System.out.println("Thread A 获取锁，执行同步代码块");
//                System.out.println("threadA11:" + ClassLayout.parseInstance(lock).toPrintable());
//                try {
//                    Thread.sleep(1000); // 模拟业务逻辑耗时
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Thread A 释放锁");
//                System.out.println("threadA12:" + ClassLayout.parseInstance(lock).toPrintable());
//            }
//            System.out.println("threadA13:" + ClassLayout.parseInstance(lock).toPrintable());
        });

//        // 线程 B：稍后启动，确保在 A 执行完成后才尝试获取锁
//        Thread threadB = new Thread(() -> {
//            try {
//                Thread.sleep(200); // 确保 B 在 A 启动后稍等再运行
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("threadB1:" + ClassLayout.parseInstance(lock).toPrintable());
//            synchronized (lock) {
//                System.out.println("Thread B 获取锁，执行同步代码块");
//                System.out.println("Thread B 释放锁");
//                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
//                System.out.println("threadB2:" + ClassLayout.parseInstance(lock).toPrintable());
//            }
//        });

        threadA.start();
//        threadB.start();
        threadA.join();
//        threadB.join();
    }
}