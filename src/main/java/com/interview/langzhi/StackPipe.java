package com.interview.langzhi;

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

    public StackPipe() {
        this.top = null;
        this.bottom = null;
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

        System.out.println("===== 栈操作测试 初始化栈 =====");
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