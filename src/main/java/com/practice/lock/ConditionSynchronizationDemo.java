package com.practice.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionSynchronizationDemo {
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    private final Object[] items = new Object[2]; // 小容量便于演示
    private int putIndex, takeIndex, count;

    public void put(Object item) throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获取到锁，准备放入元素");

            while (count == items.length) {
                System.out.println(Thread.currentThread().getName() + " 队列已满，等待...");
                notFull.await(); // 释放锁并等待
            }

            System.out.println(Thread.currentThread().getName() + " 放入元素: " + item);
            items[putIndex] = item;
            putIndex = (putIndex + 1) % items.length;
            count++;

            System.out.println(Thread.currentThread().getName() + " 唤醒消费者");
            notEmpty.signal(); // 唤醒消费者，但此时锁还未释放

            // 这里是关键：signal执行后，消费者并不会立即执行
            System.out.println(Thread.currentThread().getName() + " signal执行完毕，但还未释放锁");
            Thread.sleep(2000); // 模拟一些处理时间
            System.out.println(Thread.currentThread().getName() + " 准备释放锁");
        } finally {
            lock.unlock(); // 只有这里执行后，被唤醒的消费者才能继续执行
            System.out.println(Thread.currentThread().getName() + " 已释放锁");
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获取到锁，准备取出元素");

            while (count == 0) {
                System.out.println(Thread.currentThread().getName() + " 队列为空，等待...");
                notEmpty.await(); // 释放锁并等待
            }

            Object item = items[takeIndex];
            System.out.println(Thread.currentThread().getName() + " 取出元素: " + item);
            takeIndex = (takeIndex + 1) % items.length;
            count--;

            System.out.println(Thread.currentThread().getName() + " 唤醒生产者");
            notFull.signal(); // 唤醒生产者，但此时锁还未释放

            // 这里是关键：signal执行后，生产者并不会立即执行
            System.out.println(Thread.currentThread().getName() + " signal执行完毕，但还未释放锁");
            Thread.sleep(2000); // 模拟一些处理时间
            System.out.println(Thread.currentThread().getName() + " 准备释放锁");

            return item;
        } finally {
            lock.unlock(); // 只有这里执行后，被唤醒的生产者才能继续执行
            System.out.println(Thread.currentThread().getName() + " 已释放锁");
        }
    }

    public static void main(String[] args) {
        ConditionSynchronizationDemo demo = new ConditionSynchronizationDemo();

        // 生产者线程
        Thread producer = new Thread(() -> {
            try {
                demo.put("item1");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        // 消费者线程
        Thread consumer = new Thread(() -> {
            try {
                demo.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        consumer.start();
        try {
            Thread.sleep(1000); // 确保消费者先启动并等待
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        producer.start();
    }
}
