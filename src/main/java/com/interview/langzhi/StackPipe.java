package com.interview.langzhi;

import java.util.Iterator;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 栈型队列类
 * @author yangkuo
 * @date 2025/6/19
 * @description 实现Iterable接口以支持迭代器
 */
public class StackPipe<T> implements Iterable<T> {
    // 栈顶指针
    private Node<T> top;
    // 栈底指针
    private Node<T> bottom;
    // 新增计数器
    private int size;
    // 锁
    private final Lock lock = new ReentrantLock();

    public StackPipe() {
        this.top = null;
        this.bottom = null;
        this.size = 0;
    }

    /**
     * 栈操作, 将新的数据压入栈顶
     * @param data
     */
    public void push(T data) {
        lock.lock();
        try {
            Node<T> newNode = new Node<>(data);
            if (top == null) {
                top = newNode;
                bottom = newNode;
            } else {
                newNode.setDown(top);
                top.setUp(newNode);
                top = newNode;
            }
            size++;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 栈操作， 取出栈顶数据
     * @return
     */
    public T pop() {
        lock.lock();
        try {
            if (top == null) {
                return null;
            }
            T data = top.getData();
            // 栈顶指针下移
            top = top.getDown();
            if (top != null) {
                // 栈顶元素移除
                top.setUp(null);
            } else {
                bottom = null;
            }
            size--;
            return data;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 队列操作, 以队列的方法取出一个数据，就是栈的底部元素
     * @return
     */
    public T pop2() {
        lock.lock();
        try {
            if (bottom == null) {
                return null;
            }
            T data = bottom.getData();
            // 栈底指针上移
            bottom = bottom.getUp();
            if (bottom != null) {
                // 栈底元素移除
                bottom.setDown(null);
            } else {
                top = null;
            }
            size--;
            return data;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 栈翻转
     */
    public void reverse() {
        lock.lock();
        try {
            if (size() <= 1) {
                return;
            }
            Node<T> oldTop = top;
            Node<T> oldBottom = bottom;

            Node<T> current = top;
            while (current != null) {
                Node<T> next = current.getDown();
                current.setDown(current.getUp());
                current.setUp(next);
                current = next;
            }
            top = oldBottom;
            bottom = oldTop;
        } finally {
            lock.unlock();
        }
    }

    // 查找中间节点（快慢指针法）
    public T findMiddle() {
        lock.lock();
        try {
            if (size() < 1) {
                return null;
            }

            Node<T> slow = top;
            Node<T> fast = top;

            while (fast != null && fast.getDown() != null) {
                slow = slow.getDown();
                fast = fast.getDown().getDown();
            }

            return slow.getData();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 从栈顶到栈底打印栈内容
     * @return
     */
    private void printStack() {
        for (Node<T> current = top; current != null; current = current.getDown()) {
            System.out.println(current.getData());
        }
        System.out.println();
    }

    public static <E> StackPipe<E> create(Collection<? extends E> c) {
        StackPipe<E> pipe = new StackPipe<>();
        for (E item : c) pipe.push(item);
        return pipe;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    /**
     * 迭代器实现类
     */
    private class StackIterator implements Iterator<T> {
        private Node<T> current = top;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Stack is empty");
            }
            T data = current.getData();
            current = current.getDown();
            return data;
        }
    }

    public static void main(String[] args) {
        System.out.println("===== 初始化栈 =====");
//        StackPipe<Integer> stackPipe = new StackPipe<>();
//        stackPipe.push(1);
//        stackPipe.push(2);
//        stackPipe.push(3);
        StackPipe<Integer> stackPipe = StackPipe.create(java.util.Arrays.asList(1, 2, 3));

        System.out.println("===== 使用迭代器打印栈内容 =====");
        for (Integer num : stackPipe) {
            System.out.println(num);
        }
        System.out.println();

        System.out.println("===== 栈翻转 =====");
        stackPipe.reverse();
        stackPipe.printStack();

        System.out.println("===== 栈再次翻转 =====");
        stackPipe.reverse();
        stackPipe.printStack();

        System.out.println("===== 查找中间节点 =====");
        System.out.println("中间节点: " + stackPipe.findMiddle());
        System.out.println();

        System.out.println("===== 打印栈内容(从栈顶到栈底) =====");
        stackPipe.printStack();

        // POP 取数验证， 期望输出3
        System.out.println("栈顶元素验证:");
        System.out.println("pop() -> " + stackPipe.pop());

        // POP2取数验证, 期望输出1
        System.out.println("队列元素验证:");
        System.out.println("pop2() -> " + stackPipe.pop2());

        System.out.println();
        System.out.println("===== 再次打印栈内容(从栈顶到栈底) =====");
        stackPipe.printStack();
    }

}