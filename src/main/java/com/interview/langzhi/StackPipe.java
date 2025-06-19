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
            newNode.setDown(top);
            top.setUp(newNode);
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
     * 队列操作, 以队列的方法取出一个数据，就是栈的底部元素
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

    /**
     * 从栈顶到栈底打印栈内容
     * @return
     */
    private void printStack() {
        for (Node<T> current = top; current != null; current = current.getDown()) {
            System.out.print(current.getData() + " -> ");
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        StackPipe<Integer> stackPipe = new StackPipe<>();

        // 测试基本功能
        System.out.println("===== 栈操作测试 =====");
        stackPipe.push(1);
        stackPipe.push(2);
        stackPipe.push(3);


        System.out.println("===== 打印栈内容 =====");
        stackPipe.printStack();

        // pop 取数验证， 期望输出的是3
        System.out.println("栈顶元素验证:");
        System.out.println("top() -> " + stackPipe.pop());

        // POP2取数验证, 期望输出1
        System.out.println("队列元素验证:");
        System.out.println("pop2() -> " + stackPipe.pop2());

    }

}