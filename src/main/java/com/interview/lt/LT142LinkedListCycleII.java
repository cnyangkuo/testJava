// 添加LT142环形链表II问题解决方案
// 定义链表节点类
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class LT142LinkedListCycleII {
    // 题目：环形链表II（Linked List Cycle II）
    // 难度：中等
    // 描述：给定一个链表，返回链表开始入环的第一个节点。如果链表无环，则返回null。
    // 解法：快慢指针法，时间复杂度O(n)，空间复杂度O(1)
    
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // 第一次相遇
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break; // 相遇点
        }
        
        // 检查是否无环
        if (fast == null || fast.next == null) return null;
        
        // 找到环的入口
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow; // 返回环的入口节点
    }
    
    public static void main(String[] args) {
        LT142LinkedListCycleII solution = new LT142LinkedListCycleII();
        
        // 创建有环的链表
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(-4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n2; // 环
        
        ListNode cycleNode = solution.detectCycle(n1);
        System.out.println("Test case 1: " + (cycleNode != null ? cycleNode.val : "no cycle")); // 应该输出2
        
        // 创建无环的链表
        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(2);
        a1.next = a2;
        
        ListNode cycleNode2 = solution.detectCycle(a1);
        System.out.println("Test case 2: " + (cycleNode2 != null ? cycleNode2.val : "no cycle")); // 应该输出no cycle
    }
}