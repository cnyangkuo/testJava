package com.interview.lt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 102. 二叉树的层序遍历
 * 给定一个二叉树的根节点，返回该树按层序遍历的结果。
 * 难度: 中等
 * 标签: 树, 广度优先搜索
 */
public class LT102BinaryTreeLevelOrderTraversal {
    // 定义二叉树节点结构
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int x) { 
            val = x; 
        }
    }

    // 使用广度优先搜索实现层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result; // 空树返回空列表
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root); // 将根节点加入队列
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // 当前层的节点数
            List<Integer> currentLevel = new ArrayList<>(); // 存储当前层的值
            
            // 处理当前层的所有节点
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll(); // 取出队列中的第一个节点
                currentLevel.add(node.val); // 将当前节点值加入当前层结果
                
                // 将当前节点的左右子节点加入队列
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            result.add(currentLevel); // 将当前层结果加入最终结果
        }
        
        return result;
    }

    // 测试用例
    public static void main(String[] args) {
        LT102BinaryTreeLevelOrderTraversal solution = new LT102BinaryTreeLevelOrderTraversal();
        
        // 创建测试案例1：普通二叉树
        //       3
        //      / \
        //     9  20
        //        / \
        //       15 7
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        
        System.out.println("测试案例1结果:");
        List<List<Integer>> result1 = solution.levelOrder(root1);
        for (List<Integer> level : result1) {
            System.out.println(level);
        }
        // 应该输出:
        // [3]
        // [9, 20]
        // [15, 7]
        
        // 创建测试案例2：只有一个节点的树
        TreeNode root2 = new TreeNode(1);
        System.out.println("\n测试案例2结果:");
        List<List<Integer>> result2 = solution.levelOrder(root2);
        for (List<Integer> level : result2) {
            System.out.println(level);
        }
        // 应该输出:
        // [1]
        
        // 创建测试案例3：空树
        TreeNode root3 = null;
        System.out.println("\n测试案例3结果:");
        List<List<Integer>> result3 = solution.levelOrder(root3);
        System.out.println(result3);
        // 应该输出: []
    }
}