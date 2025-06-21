package com.interview.lt;

/**
 * 160. 相交链表
 * 给你两个单链表的头节点 headA 和 headB，请你找出并返回两个单链表相交的起始节点。
 * 难度: 简单
 * 标签: 链表
 */
public class LT160IntersectionOfTwoLinkedLists {
    // 定义链表节点结构
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    // 使用双指针法解决相交链表问题
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pointerA = headA;
        ListNode pointerB = headB;

        // 当两个指针不相遇时继续遍历
        while (pointerA != pointerB) {
            // 如果指针A到达末尾，则跳转到headB继续遍历
            pointerA = pointerA == null ? headB : pointerA.next;
            // 如果指针B到达末尾，则跳转到headA继续遍历
            pointerB = pointerB == null ? headA : pointerB.next;
        }

        // 返回相交节点
        return pointerA;
    }

    // 测试用例
    public static void main(String[] args) {
        // 创建第一个链表: 4 -> 1 -> 8 -> 4 -> 5
        ListNode headA = new ListNode(4);
        headA.next = new ListNode(1);
        headA.next.next = new ListNode(8);
        headA.next.next.next = new ListNode(4);
        headA.next.next.next.next = new ListNode(5);

        // 创建第二个链表: 5 -> 6 -> 1 -> 8 -> 4 -> 5
        ListNode headB = new ListNode(5);
        headB.next = new ListNode(6);
        headB.next.next = new ListNode(1);
        headB.next.next.next = headA.next.next; // 在节点8处相交

        LT160IntersectionOfTwoLinkedLists solution = new LT160IntersectionOfTwoLinkedLists();
        ListNode result = solution.getIntersectionNode(headA, headB);
        System.out.println("相交节点值: " + result.val); // 应该输出8
    }
}