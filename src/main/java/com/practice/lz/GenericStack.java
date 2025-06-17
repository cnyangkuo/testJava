package com.practice.lz;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型堆栈实现及扩展操作
 * 考察点：泛型、栈操作、设计模式
 * @param <T> 元素类型，需支持比较操作
 */
public class GenericStack<T extends Comparable<T>> {
    private List<T> stack;
    
    public GenericStack() {
        this.stack = new ArrayList<>();
    }
    
    /**
     * 题目1：实现基本的栈操作
     * @param item 压入的元素
     */
    public void push(T item) {
        stack.add(item);
    }
    
    /**
     * 弹出栈顶元素
     * @return 栈顶元素
     * @throws IllegalStateException 如果栈为空
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("栈为空");
        }
        return stack.remove(stack.size() - 1);
    }
    
    /**
     * 查看栈顶元素
     * @return 栈顶元素
     * @throws IllegalStateException 如果栈为空
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("栈为空");
        }
        return stack.get(stack.size() - 1);
    }
    
    /**
     * 判断栈是否为空
     * @return true if empty
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    /**
     * 获取栈中元素个数
     * @return 元素数量
     */
    public int size() {
        return stack.size();
    }
    
    /**
     * 题目2：在O(1)时间内获取最小值
     * 核心思想：辅助栈同步记录最小值
     */
    private GenericStack<T> minStack;
    
    public void pushWithMin(T item) {
        if (minStack == null) {
            minStack = new GenericStack<>();
        }
        stack.add(item);
        // 如果当前元素小于等于辅助栈栈顶元素，则将当前元素压入辅助栈
        if (minStack.isEmpty() || item.compareTo(minStack.peek()) <= 0) {
            minStack.push(item);
        }
    }
    
    public T popWithMin() {
        if (minStack == null) {
            throw new IllegalStateException("栈为空");
        }
        if (isEmpty()) {
            throw new IllegalStateException("栈为空");
        }
        T item = stack.remove(stack.size() - 1);
        // 如果当前元素等于辅助栈栈顶元素，则将辅助栈栈顶元素弹出
        if (!minStack.isEmpty() && item.equals(minStack.peek())) {
            minStack.pop();
        }
        return item;
    }
    
    public T getMin() {
        if (minStack == null || minStack.isEmpty()) {
            throw new IllegalStateException("栈为空");
        }
        return minStack.peek();
    }
    
    /**
     * 题目3：栈排序（仅使用一个额外栈） 从栈顶到栈底进行排序 从小到大排序
     * 核心思想：临时栈辅助排序
     */
    public void sort() {
        if (isEmpty()) {
            return;
        }
        // 临时栈，栈顶是最大数
        GenericStack<T> tempStack = new GenericStack<>();
        
        while (!isEmpty()) {
            T current = pop();
            // 当前元素比临时栈顶大时，将临时栈元素移回原栈；因为current必须比临时栈的栈顶元素大才能压入临时栈
            while (!tempStack.isEmpty() && current.compareTo(tempStack.peek()) < 0) {
                push(tempStack.pop());
            }
            // 将当前元素压入临时栈
            tempStack.push(current);
        }
        
        // 将元素移回原栈，栈顶到栈底 临时栈为从大到小，当前栈变为从小到大
        while (!tempStack.isEmpty()) {
            push(tempStack.pop());
        }
    }
    
    /**
     * 辅助方法：打印栈内容（从栈顶到栈底）
     */
    public void printStack() {
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.print(stack.get(i) + " -> ");
        }
        System.out.println("null");
    }
    
    public static void main(String[] args) {
        // 测试用例1：基本栈操作
        GenericStack<Integer> stack1 = new GenericStack<>();
        System.out.println("测试基本栈操作:");
        
        Integer[] elements1 = {3, 1, 4, 1, 5};
        for (Integer element : elements1) {
            stack1.push(element);
        }
        
        System.out.println("初始栈:");
        stack1.printStack();
        
        System.out.println("弹出元素: " + stack1.pop());
        System.out.println("弹出后栈:");
        stack1.printStack();
        
        // 测试用例2：带最小值跟踪的栈
        GenericStack<Integer> stack2 = new GenericStack<>();
        System.out.println("\n测试带最小值跟踪的栈:");
        
        Integer[] elements2 = {5, 2, 3, 1, 4};
        for (Integer element : elements2) {
            stack2.pushWithMin(element);
        }
        
        System.out.println("栈内容:");
        for (int i = stack2.size() - 1; i >= 0; i--) {
            System.out.println("元素: " + elements2[i] + ", 当前最小值: " + stack2.getMin());
        }
        
        // 测试用例3：栈排序
        GenericStack<Integer> stack3 = new GenericStack<>();
        Integer[] elements3 = {4, 2, 5, 1, 3};
        for (Integer element : elements3) {
            stack3.push(element);
        }
        
        System.out.println("\n测试栈排序:");
        System.out.println("排序前:");
        stack3.printStack();
        
        stack3.sort();
        System.out.println("排序后:");
        stack3.printStack();
    }
}