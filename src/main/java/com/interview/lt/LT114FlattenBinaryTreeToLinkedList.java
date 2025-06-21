package com.interview.lt;

/**
 * LeetCode 114: Flatten Binary Tree to Linked List
 * 题目描述: 给定一个二叉树，将其原地展开为一个单链表。
 * 解法思路: 使用前序遍历的方式，将当前节点的左子树插入到当前节点和右子树之间。
 */
public class LT114FlattenBinaryTreeToLinkedList {
    
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
     * 将二叉树展开为单链表
     * @param root 根节点
     */
    public void flatten(TreeNode root) {
        while (root != null) {
            // 如果左子树为空，则直接处理右子树
            if (root.left == null) {
                root = root.right;
            } else {
                // 找到左子树的最右节点
                TreeNode pre = root.left;
                while (pre.right != null) {
                    pre = pre.right;
                }
                
                // 将右子树连接到左子树的最右节点
                pre.right = root.right;
                
                // 将左子树插入到当前节点和右子树之间
                root.right = root.left;
                root.left = null;
                
                // 继续处理下一个节点
                root = root.right;
            }
        }
    }
    
    public static void main(String[] args) {
        // 测试用例
        /**
         * 构建测试树
         *            1
         *           / \
         *          2   5
         *         / \   \
         *        3  4    6
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);
        
        LT114FlattenBinaryTreeToLinkedList solution = new LT114FlattenBinaryTreeToLinkedList();
        solution.flatten(root);
        
        // 遍历展开后的链表
        TreeNode current = root;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.right;
        }
        System.out.println("null");
    }
}