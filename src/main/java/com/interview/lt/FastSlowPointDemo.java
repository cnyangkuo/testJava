package com.interview.lt;

/**
 * 快慢指针在链表操作中的典型应用示例
 * 包含中点查找、环检测、环入口定位及归并排序分割
 *
 *
 快慢指针找中点的两种初始化方式对比
 一、核心差异解析
 初始化方式 快指针起点   中间节点位置  偶数节点处理        是否需要prev
 同步走     head        偏向前位    选前半段最后一个    是 prev.next表示后半段第一个
 提前走     head.next   偏向后位    选后半段第一个      否 slow.next表示后半段第一个

 思路：用偶数个节点验证一下同步走、提前走哪个方便就用哪个。

 能推导如下结论：
 * 场景     * 推荐方式      * 说明
 链表分割     提前走         更简洁，无需维护prev
 环检测       提前走         避免初始相遇误判
 插入操作     同步走+prev    精准定位插入位置
 删除中间节点  同步走+prev    需要修改前驱指针
 环的入口     同步走         更简洁fast

 *
 */
public class FastSlowPointDemo {
    // 链表节点定义
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    /**
     * 找到链表中间节点（同步走法）
     * 适用于需要断开链表的场景
     * @param head 链表头节点
     * @return 返回前半段尾节点
     */
    public ListNode findMidNode1(ListNode head) {
        if (head == null) return null;
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            prev = prev.next;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return prev; // 返回前半段尾节点
    }

    /**
     * 找到链表中间节点（提前走法）
     * 适用于直接获取中间节点的场景
     * @param head 链表头节点
     * @return 返回中间节点
     */
    public ListNode findMidNode2(ListNode head) {
        if (head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow; // 返回中间节点
    }

    /**
     * 检测环形链表
     * @param head 链表头节点
     * @return 是否存在环
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (fast != null && fast.next != null) {
            if (slow == fast) return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return false;
    }

    /**
     * 寻找环形链表入口
     * @param head 链表头节点
     * @return 环的入口节点，无环返回null
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;
        
        ListNode slow = head;
        ListNode fast = head.next;
        
        // 第一次相遇
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        
        // 无环情况
        if (slow != fast) return null;
        
        // 寻找入口点
        slow = head;
        fast = fast.next; // 从相遇点前进一位开始
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
    }

    /**
     * 归并排序链表分割示例
     * @param head 链表头节点
     * @return 分割后的右半段头节点
     */
    public ListNode sortListSplit(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode slow = head;
        ListNode fast = head.next;
        
        // 快慢指针找中点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // 分割链表
        ListNode right = slow.next;
        slow.next = null;
        
        return right;
    }

    // 单元测试示例
    public static void main(String[] args) {
        // 测试用例构建
        FastSlowPointDemo demo = new FastSlowPointDemo();
        
        // 创建测试链表 1->2->3->4->5
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        
        // 测试中点查找
        ListNode mid1 = demo.findMidNode1(head1);
        System.out.println("同步走法中点值: " + mid1.next.val); // 应输出3
        
        // 创建测试环形链表
        ListNode head2 = new ListNode(3);
        head2.next = new ListNode(2);
        head2.next.next = new ListNode(0);
        head2.next.next.next = head2.next; // 创建环
        
        // 测试环检测
        System.out.println("环检测结果: " + demo.hasCycle(head2)); // 应输出true
        
        // 测试环入口定位
        ListNode cycleEntry = demo.detectCycle(head2);
        System.out.println("环入口值: " + cycleEntry.val); // 应输出2
    }
}