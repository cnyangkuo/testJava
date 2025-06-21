package com.interview.lt;

/**
 * 演示快慢指针技术找链表中点的不同实现方式
 */
public class LinkedListMiddleFinder {
    
    // 重用LT141题中的ListNode定义
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    
    /**
     * 使用fast = head的方式找中点（奇数个节点）
     * @param head 链表头节点
     * @return 中间节点
     */
    public ListNode findMiddleWithFastAtHead(ListNode head) {
        if (head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        System.out.println("Using fast = head for odd length list");
        return slow;
    }
    
    /**
     * 使用fast = head.next的方式找中点（奇数个节点）
     * @param head 链表头节点
     * @return 中间节点
     */
    public ListNode findMiddleWithFastAtHeadNext(ListNode head) {
        if (head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head.next;  // 快指针比慢指针多走一步
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        System.out.println("Using fast = head.next for odd length list");
        return slow;
    }
    
    /**
     * 创建一个长度为奇数(5)的测试链表
     * @return 链表头节点
     */
    public ListNode createOddLengthList() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        return head;
    }
    
    /**
     * 创建一个长度为偶数(6)的测试链表
     * @return 链表头节点
     */
    public ListNode createEvenLengthList() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(6);
        return head;
    }
    
    public static void main(String[] args) {
        LinkedListMiddleFinder finder = new LinkedListMiddleFinder();
        
        // 测试奇数长度链表的中点查找
        System.out.println("Odd length list middle:");
        ListNode testList1 = finder.createOddLengthList();
        ListNode middle = finder.findMiddleWithFastAtHead(testList1);
        System.out.println(middle.val);  // 应该输出3
        
        middle = finder.findMiddleWithFastAtHeadNext(finder.createOddLengthList());
        System.out.println(middle.val);  // 也应该输出3
        
        // 测试偶数长度链表的中点查找
        System.out.println("\nEven length list middle:");
        ListNode testList2 = finder.createEvenLengthList();
        middle = finder.findMiddleWithFastAtHead(testList2);
        System.out.println(middle.val);  // 应该输出3
        
        middle = finder.findMiddleWithFastAtHeadNext(finder.createEvenLengthList());
        System.out.println(middle.val);  // 也应该输出3
    }
}