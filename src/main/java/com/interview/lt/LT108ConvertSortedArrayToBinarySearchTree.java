package com.interview.lt;

/**
 * LeetCode 108: Convert Sorted Array to Binary Search Tree
 * 题目描述: 将一个按升序排列的数组转换成一个高度平衡的二叉搜索树。
 * 解法思路: 使用递归方法，选择数组中间元素作为根节点，递归构建左右子树。
 */
public class LT108ConvertSortedArrayToBinarySearchTree {
    
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
     * 将有序数组转换为高度平衡的二叉搜索树
     * @param nums 升序排列的数组
     * @return 二叉搜索树的根节点
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        
        // 调用递归方法构建二叉搜索树
        return buildBST(nums, 0, nums.length - 1);
    }
    
    /**
     * 递归构建二叉搜索树
     * @param nums 有序数组
     * @param left 左边界
     * @param right 右边界
     * @return 构建好的子树
     */
    private TreeNode buildBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        
        // 选择中间元素作为根节点
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        
        // 递归构建左右子树
        root.left = buildBST(nums, left, mid - 1);
        root.right = buildBST(nums, mid + 1, right);
        
        return root;
    }
    
    public static void main(String[] args) {
        // 测试用例
        int[] nums = {-10, -3, 0, 5, 9};
        
        LT108ConvertSortedArrayToBinarySearchTree solution = new LT108ConvertSortedArrayToBinarySearchTree();
        TreeNode root = solution.sortedArrayToBST(nums);
        
        // 打印构建好的二叉搜索树进行验证（这里可以添加中序遍历等方法验证结构）
        System.out.println("Height balanced binary search tree constructed successfully.");
    }
}