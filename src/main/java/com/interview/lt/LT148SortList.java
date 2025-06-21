package com.interview.lt;

// 添加LT148排序链表问题解决方案

public class LT148SortList {
    // 题目：排序链表（Sort List）
    // 难度：中等
    // 描述：在O(n log n)时间复杂度内对链表进行排序，并返回排序后的链表。
    // 解法：归并排序，使用快慢指针找中点，时间复杂度O(n log n)，空间复杂度O(log n)
    
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // 使用快慢指针找到链表中点, 快慢指针, 偶数情况下中点定义为偏左
        ListNode slow = head, fast = head;
        ListNode prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // 断开链表，分成两部分
        prev.next = null;
        
        // 递归排序左右两部分
        ListNode left = sortList(head);
        ListNode right = sortList(slow);
        
        // 合并两个有序链表
        return merge(left, right);
    }
    
    // 合并两个有序链表
    private ListNode merge(ListNode<Integer> l1, ListNode<Integer> l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode curr = dummyHead;
        
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        
        // 连接剩余部分
        curr.next = (l1 != null) ? l1 : l2;
        
        return dummyHead.next;
    }
    
    public static void main(String[] args) {
        LT148SortList solution = new LT148SortList();
        
        // 创建测试链表: 4->2->1->3->5
        ListNode n1 = new ListNode(4);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(1);
        ListNode n4 = new ListNode(3);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        
        ListNode sorted = solution.sortList(n1);
        
        // 打印排序后的链表
        ListNode p = sorted;
        System.out.print("Test case 1: ");
        while (p != null) {
            System.out.print(p.val + " -> ");
            p = p.next;
        }
        System.out.println("null"); // 应该输出1 -> 2 -> 3 -> 4 -> 5 -> null
        
        // 创建测试链表: 3->1->2->4
        ListNode a1 = new ListNode(3);
        ListNode a2 = new ListNode(1);
        ListNode a3 = new ListNode(2);
        ListNode a4 = new ListNode(4);
        a1.next = a2;
        a2.next = a3;
        a3.next = a4;
        
        ListNode sorted2 = solution.sortList(a1);
        
        // 打印排序后的链表
        ListNode q = sorted2;
        System.out.print("Test case 2: ");
        while (q != null) {
            System.out.print(q.val + " -> ");
            q = q.next;
        }
        System.out.println("null"); // 应该输出1 -> 2 -> 3 -> 4 -> null
    }
}