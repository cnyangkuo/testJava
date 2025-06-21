package com.interview.lt;

// 添加LT143重排链表问题解决方案
// 定义链表节点类

public class LT143ReorderList {
    // 题目：重排链表（Reorder List）
    // 难度：中等
    // 描述：给定一个单链表L0→L1→…→Ln-1→Ln，将其重排为L0→Ln→L1→Ln-1→L2→Ln-2→…的形式。
    // 解法：快慢指针找中点+反转后半部分+合并两个链表
    
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;
        
        // 1. 使用快慢指针找到链表中点
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // 2. 反转后半部分链表
        ListNode prev = null, curr = slow.next;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        slow.next = null; // 断开前半部分和后半部分
        
        // 3. 合并两个链表
        ListNode first = head;
        ListNode second = prev;
        while (second != null) {
            ListNode temp1 = first.next;
            ListNode temp2 = second.next;
            
            first.next = second;
            second.next = temp1;
            
            first = temp1;
            second = temp2;
        }
    }
    
    public static void main(String[] args) {
        LT143ReorderList solution = new LT143ReorderList();
        
        // 创建测试链表: 1->2->3->4->5
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        
        solution.reorderList(n1);
        
        // 打印重排后的链表
        ListNode p = n1;
        System.out.print("Test case 1: ");
        while (p != null) {
            System.out.print(p.val + " -> ");
            p = p.next;
        }
        System.out.println("null"); // 应该输出1->5->2->4->3->null
        
        // 创建测试链表: 1->2->3->4
        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(2);
        ListNode a3 = new ListNode(3);
        ListNode a4 = new ListNode(4);
        a1.next = a2;
        a2.next = a3;
        a3.next = a4;
        
        solution.reorderList(a1);
        
        // 打印重排后的链表
        ListNode q = a1;
        System.out.print("Test case 2: ");
        while (q != null) {
            System.out.print(q.val + " -> ");
            q = q.next;
        }
        System.out.println("null"); // 应该输出1->4->2->3->null
    }
}