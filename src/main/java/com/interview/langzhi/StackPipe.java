package com.interview.langzhi;

import java.util.Random;

/**
 * 栈型队列类
 * @author yangkuo
 * @date 2025/6/19
 * @description
 */
public class StackPipe<T> {
    // 栈顶指针
    private Node<T> top;
    // 栈底指针
    private Node<T> bottom;
    // 栈节点计算器
    private int size;

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
    }

    /**
     * 栈操作， 取出栈顶数据
     * @return
     */
    public T pop() {
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
    }

    /**
     * 队列操作, 以队列的方法取出一个数据，就是栈的底部元素
     * @return
     */
    public T pop2() {
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
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断蛇是否全部被吃掉
     * @return
     */
    public boolean isAllBitten() {
        Node<T> current = top;
        while (current != null) {
            if (!current.isBitten()) {
                return false;
            }
            current = current.getDown();
        }
        return true;
    }

    public boolean eat(StackPipe<T> otherSnake) {
        if (otherSnake.isEmpty()) {
            return false;
        }

        Random rand = new Random();
        int index = rand.nextInt(otherSnake.getSize());

        Node<T> current = otherSnake.top;
        for (int i = 0; i < index; i++) {
            current = current.getDown();
        }

        if (!current.isBitten()) {
            current.setBitten(true);
            return true;
        } else {
            System.out.println("Miss!");
            return false;
        }
    }

    /**
     * 打印蛇的身体，每段是否被咬过
     */
    private void printSnakeBody() {
        for (Node<T> current = top; current != null; current = current.getDown()) {
            System.out.print((current.isBitten() ? "X" : "O") + " -> ");
        }
        System.out.print("null");
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

    public static void main(String[] args) {
        StackPipe<Integer> snakeA = new StackPipe<>();
        StackPipe<Integer> snakeB = new StackPipe<>();

        // 初始化蛇的身体（各10个节点）
        for (int i = 1; i <= 10; i++) {
            snakeA.push(i);
            snakeB.push(i);
        }

        while (true) {
            // 蛇A攻击蛇B
            if (snakeA.eat(snakeB)) {
                System.out.println("Snake A bites Snake B!");
            }
            // 检查胜利条件
            if (snakeB.isAllBitten()) {
                snakeA.printSnakeBody();
                System.out.println("Snake A wins!");
                break;
            }

            // 蛇B攻击蛇A
            if (snakeB.eat(snakeA)) {
                System.out.println("Snake B bites Snake A!");
            }
            // 检查胜利条件
            if (snakeA.isAllBitten()) {
                snakeB.printSnakeBody();
                System.out.println("Snake B wins!");
                break;
            }
        }
    }

    public static void mainOld(String[] args) {
        StackPipe<Integer> stackPipe = new StackPipe<>();

        System.out.println("===== 初始化栈 =====");
        stackPipe.push(1);
        stackPipe.push(2);
        stackPipe.push(3);

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