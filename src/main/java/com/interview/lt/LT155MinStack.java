package com.interview.lt;

import java.util.Stack;

/**
 * 155. 最小栈
 * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 * 难度: 简单
 * 标签: 栈, 设计
 */
public class LT155MinStack {
    // 主栈用于存储所有元素
    private Stack<Integer> stack;
    // 辅助栈用于存储每个时刻的最小值
    private Stack<Integer> minStack;

    // 初始化堆栈
    public LT155MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    // 入栈操作
    public void push(int val) {
        stack.push(val);
        
        // 如果辅助栈为空或当前值小于等于辅助栈顶元素，则入栈到辅助栈
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    // 出栈操作
    public void pop() {
        // 如果栈不为空，弹出主栈元素
        if (!stack.isEmpty()) {
            int popped = stack.pop();
            
            // 如果弹出的元素等于辅助栈栈顶元素，则也从辅助栈中弹出
            if (!minStack.isEmpty() && popped == minStack.peek()) {
                minStack.pop();
            }
        }
    }

    // 获取栈顶元素
    public int top() {
        // 返回栈顶元素（如果栈不为空）
        return !stack.isEmpty() ? stack.peek() : -1;
    }

    // 获取栈中的最小元素
    public int getMin() {
        // 返回辅助栈的栈顶元素（如果栈不为空）
        return !minStack.isEmpty() ? minStack.peek() : -1;
    }

    // 测试用例
    public static void main(String[] args) {
        LT155MinStack minStack = new LT155MinStack();
        
        minStack.push(2);
        minStack.push(0);
        minStack.push(3);
        minStack.push(0);
        
        System.out.println("获取最小元素: " + minStack.getMin()); // 应该输出0
        
        minStack.pop();
        System.out.println("获取最小元素: " + minStack.getMin()); // 应该输出0
        
        minStack.pop();
        System.out.println("获取最小元素: " + minStack.getMin()); // 应该输出0
        
        minStack.pop();
        System.out.println("获取最小元素: " + minStack.getMin()); // 应该输出2
    }
}