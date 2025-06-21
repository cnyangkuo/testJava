package com.interview.lt;

/**
 * LeetCode 234: Palindrome Linked List
 * 题目描述: 判断一个单向链表是否是回文链表。
 * 解法思路: 
 * 1. 使用快慢指针找到链表中点
 * 2. 反转后半部分链表
 * 3. 比较前半部分和后半部分的值
 * 4. 恢复原链表结构（可选）
 */
public class LT234PalindromeLinkedList {
    
    /**
     * 定义ListNode类表示单向链表节点
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }

        public void printList() {
            ListNode current = this;
            while (current != null) {
                System.out.print(current.val + " ");
                current = current.next;
            }
            System.out.println();
        }
    }
    
    /**
     * 判断链表是否为回文
     * @param head 链表头节点
     * @return 是否为回文链表
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        // 使用快慢指针找到链表中点
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 反转后半部分链表
        ListNode prev = null;
        ListNode current = slow;
        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }

        // 比较前半部分和后半部分的值
        ListNode firstHalf = head;
        ListNode secondHalf = prev;
        boolean isPalindrome = true;
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                isPalindrome = false;
                break;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }

        // 可选：恢复原链表结构
        // 对于偶数个节点的情况，需要重新连接中间节点
        current = prev;
        prev = null;
        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }

        head.printList();
        return isPalindrome;
    }

    private ListNode revertList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }
        return prev;
    }

    public static void main(String[] args) {
        // 测试用例
        LT234PalindromeLinkedList solution = new LT234PalindromeLinkedList();
        
        // 创建测试链表 1->2
        ListNode test1 = new ListNode(1);
        test1.next = new ListNode(2);
        
        // 创建测试链表 1->2->2->1
        ListNode test2 = new ListNode(1);
        test2.next = new ListNode(2);
        test2.next.next = new ListNode(2);
        test2.next.next.next = new ListNode(1);
        
        // 创建测试链表 1->2->3->2->1
        ListNode test3 = new ListNode(1);
        test3.next = new ListNode(2);
        test3.next.next = new ListNode(3);
        test3.next.next.next = new ListNode(2);
        test3.next.next.next.next = new ListNode(1);
        
        // 运行测试用例
        System.out.println("Test case 1 (1->2): ");
        System.out.println(solution.isPalindrome(test1)); // 应该返回false
        
        System.out.println("Test case 2 (1->2->2->1): ");
        System.out.println(solution.isPalindrome(test2)); // 应该返回true
        
        System.out.println("Test case 3 (1->2->3->2->1): ");
        System.out.println(solution.isPalindrome(test3)); // 应该返回true
    }
}