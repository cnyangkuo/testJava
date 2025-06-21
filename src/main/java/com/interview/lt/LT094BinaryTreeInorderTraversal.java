package com.interview.lt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 94. 二叉树的中序遍历
 * 给定一个二叉树的根节点，返回它的中序遍历结果。
 * 难度: 简单
 * 标签: 栈, 树, 深度优先搜索
 */
public class LT094BinaryTreeInorderTraversal {
    // 定义二叉树节点结构
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 使用迭代法实现二叉树的中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // 一直向左走，将所有左节点压入栈
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // 弹出栈顶元素并访问
            current = stack.pop();
            result.add(current.val);
            
            // 转向右子树
            current = current.right;
        }
        
        return result;
    }

//    // 使用递归实现二叉树的中序遍历
//    public List<Integer> inorderTraversal(TreeNode root) {
//        List<Integer> result = new ArrayList<>();
//        if (root == null) {
//            return result;
//        }
//
//        inorderTraversalHelper(root, result);
//        return result;
//    }
//
//    private void inorderTraversalHelper(TreeNode node, List<Integer> result) {
//        if (node == null) {
//            return;
//        }
//
//        inorderTraversalHelper(node.left, result);
//        result.add(node.val);
//        inorderTraversalHelper(node.right, result);
//    }

    // 测试用例
    public static void main(String[] args) {
        LT094BinaryTreeInorderTraversal solution = new LT094BinaryTreeInorderTraversal();
        
        // 创建测试用例: 1->null->2->3
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        
        System.out.println("测试案例1结果: " + solution.inorderTraversal(root)); // 应该输出[1,3,2]
        
        // 创建空二叉树
        TreeNode nullRoot = null;
        System.out.println("测试案例2结果: " + solution.inorderTraversal(nullRoot)); // 应该输出空列表[]
        
        // 创建更复杂的二叉树:
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode complexRoot = new TreeNode(1);
        complexRoot.left = new TreeNode(2);
        complexRoot.right = new TreeNode(3);
        complexRoot.left.left = new TreeNode(4);
        complexRoot.left.right = new TreeNode(5);
        
        System.out.println("测试案例3结果: " + solution.inorderTraversal(complexRoot)); // 应该输出[4,2,5,1,3]
    }
}