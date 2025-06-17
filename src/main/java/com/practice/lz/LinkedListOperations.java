package com.practice.lz;

/**
 * 链表常见算法题实现，包含泛型操作
 * 考察点：链表操作、快慢指针、递归等
 * @param <T> 泛型类型
 */
public class LinkedListOperations<T> {
    // 链表节点定义
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node<T> head;
    
    public LinkedListOperations() {
        this.head = null;
    }
    
    /**
     * 题目1：反转单链表
     * 核心思想：三指针迭代法
     * @return 反转后的链表头节点
     */
    public Node<T> reverseList() {
        Node<T> prev = null;
        Node<T> current = head;
        
        while (current != null) {
            Node<T> next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        return prev;
    }
    
    /**
     * 题目2：检测链表是否有环
     * 核心思想：快慢指针法
     * @return true if cycle exists
     */
    public boolean hasCycle() {
        Node<T> slow = head;
        Node<T> fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 题目3：找到链表的中间节点
     * 核心思想：快慢指针法
     * @return 中间节点
     */
    public Node<T> findMiddle() {
        if (head == null) return null;
        
        Node<T> slow = head;
        Node<T> fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * 辅助方法：创建测试链表
     * @param elements 元素数组
     */
    public void createList(T[] elements) {
        if (elements == null || elements.length == 0) return;
        
        head = new Node<>(elements[0]);
        Node<T> current = head;
        
        for (int i = 1; i < elements.length; i++) {
            current.next = new Node<>(elements[i]);
            current = current.next;
        }
    }
    
    /**
     * 辅助方法：打印链表
     * @param node 头节点
     */
    public void printList(Node<T> node) {
        Node<T> current = node;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
    
    public static void main(String[] args) {
        // 测试用例1：普通链表反转
        LinkedListOperations<Integer> list1 = new LinkedListOperations<>();
        Integer[] elements1 = {1, 2, 3, 4, 5};
        list1.createList(elements1);
        System.out.println("原始链表:");
        list1.printList(list1.head);
        
        Node<Integer> reversed = list1.reverseList();
        System.out.println("反转后链表:");
        list1.printList(reversed);
        
        // 测试用例2：带环链表检测
        LinkedListOperations<Integer> list2 = new LinkedListOperations<>();
        Integer[] elements2 = {3, 2, 0, -4};
        list2.createList(elements2);
        // 创建环
        Node<Integer> current = list2.head;
        Node<Integer> cycleNode = null;
        int index = 0;
        while (current.next != null) {
            if (index == 1) cycleNode = current;
            current = current.next;
            index++;
        }
        current.next = cycleNode; // 形成环
        
        System.out.println("测试带环链表:");
        System.out.println("是否有环: " + list2.hasCycle());
        
        // 测试用例3：找中间节点
        LinkedListOperations<Integer> list3 = new LinkedListOperations<>();
        Integer[] elements3 = {1, 2, 3, 4, 5, 6};
        list3.createList(elements3);
        System.out.println("测试找中间节点:");
        Node<Integer> middle = list3.findMiddle();
        System.out.println("中间节点值: " + middle.data);
    }
}