package com.interview.lt;

/**
 * 100. 相同的树
 * 给定两个二叉树的根节点，请判断它们是否相同。
 * 难度: 简单
 * 标签: 树, 深度优先搜索
 */
public class LT100SameTree {
    // 定义二叉树节点结构
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int x) { 
            val = x; 
        }
    }

    // 使用递归方法判断两棵树是否相同
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // 如果两个节点都为null，则相同
        if (p == null && q == null) {
            return true;
        }
        // 如果其中一个为null，另一个不为null，则不相同
        if (p == null || q == null) {
            return false;
        }
        // 如果节点值不同，返回false
        if (p.val != q.val) {
            return false;
        }
        
        // 递归比较左右子树
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // 测试用例
    public static void main(String[] args) {
        LT100SameTree solution = new LT100SameTree();
        
        // 创建测试案例1：相同树
        //       1         1
        //      / \       / \
        //     2   3     2   3
        TreeNode p1 = new TreeNode(1);
        p1.left = new TreeNode(2);
        p1.right = new TreeNode(3);
        
        TreeNode q1 = new TreeNode(1);
        q1.left = new TreeNode(2);
        q1.right = new TreeNode(3);
        
        System.out.println("测试案例1结果: " + solution.isSameTree(p1, q1)); // 应该输出true
        
        // 创建测试案例2：不同树
        //       1         1
        //      /           \
        //     2             2
        TreeNode p2 = new TreeNode(1);
        p2.left = new TreeNode(2);
        
        TreeNode q2 = new TreeNode(1);
        q2.right = new TreeNode(2);
        
        System.out.println("测试案例2结果: " + solution.isSameTree(p2, q2)); // 应该输出false
        
        // 创建测试案例3：一个空树
        TreeNode p3 = null;
        TreeNode q3 = new TreeNode(1);
        
        System.out.println("测试案例3结果: " + solution.isSameTree(p3, q3)); // 应该输出false
    }
}