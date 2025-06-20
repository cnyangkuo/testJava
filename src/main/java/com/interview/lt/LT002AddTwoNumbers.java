package com.interview.lt;

/**
 * 2. 两数相加
 * 给你两个 非空 的链表，表示两个非负的整数。其中，它们各自的位数是通过逆序存储的，并且每个节点只能存储 一位 数字。
 * 难度: 中等
 * 标签: 链表, 数学
 */
public class LT002AddTwoNumbers {
    // 定义链表节点结构
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode curr = dummyHead;
        int carry = 0;
        
        while (l1 != null || l2 != null || carry != 0) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        return dummyHead.next;
    }
    
    public static void main(String[] args) {
        LT002AddTwoNumbers solution = new LT002AddTwoNumbers();
        
        // 创建测试用例1: 2->4->3 和 5->6->4
        ListNode l1_1 = solution.new ListNode(2);
        l1_1.next = solution.new ListNode(4);
        l1_1.next.next = solution.new ListNode(3);
        
        ListNode l2_1 = solution.new ListNode(5);
        l2_1.next = solution.new ListNode(6);
        l2_1.next.next = solution.new ListNode(4);
        
        ListNode result1 = solution.addTwoNumbers(l1_1, l2_1);
        System.out.print("Test 1 Result: ");
        while (result1 != null) {
            System.out.print(result1.val);
            if (result1.next != null) System.out.print(" -> ");
            result1 = result1.next;
        }
        System.out.println(" 期望输出: 7 -> 0 -> 8");
        
        // 创建测试用例2: 0 和 0
        ListNode l3 = solution.new ListNode(0);
        ListNode l4 = solution.new ListNode(0);
        
        ListNode result2 = solution.addTwoNumbers(l3, l4);
        System.out.print("Test 2 Result: ");
        while (result2 != null) {
            System.out.print(result2.val);
            if (result2.next != null) System.out.print(" -> ");
            result2 = result2.next;
        }
        System.out.println(" 期望输出: 0");
    }
}