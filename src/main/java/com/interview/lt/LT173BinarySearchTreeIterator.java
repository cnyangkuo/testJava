// 添加LT173二叉搜索树迭代器问题解决方案
package com.interview.lt;

import java.util.Stack;

public class LT173BinarySearchTreeIterator {
    // 题目：二叉搜索树迭代器（Binary Search Tree Iterator）
    // 难度：中等
    // 描述：实现一个按升序排列的二叉搜索树迭代器，支持hasNext和next操作。
    // 解法：使用栈模拟中序遍历，时间复杂度O(1)平均，空间复杂度O(h)，其中h是树的高度
    
    private Stack<TreeNode> stack;
    
    public LT173BinarySearchTreeIterator(TreeNode root) {
        stack = new Stack<>();
        pushLeft(root);
    }
    
    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
    
    public boolean hasNext() {
        return !stack.isEmpty();
    }
    
    public int next() {
        TreeNode<Integer> node = stack.pop();
        if (node.right != null) {
            pushLeft(node.right);
        }
        return node.val;
    }
    
    public static void main(String[] args) {
        // 创建测试用的二叉搜索树
        //       7
        //      / \
        //     3   15
        //        / \
        //       9  20
        TreeNode root = new TreeNode(7);
        root.left = new TreeNode(3);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(20);
        
        LT173BinarySearchTreeIterator iterator = new LT173BinarySearchTreeIterator(root);
        System.out.println("Test case 1: " + iterator.next());    // 应该输出3
        System.out.println("Test case 2: " + iterator.hasNext());  // 应该输出true
        System.out.println("Test case 3: " + iterator.next());    // 应该输出7
        System.out.println("Test case 4: " + iterator.hasNext());  // 应该输出true
        System.out.println("Test case 5: " + iterator.next());    // 应该输出9
        System.out.println("Test case 6: " + iterator.hasNext());  // 应该输出true
    }
}