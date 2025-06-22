package com.interview.lt;

// 添加LT144二叉树的前序遍历问题解决方案
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LT144BinaryTreePreorderTraversal {
    // 题目：二叉树的前序遍历（Binary Tree Preorder Traversal）
    // 难度：中等
    // 描述：给定一个二叉树，返回其前序遍历结果。
    // 解法：迭代法，使用栈模拟递归过程
    
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode<Integer> node = stack.pop();
            result.add(node.val);
            
            // 先压入右孩子，再压入左孩子（因为栈是后进先出）
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        
        return result;
    }

    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        preorder(root, result);
        return result;
    }

    private void preorder(TreeNode<Integer> root, List<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.val);
        preorder(root.left, result);
        preorder(root.right, result);
    }

    public static void main(String[] args) {
        LT144BinaryTreePreorderTraversal solution = new LT144BinaryTreePreorderTraversal();
        
        // 创建测试用的二叉树
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        
        List<Integer> result = solution.preorderTraversal(root);
        System.out.println("Test case 1: " + result); // 应该输出[1, 2, 4, 5, 3]
        
        // 创建空二叉树
        TreeNode nullRoot = null;
        List<Integer> nullResult = solution.preorderTraversal(nullRoot);
        System.out.println("Test case 2: " + nullResult); // 应该输出[]
    }
}