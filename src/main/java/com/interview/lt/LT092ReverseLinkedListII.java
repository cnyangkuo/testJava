package com.interview.lt;

/**
 * 92. 反转链表 II 
 * 给定一个单链表和两个位置 m 和 n，其中 1 ≤ m ≤ n ≤ 链表长度，反转链表中从位置 m 到 n 的节点，并返回反转后的链表。
 * 难度: 中等
 * 标签: 链表
 */
public class LT092ReverseLinkedListII {
    // 定义链表节点结构
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    // 使用直接反转法解决反转链表 II 问题
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || m == n) {
            return head;
        }
        
        // 创建虚拟头节点，处理m=1的情况
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        // 移动到第m个节点的前一个节点
        for (int i = 0; i < m - 1; i++) {
            prev = prev.next;
        }
        
        // 开始反转从m到n的节点
        ListNode current = prev.next;
        for (int i = 0; i < n - m; i++) {
            ListNode temp = prev.next;
            prev.next = current.next;
            current.next = current.next.next;
            prev.next.next = temp;
        }
        
        return dummy.next;
    }

    // 测试用例
    public static void main(String[] args) {
        LT092ReverseLinkedListII solution = new LT092ReverseLinkedListII();
        
        // 创建测试用例: 1->2->3->4->5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        
        System.out.println("原始链表:");
        printList(head);
        
        // 反转链表从位置2到4的部分
        ListNode result = solution.reverseBetween(head, 2, 4);
        
        System.out.println("\n反转后链表:");
        printList(result);
        // 应该输出1->4->3->2->5
    }
    
    // 打印链表
    private static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
    }
}