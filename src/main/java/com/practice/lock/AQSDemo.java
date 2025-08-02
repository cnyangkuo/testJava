package com.practice.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void awaitMethod() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获取锁并准备等待");
            // 调用await时，线程会从同步队列转移到条件等待队列
            condition.await();
            System.out.println(Thread.currentThread().getName() + " 被唤醒并重新获取锁");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void signalMethod() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获取锁并准备唤醒");
            // 调用signal时，线程从条件等待队列转移到同步队列
            condition.signal();
            System.out.println(Thread.currentThread().getName() + " 唤醒一个等待线程");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AQSDemo demo = new AQSDemo();

        // 创建等待线程
        Thread awaitThread = new Thread(demo::awaitMethod, "AwaitThread");
        awaitThread.start();

        // 确保awaitThread先执行并进入等待状态
        Thread.sleep(1000);

        // 创建唤醒线程
        Thread signalThread = new Thread(demo::signalMethod, "SignalThread");
        signalThread.start();

        awaitThread.join();
        signalThread.join();
    }
}
