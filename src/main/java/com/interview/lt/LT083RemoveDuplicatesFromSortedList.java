package com.interview.lt;

/**
 * 83. 删除排序链表中的重复元素
 * 给定一个已排序的链表的头 head ，删除所有重复的元素，只留下原始链表中 没有重复的元素。
 * 难度: 简单
 * 标签: 链表
 */
public class LT083RemoveDuplicatesFromSortedList {
    // 定义链表节点结构
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        
        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                // 跳过重复节点
                current.next = current.next.next;
            } else {
                // 移动到下一个不同值的节点
                current = current.next;
            }
        }
        
        return head;
    }
    
    public static void main(String[] args) {
        LT083RemoveDuplicatesFromSortedList solution = new LT083RemoveDuplicatesFromSortedList();
        
        // 创建测试用例1: 1->1->1->2->3
        ListNode head1 = solution.new ListNode(1);
        head1.next = solution.new ListNode(1);
        head1.next.next = solution.new ListNode(1);
        head1.next.next.next = solution.new ListNode(2);
        head1.next.next.next.next = solution.new ListNode(3);
        
        ListNode result1 = solution.deleteDuplicates(head1);
        System.out.print("Test 1 Result: ");
        while (result1 != null) {
            System.out.print(result1.val);
            if (result1.next != null) System.out.print(" -> ");
            result1 = result1.next;
        }
        System.out.println(" 期望输出: 1 -> 2 -> 3");
        
        // 创建测试用例2: 空链表
        ListNode result2 = solution.deleteDuplicates(null);
        System.out.print("Test 2 Result: ");
        System.out.println((result2 == null) ? "null" : result2.val);
        System.out.println(" 期望输出: null");
    }
}