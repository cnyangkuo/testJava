package com.interview.langzhi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 栈型队列类
 * @author yangkuo
 * @date 2025/6/19
 * @description 实现Iterable接口以支持迭代器
 */
public class StackPipe<T> implements Iterable<T>, Serializable {
    // 声明序列化版本ID
    private static final long serialVersionUID = 1L;
    // 栈顶指针 - transient表示不直接序列化链表结构
    private transient Node<T> top;
    // 栈底指针 - transient表示不直接序列化链表结构
    private transient Node<T> bottom;
    // 新增计数器
    private int size;
    // 锁 - transient表示不直接序列化锁
    private transient Lock lock = new ReentrantLock();

    public StackPipe() {
        this.top = null;
        this.bottom = null;
        this.size = 0;
    }

    public StackPipe(int length) {
        if (length <= 0) return;
        Node<T> current = new Node<>((T) Integer.valueOf(1));  // 修正类型转换
        this.top = current;
        this.size = 1;

        for (int i = 2; i <= length; i++) {
            Node<T> newNode = new Node<>((T) Integer.valueOf(i));  // 修正类型转换
            current.setDown(newNode);
            newNode.setUp(current);
            current = newNode;
            size++;
        }
        this.bottom = current;
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
    public void printStack() {
        for (Node<T> current = top; current != null; current = current.getDown()) {
            System.out.println(current.getData());
//            if (current == bottom) {
//                System.out.print("栈底部");
//            }
        }
        System.out.println();
    }

    /**
     * 打印列表，即从栈底到栈顶打印栈内容
     */
    public void printList() {
        for (Node<T> current = bottom; current != null; current = current.getUp()) {
            System.out.print(current.getData());
            if (current != top) {
                System.out.print(",");
            }
        }
        System.out.println();
    }



    public Node getNodeAt(int index) {
        if (index < 1 || index > size) return null;

        Node current = top;
        for (int i = 1; i < index && current != null; i++) {
            current = current.getDown();
        }
        return current;
    }

    public boolean eat(StackPipe<Node> enemy) {
        if (enemy == null || enemy.size == 0) {
            return false;
        }

        int k = new Random().nextInt(10);
        Node target = enemy.getNodeAt(k);

        if (target == null) {
            return false;
        }

        if (target.getUp() != null) {
            target.getUp().setDown(null);
            enemy.bottom = target.getUp();
        } else {
            enemy.top = null;
            enemy.bottom = null;
        }

        enemy.size = k - 1;
        return true;
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

    /**
     * 自定义序列化方法
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        lock.lock();
        try {
            List<T> elements = new ArrayList<>();
            Node<T> current = top;
            while (current != null) {
                elements.add(current.getData());
                current = current.getDown();
            }
            out.defaultWriteObject(); // 序列化非transient字段
            out.writeInt(elements.size());
            for (T element : elements) {
                out.writeObject(element);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 自定义反序列化方法
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        lock = new ReentrantLock(); // 重新初始化锁
        in.defaultReadObject(); // 反序列化非transient字段

        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            T element = (T) in.readObject();
            push(element);
        }
    }

    public static void main(String[] args) {
        // 初始化两条蛇
        StackPipe<Node> snakeA = new StackPipe<>(10);
        StackPipe<Node> snakeB = new StackPipe<>(10);

        int round = 1;
        while (snakeA.size > 0 && snakeB.size > 0) {
            System.out.println("=== 第 " + round + " 回合 ===");

            // 蛇A攻击
            if (snakeA.eat(snakeB)) {
                System.out.println("蛇A攻击成功！蛇B剩余 " + snakeB.size + " 个节点");
            } else {
                System.out.println("蛇A攻击失败");
            }

            // 检查胜负
            if (snakeB.size == 0) break;

            // 蛇B反击
            if (snakeB.eat(snakeA)) {
                System.out.println("蛇B反击成功！蛇A剩余 " + snakeA.size + " 个节点");
            } else {
                System.out.println("蛇B反击失败");
            }

            System.out.println("------------------------------");
            round++;
        }

        // 输出胜利信息
        System.out.println("\n胜利者：" + (snakeA.size > 0 ? "蛇A" : "蛇B"));
        System.out.println("最终战果：");
        System.out.println("蛇A长度: " + snakeA.size);
        System.out.println("蛇B长度: " + snakeB.size);
    }

    public static void main0(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("===== 初始化栈 =====");
//        StackPipe<Integer> stackPipe = new StackPipe<>();
//        stackPipe.push(1);
//        stackPipe.push(2);
//        stackPipe.push(3);
        StackPipe<Integer> stackPipe = StackPipe.create(Arrays.asList(1, 2, 3));

        System.out.println("===== 序列化并反序列化测试 =====");
        // 创建临时文件路径
        String tempFilePath = "/tmp/stackpipe.ser";
        // 序列化
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFilePath))) {
            oos.writeObject(stackPipe);
        }
        // 反序列化
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFilePath))) {
            @SuppressWarnings("unchecked")
            StackPipe<Integer> deserializedStack = (StackPipe<Integer>) ois.readObject();
            System.out.println("反序列化后的栈内容:");
            deserializedStack.printList();
            deserializedStack.printStack();
        }

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