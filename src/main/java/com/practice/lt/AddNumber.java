package com.practice.lt;

import java.util.Arrays;
import java.util.List;

/**
 *
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 * 注：每个链表中的节点数在范围 [1, 100] 内
 * @author yangkuo
 * @date 2025/5/28
 * @description
 */
public class AddNumber {
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = dummyHead;
        int carry = 0;
        while (l1 != null || l2 != null || carry > 0) {
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;

            p.next = new ListNode(sum % 10);
            p = p.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        return dummyHead.next;
    }

    // 将List转换为链表
    private static ListNode buildList(List<Integer> nums) {
        if (nums.isEmpty()) return null;
        ListNode head = new ListNode(nums.get(0));
        ListNode current = head;
        for (int i = 1; i < nums.size(); i++) {
            current.next = new ListNode(nums.get(i));
            current = current.next;
        }
        return head;
    }

    public static void main(String[] args) {
        AddNumber addNumber = new AddNumber();

        // 使用 List 初始化链表，更简洁清晰
        ListNode l1 = buildList(Arrays.asList(2, 4, 3));
        ListNode l2 = buildList(Arrays.asList(5, 6, 4));

        ListNode result = addNumber.addTwoNumbers(l1, l2);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }

}
