package com.interview.lt;

// 添加LT147对链表进行插入排序问题解决方案
// 定义链表节点类

public class LT147InsertionSortList {
    // 题目：对链表进行插入排序（Insertion Sort List）
    // 难度：中等
    // 描述：对一个链表进行插入排序，并返回排序后的链表。
    // 解法：插入排序，使用虚拟头节点简化操作
    
    public ListNode insertionSortList(ListNode<Integer> head) {
        if (head == null || head.next == null) return head;
        
        // 创建虚拟头节点
        ListNode<Integer> dummyHead = new ListNode(0);
        dummyHead.next = head;
        
        ListNode<Integer> lastSorted = head; // 已排序部分的最后一个节点
        ListNode<Integer> curr = head.next;  // 当前需要插入的节点
        
        while (curr != null) {
            if (lastSorted.val <= curr.val) {
                // 如果当前节点值大于等于已排序部分的最后一个值，直接加入已排序部分
                lastSorted = lastSorted.next;
            } else {
                // 从头开始找插入位置
                ListNode<Integer> prev = dummyHead;
                while ((Integer)prev.next.val <= curr.val) {
                    prev = prev.next;
                }
                
                // 将curr插入到prev和prev.next之间
                lastSorted.next = curr.next;
                curr.next = prev.next;
                prev.next = curr;
            }
            
            curr = lastSorted.next; // 更新当前节点
        }
        
        return dummyHead.next;
    }
    
    public static void main(String[] args) {
        LT147InsertionSortList solution = new LT147InsertionSortList();
        
        // 创建测试用的链表: 4->2->1->3
        ListNode n1 = new ListNode(4);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(1);
        ListNode n4 = new ListNode(3);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        
        ListNode sorted = solution.insertionSortList(n1);
        
        // 打印排序后的链表
        ListNode p = sorted;
        System.out.print("Test case 1: ");
        while (p != null) {
            System.out.print(p.val + " -> ");
            p = p.next;
        }
        System.out.println("null"); // 应该输出1->2->3->4->null
        
        // 创建测试用的已排序链表: 1->2->3->4
        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(2);
        ListNode a3 = new ListNode(3);
        ListNode a4 = new ListNode(4);
        a1.next = a2;
        a2.next = a3;
        a3.next = a4;
        
        ListNode sorted2 = solution.insertionSortList(a1);
        
        // 打印排序后的链表
        ListNode q = sorted2;
        System.out.print("Test case 2: ");
        while (q != null) {
            System.out.print(q.val + " ->");
            q = q.next;
        }
        System.out.println("null"); // 应该输出1->2->3->4->null
    }
}