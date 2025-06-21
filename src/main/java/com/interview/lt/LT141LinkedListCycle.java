package com.interview.lt;

// 添加LT141环形链表问题解决方案

public class LT141LinkedListCycle {
    // 题目：环形链表（Linked List Cycle）
    // 难度：简单
    // 描述：判断链表中是否有环。
    // 解法：快慢指针法，时间复杂度O(n)，空间复杂度O(1)
    
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (fast != null && fast.next != null) {
            if (slow == fast) {
                return true; // 快慢指针相遇，说明有环
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return false; // 遍历完成未相遇，无环
    }
    
    public static void main(String[] args) {
        LT141LinkedListCycle solution = new LT141LinkedListCycle();
        
        // 创建有环的链表
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(-4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n2; // 环
        
        System.out.println("Test case 1: " + solution.hasCycle(n1)); // 应该输出true
        
        // 创建无环的链表
        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(2);
        a1.next = a2;
        
        System.out.println("Test case 2: " + solution.hasCycle(a1)); // 应该输出false
    }
}