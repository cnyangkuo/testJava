package com.interview.lt;

// 添加LT148排序链表问题解决方案

public class LT148SortList {
    // 题目：排序链表（Sort List）
    // 难度：中等
    // 描述：在O(n log n)时间复杂度内对链表进行排序，并返回排序后的链表。
    // 解法：归并排序，使用快慢指针找中点，时间复杂度O(n log n)，空间复杂度O(log n)
    
    public ListNode sortList1(ListNode head) {
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
        ListNode mid = prev.next;
        prev.next = null;
        
        // 递归排序左右两部分
        ListNode left = sortList1(head);
        ListNode right = sortList1(mid);
        
        // 合并两个有序链表
        return merge(left, right);
    }
    
    /**
     * 归并排序变体2：快指针初始化为head.next实现
     * @param head 链表头节点
     * @return 排序后的链表
     * 时间复杂度：O(n log n) - 最坏、平均情况
     * 空间复杂度：O(log n) - 递归栈深度
     * 关键改进：使用 fast = head.next 的初始化方式
     */
    public ListNode sortList(ListNode head) {
        // 终止条件：空节点或单节点
        if (head == null || head.next == null) {
            return head;
        }

        // 快慢指针找中点（快指针提前走一步）
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 分割链表
        ListNode mid = slow.next;
        slow.next = null;

        /**
         ListNode slow = head, fast = head;
         ListNode prev = null;
         while (fast != null && fast.next != null) {
             prev = slow;
             slow = slow.next;
             fast = fast.next.next;
         }

         // 断开链表，分成两部分
         ListNode mid = prev.next;
         prev.next = null;
         */

        // 递归排序左右子链表
        ListNode left = sortList(head);
        ListNode right = sortList(mid);

        // 合并有序链表
        return merge(left, right);
    }

    /**
     * 合并两个有序链表（无哨兵节点版）
     * @param l1 第一个有序链表
     * @param l2 第二个有序链表
     * @return 合并后的有序链表
     */
    private ListNode merge(ListNode<Integer> l1, ListNode<Integer> l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        // 交叉连接节点
        while (l1 != null && l2 != null) {
            // 修复：使用Integer类型确保数值比较有效性
            if (l1.val < l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }

        // 连接剩余节点
        tail.next = (l1 != null) ? l1 : l2;
        
        return dummy.next;
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