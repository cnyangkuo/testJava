package com.practice.algorithm;

/**
 * @author hanson
 */
public class BlockingQueueDemo {
    static class BlockingQueue<T> {
        private Object[] items;
        private int head;
        private int tail;
        private int count;
        private int capacity;
        private Object lock = new Object();
        public BlockingQueue(int capacity) {
            this.capacity = capacity;
            items = new Object[capacity];
        }
        public void enqueue(T item) {
            synchronized (lock) {
                while (count == capacity) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                items[tail] = item;
                tail = (tail + 1) % capacity;
                count++;
                lock.notifyAll();
                System.out.println("enqueue: " + item + " head: " + head + " tail: " + tail + " count: " + count);
            }
        }

        public T dequeue() {
            synchronized (lock) {
                while (count == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                T item = (T) items[head];
                head = (head + 1) % capacity;
                count--;
                lock.notifyAll();
                System.out.println("dequeue: " + item + " head: " + head + " tail: " + tail + " count: " + count);
                return item;
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                queue.enqueue(i);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.dequeue();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.dequeue();
            }
        }).start();
    }

}
