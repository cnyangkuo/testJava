package com.interview.lt;

/**
 * LeetCode 106: Construct Binary Tree from Inorder and Postorder Traversal
 * 题目描述: 根据一棵树的中序遍历和后序遍历构造二叉树。
 * 解题思路: 使用递归方法，以后序遍历的最后一个元素作为根节点，然后在中序遍历中找到该根节点，将数组分为左右子树部分。
 */
public class LT106ConstructBinaryTreeFromInorderAndPostorderTraversal {
    
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
     * 根据中序遍历和后序遍历构造二叉树
     * @param inorder 中序遍历结果数组
     * @param postorder 后序遍历结果数组
     * @return 构造好的二叉树的根节点
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null || inorder.length != postorder.length) {
            return null;
        }
        
        // 调用递归构建函数
        return buildTreeHelper(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }
    
    /**
     * 辅助递归函数用于构建二叉树
     * @param inorder 中序遍历结果数组
     * @param inStart 当前子树在中序遍历中的起始索引
     * @param inEnd 当前子树在中序遍历中的结束索引
     * @param postorder 后序遍历结果数组
     * @param postStart 当前子树在后序遍历中的起始索引
     * @param postEnd 当前子树在后序遍历中的结束索引
     * @return 构造好的二叉树的根节点
     */
    private TreeNode buildTreeHelper(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }
        
        // 创建根节点
        TreeNode root = new TreeNode(postorder[postEnd]);
        
        // 在中序遍历中找到根节点的位置
        int rootIndex = inStart;
        while (inorder[rootIndex] != root.val) {
            rootIndex++;
        }
        
        // 计算左子树的大小
        int leftSubtreeSize = rootIndex - inStart;
        
        // 递归构建左子树
        root.left = buildTreeHelper(inorder, inStart, rootIndex - 1, postorder, postStart, postStart + leftSubtreeSize - 1);
        // 递归构建右子树
        root.right = buildTreeHelper(inorder, rootIndex + 1, inEnd, postorder, postStart + leftSubtreeSize, postEnd - 1);
        
        return root;
    }
    
    public static void main(String[] args) {
        // 测试用例
        int[] inorder = {9,3,15,20,7};
        int[] postorder = {9,15,7,20,3};
        
        LT106ConstructBinaryTreeFromInorderAndPostorderTraversal solution = new LT106ConstructBinaryTreeFromInorderAndPostorderTraversal();
        TreeNode root = solution.buildTree(inorder, postorder);
        
        // 打印构建好的二叉树进行验证
        System.out.println("Binary tree constructed successfully.");
    }
}