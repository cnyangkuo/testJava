package com.interview.lt;

/**
 * 21. 合并两个有序链表
 * 将两个升序的非空链表合并为一个新的升序链表。
 * 难度: 简单
 * 标签: 链表
 */
public class LT021MergeTwoSortedLists {
    // 定义链表节点结构
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 创建虚拟头节点，简化边界情况处理
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }
        
        // 连接剩余部分
        tail.next = (l1 != null) ? l1 : l2;
        
        return dummy.next;
    }
    
    public static void main(String[] args) {
        LT021MergeTwoSortedLists solution = new LT021MergeTwoSortedLists();
        
        // 创建测试用例1: 1->2->4 和 1->3->4
        ListNode l1_1 = solution.new ListNode(1);
        l1_1.next = solution.new ListNode(2);
        l1_1.next.next = solution.new ListNode(4);
        
        ListNode l2_1 = solution.new ListNode(1);
        l2_1.next = solution.new ListNode(3);
        l2_1.next.next = solution.new ListNode(4);
        
        ListNode result1 = solution.mergeTwoLists(l1_1, l2_1);
        System.out.print("Test 1 Result: ");
        while (result1 != null) {
            System.out.print(result1.val);
            if (result1.next != null) System.out.print(" -> ");
            result1 = result1.next;
        }
        System.out.println(" 期望输出: 1 -> 1 -> 2 -> 3 -> 4 -> 4");
        
        // 创建测试用例2: 空链表
        ListNode result2 = solution.mergeTwoLists(null, null);
        System.out.print("Test 2 Result: ");
        System.out.println((result2 == null) ? "null" : result2.val);
        System.out.println(" 期望输出: null");
    }
}