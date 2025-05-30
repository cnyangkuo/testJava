package com.practice.lt;

import java.util.*;

/**
 * 深度优先搜索（DFS）相关面试题集合
 * 包含3个典型的DFS问题
 */
public class DFSProblems {
    // 二叉树节点定义
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    
    /**
     * 问题1：二叉树的最大深度（LeetCode 104）
     * 思路分析：
     * 1. 如果当前节点为空，深度为0
     * 2. 递归计算左右子树的深度
     * 3. 当前节点的最大深度为子树最大深度+1
     * 
     * @param root 二叉树根节点
     * @return 树的最大深度
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }
    
    /**
     * 问题2：路径总和II（LeetCode 113）
     * 思路分析：
     * 1. 使用DFS遍历二叉树
     * 2. 记录从根节点到叶子节点的路径
     * 3. 如果路径和等于目标值则保存该路径
     * 
     * @param root 二叉树根节点
     * @param sum 目标路径和
     * @return 所有满足条件的路径
     */
    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        dfsPathSum(root, sum, new ArrayList<>(), result);
        return result;
    }
    
    private static void dfsPathSum(TreeNode node, int remaining, List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;
        
        path.add(node.val);
        if (node.val == remaining && node.left == null && node.right == null) {
            result.add(new ArrayList<>(path));
        } else {
            dfsPathSum(node.left, remaining - node.val, path, result);
            dfsPathSum(node.right, remaining - node.val, path, result);
        }
        path.remove(path.size() - 1);
    }
    
    /**
     * 问题3：岛屿数量（LeetCode 200）
     * 思路分析：
     * 1. 遍历网格，遇到'1'则进行DFS
     * 2. 将访问过的'1'标记为'0'
     * 3. 统计DFS调用次数即为岛屿数量
     * 
     * @param grid 字符网格
     * @return 岛屿数量
     */
    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    dfsIsland(grid, r, c);
                    count++;
                }
            }
        }
        return count;
    }
    
    private static void dfsIsland(char[][] grid, int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == '0') {
            return;
        }
        grid[row][col] = '0'; // 标记为已访问
        // 对四个方向进行DFS
        dfsIsland(grid, row+1, col);
        dfsIsland(grid, row-1, col);
        dfsIsland(grid, row, col+1);
        dfsIsland(grid, row, col-1);
    }
    
    public static void main(String[] args) {
        // 测试示例1：二叉树最大深度
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        System.out.println("二叉树最大深度: " + maxDepth(root1));  // 输出3
        
        // 测试示例2：路径总和
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(4);
        root2.left.left = new TreeNode(11);
        root2.left.left.left = new TreeNode(7);
        root2.left.left.right = new TreeNode(2);
        root2.right = new TreeNode(8);
        root2.right.left = new TreeNode(13);
        root2.right.right = new TreeNode(4);
        root2.right.right.left = new TreeNode(5);
        root2.right.right.right = new TreeNode(1);
        int sum = 22;
        System.out.println("满足路径和为" + sum + "的路径: " + pathSum(root2, sum));  // 输出[[5,4,11,2], [5,8,4,5]]
        
        // 测试示例3：岛屿数量
        char[][] grid = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println("岛屿数量: " + numIslands(grid));  // 输出3
    }
}