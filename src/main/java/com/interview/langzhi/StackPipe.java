package com.interview.langzhi;

/**
 * 栈型队列类
 * @author yangkuo
 * @date 2025/6/19
 * @description
 */
public class StackPipe<T> {
    private Node<T> top;
    private Node<T> bottom;

    public StackPipe() {
        this.top = null;
        this.bottom = null;
    }

    /**
     * 栈操作, 讲新的数据压入栈顶
     * @param data
     */
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        if (top == null) {
            top = newNode;
            bottom = newNode;
        } else {
            newNode.setUp(top);
            top.setDown(newNode);
            top = newNode;
        }
    }

    /**
     * 栈操作， 取出一个数据
     * @return
     */
    public T pop() {
        if (top == null) {
            return null;
        }
        T data = top.getData();

        // 栈顶元素移除
        top = top.getDown();
        if (top != null) {
            top.setUp(null);
        } else {
            bottom = null;
        }
        return data;
    }

    /**
     * 队列操作, 以队列的方法取出一个数据
     * @return
     */
    public T pop2() {
        if (bottom == null) {
            return null;
        }
        T data = bottom.getData();
        bottom = bottom.getUp();
        if (bottom != null) {
            bottom.setDown(null);
        } else {
            top = null;
        }
        return data;
    }

    public static void main(String[] args) {
        StackPipe<Integer> stackPipe = new StackPipe<>();

        // 测试基本功能
        System.out.println("===== 栈操作测试 =====");
        stackPipe.push(1);
        stackPipe.push(2);
        stackPipe.push(3);

        System.out.println("栈弹出顺序验证:");
        System.out.println("pop() -> " + stackPipe.pop());  // 应输出3
        System.out.println("pop() -> " + stackPipe.pop());  // 应输出2
        System.out.println("pop() -> " + stackPipe.pop());  // 应输出1

        // 重新填充数据
        stackPipe.push(1);
        stackPipe.push(2);
        stackPipe.push(3);

        System.out.println("\n===== 队列操作测试 =====");
        System.out.println("队列弹出顺序验证:");
        System.out.println("pop2() -> " + stackPipe.pop2());  // 应输出1
        System.out.println("pop2() -> " + stackPipe.pop2());  // 应输出2
        System.out.println("pop2() -> " + stackPipe.pop2());  // 应输出3

    }
}