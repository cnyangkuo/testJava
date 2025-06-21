package com.interview.lt;

/**
 * LeetCode 112: Path Sum
 * 题目描述: 判断二叉树中是否存在从根节点到叶子节点的路径，使得路径上的节点值之和等于目标值。
 * 解法思路: 使用递归方法遍历所有从根节点到叶子节点的路径，并检查路径和是否等于目标值。
 */
public class LT112PathSum {
    
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
     * 判断是否存在从根节点到叶子节点的路径，使得路径和等于目标值
     * @param root 根节点
     * @param sum 目标路径和
     * @return 是否存在满足条件的路径
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        
        // 如果是叶子节点，检查路径和是否匹配
        if (root.left == null && root.right == null) {
            return root.val == sum;
        }
        
        // 递归检查左子树或右子树是否存在满足条件的路径
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }
    
    public static void main(String[] args) {
        // 测试用例
        LT112PathSum solution = new LT112PathSum();
        
        // 创建测试二叉树
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.right.right = new TreeNode(1);
        
        // 测试不同的路径和
        System.out.println("Testing path sum:");
        System.out.println("Has path sum 22: " + solution.hasPathSum(root, 22)); // 应该返回true
        System.out.println("Has path sum 27: " + solution.hasPathSum(root, 27)); // 应该返回false
        System.out.println("Has path sum 26: " + solution.hasPathSum(root, 26)); // 应该返回true
    }
}