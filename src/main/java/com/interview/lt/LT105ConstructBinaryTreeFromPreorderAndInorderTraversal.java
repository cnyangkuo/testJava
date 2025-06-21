package com.interview.lt;

/**
 * LeetCode 105: Construct Binary Tree from Preorder and Inorder Traversal
 * 题目描述: 根据一棵树的前序遍历与中序遍历构造二叉树。
 * 解题思路: 使用递归方法，以前序遍历的第一个元素作为根节点，然后在中序遍历中找到该根节点，将数组分为左右子树部分。
 */
public class LT105ConstructBinaryTreeFromPreorderAndInorderTraversal {
    
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
     * 根据前序遍历和中序遍历构造二叉树
     * @param preorder 前序遍历结果数组
     * @param inorder 中序遍历结果数组
     * @return 构造好的二叉树的根节点
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        
        // 调用递归构建函数
        return buildTreeHelper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }
    
    /**
     * 辅助递归函数用于构建二叉树
     * @param preorder 前序遍历结果数组
     * @param preStart 当前子树在前序遍历中的起始索引
     * @param preEnd 当前子树在前序遍历中的结束索引
     * @param inorder 中序遍历结果数组
     * @param inStart 当前子树在中序遍历中的起始索引
     * @param inEnd 当前子树在中序遍历中的结束索引
     * @return 构造好的二叉树的根节点
     */
    private TreeNode buildTreeHelper(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        
        // 创建根节点
        TreeNode root = new TreeNode(preorder[preStart]);
        
        // 在中序遍历中找到根节点的位置
        int rootIndex = inStart;
        while (inorder[rootIndex] != root.val) {
            rootIndex++;
        }
        
        // 计算左子树的大小
        int leftSubtreeSize = rootIndex - inStart;
        
        // 递归构建左子树
        root.left = buildTreeHelper(preorder, preStart + 1, preStart + leftSubtreeSize, inorder, inStart, rootIndex - 1);
        // 递归构建右子树
        root.right = buildTreeHelper(preorder, preStart + leftSubtreeSize + 1, preEnd, inorder, rootIndex + 1, inEnd);
        
        return root;
    }
    
    public static void main(String[] args) {
        // 测试用例
        int[] preorder = {3,9,20,15,7};
        int[] inorder = {9,3,15,20,7};
        
        LT105ConstructBinaryTreeFromPreorderAndInorderTraversal solution = new LT105ConstructBinaryTreeFromPreorderAndInorderTraversal();
        TreeNode root = solution.buildTree(preorder, inorder);
        
        // 打印构建好的二叉树进行验证
        System.out.println("Binary tree constructed successfully.");
    }
}