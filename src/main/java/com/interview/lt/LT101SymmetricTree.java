package com.interview.lt;

/**
 * 101. 对称二叉树
 * 给定一个二叉树的根节点，判断它是否是对称的。
 * 难度: 简单
 * 标签: 树, 广度优先搜索, 深度优先搜索
 */
public class LT101SymmetricTree {
    // 定义二叉树节点结构
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int x) { 
            val = x; 
        }
    }

    // 使用递归方法判断二叉树是否对称
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true; // 空树是对称的
        }
        
        return isMirror(root.left, root.right);
    }

    // 辅助方法判断两棵树是否镜像对称
    private boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true; // 两个空树是对称的
        }
        if (left == null || right == null) {
            return false; // 其中一个是空，则不对称
        }
        
        // 判断当前节点值是否相同，并且左右子树互为镜像
        return (left.val == right.val) 
               && isMirror(left.left, right.right)
               && isMirror(left.right, right.left);
    }

    // 测试用例
    public static void main(String[] args) {
        LT101SymmetricTree solution = new LT101SymmetricTree();
        
        // 创建测试案例1：对称树
        //       1
        //      / \
        //     2   2
        //    / \ / \
        //   3  4 4  3
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        root1.right.left = new TreeNode(4);
        root1.right.right = new TreeNode(3);
        
        System.out.println("测试案例1结果: " + solution.isSymmetric(root1)); // 应该输出true
        
        // 创建测试案例2：非对称树
        //       1
        //      / \
        //     2   2
        //      \   \
        //       3   3
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(2);
        root2.left.right = new TreeNode(3);
        root2.right.right = new TreeNode(3);
        
        System.out.println("测试案例2结果: " + solution.isSymmetric(root2)); // 应该输出false
        
        // 创建测试案例3：只有一个节点的树
        TreeNode root3 = new TreeNode(1);
        System.out.println("测试案例3结果: " + solution.isSymmetric(root3)); // 应该输出true
    }
}