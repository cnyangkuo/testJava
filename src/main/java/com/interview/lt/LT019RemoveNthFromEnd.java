package com.interview.lt;

/**
 * 19. 删除链表的倒数第 N 个结点
 * 给你一个链表，删除链表中的倒数第 n 个结点，并且返回链表的头结点。
 * 难度: 中等
 * 标签: 链表, 双指针
 */
public class LT019RemoveNthFromEnd {
    // 定义链表节点结构
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 创建虚拟头节点，简化边界情况处理
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        // 让fast指针先走n步
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        
        // 同时移动fast和slow指针
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // 删除倒数第n个节点
        slow.next = slow.next.next;
        
        return dummy.next;
    }
    
    public static void main(String[] args) {
        LT019RemoveNthFromEnd solution = new LT019RemoveNthFromEnd();
        
        // 创建测试用例: 1->2->3->4->5
        ListNode head = solution.new ListNode(1);
        head.next = solution.new ListNode(2);
        head.next.next = solution.new ListNode(3);
        head.next.next.next = solution.new ListNode(4);
        head.next.next.next.next = solution.new ListNode(5);
        
        // 删除倒数第2个节点 (即4)
        ListNode result = solution.removeNthFromEnd(head, 2);
        
        System.out.println("删除倒数第2个节点后的链表:");
        while (result != null) {
            System.out.print(result.val);
            if (result.next != null) System.out.print(" -> ");
            result = result.next;
        }
        // 期望输出: 1 -> 2 -> 3 -> 5
    }
}