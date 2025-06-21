package com.interview.lt;

/**
 * 99. 恢复二叉搜索树
 * 给定一个二叉搜索树（BST），其中两个节点被错误地交换，请在不改变其结构的情况下恢复此BST。
 * 难度: 困难
 * 标签: 树, 深度优先搜索
 */
public class LT099RecoverBinarySearchTree {
    // 定义二叉树节点结构
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 用于记录需要交换的两个节点
    private TreeNode first = null;
    private TreeNode second = null;
    // 记录前一个节点，用于比较
    private TreeNode prev = new TreeNode(Integer.MIN_VALUE);

    // 使用中序遍历查找错误交换的节点
    public void recoverTree(TreeNode root) {
        inOrderTraversal(root);
        
        // 交换两个错误节点的值
        if (first != null && second != null) {
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }
    }

    // 中序遍历BST
    private void inOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        
        inOrderTraversal(node.left);
        
        // 如果发现递增顺序被打乱，则记录异常节点
        if (first == null && prev.val >= node.val) {
            first = prev;
        }
        if (first != null && prev.val >= node.val) {
            second = node;
        }
        
        prev = node; // 更新前一个节点
        
        inOrderTraversal(node.right);
    }

    // 测试用例
    public static void main(String[] args) {
        LT099RecoverBinarySearchTree solution = new LT099RecoverBinarySearchTree();
        
        // 创建测试用例：错误的BST
        //       1
        //      /
        //     3
        //      \
        //       2
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        
        System.out.println("原始错误BST:");
        solution.printInOrder(root); // 应该输出: 1 3 2
        
        // 恢复BST
        solution.recoverTree(root);
        
        System.out.println("\n\n恢复后的BST:");
        solution.printInOrder(root); // 应该输出: 1 2 3
    }
    
    // 打印中序遍历结果
    private void printInOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        
        printInOrder(node.left);
        System.out.print(node.val + " ");
        printInOrder(node.right);
    }
}