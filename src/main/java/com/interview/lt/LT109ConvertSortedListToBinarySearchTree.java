package com.interview.lt;

/**
 * LeetCode 109: Convert Sorted List to Binary Search Tree
 * 题目描述: 将一个按升序排列的链表转换成一个高度平衡的二叉搜索树。
 * 解法思路: 使用快慢指针找到链表中点，递归构建左右子树。
 */
public class LT109ConvertSortedListToBinarySearchTree {
    
    /**
     * 定义ListNode类表示单向链表节点
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    
    /**
     * 定义TreeNode类表示二叉树节点
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    
    /**
     * 将有序链表转换为高度平衡的二叉搜索树
     * @param head 链表头节点
     * @return 二叉搜索树的根节点
     */
    public TreeNode sortedListToBST(ListNode head) {
        // 调用递归方法构建二叉搜索树
        return buildBST(head, null);
    }
    
    /**
     * 递归构建二叉搜索树
     * @param head 子链表头节点
     * @param tail 子链表尾后节点（不包含）
     * @return 构建好的子树
     */
    private TreeNode buildBST(ListNode head, ListNode tail) {
        if (head == tail) {
            return null;
        }
        
        // 使用快慢指针找到链表中间节点
        /**
         * 快指针的初始化，这二种情况都可行
         * fast = head（偏左）	    偶数长度时中点定义需明确
         * fast = head.next（偏右）
         */
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // 创建根节点
        TreeNode root = new TreeNode(slow.val);
        // 递归构建左右子树
        root.left = buildBST(head, slow);
        root.right = buildBST(slow.next, tail);
        
        return root;
    }
    
    public static void main(String[] args) {
        // 测试用例
        ListNode head = new ListNode(-10);
        head.next = new ListNode(-3);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(5);
        head.next.next.next.next = new ListNode(9);
        
        LT109ConvertSortedListToBinarySearchTree solution = new LT109ConvertSortedListToBinarySearchTree();
        TreeNode root = solution.sortedListToBST(head);
        
        // 打印构建好的二叉搜索树进行验证（可以添加遍历方法验证结构）
        System.out.println("Height balanced binary search tree constructed successfully.");
    }
}