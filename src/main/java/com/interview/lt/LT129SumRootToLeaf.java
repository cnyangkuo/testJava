package com.interview.lt;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 129. 求根节点到叶节点数字之和
 * 给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
 * 每条从根节点到叶节点的路径都代表一个数字。
 * 计算从根节点到叶节点生成的所有数字之和。
 * 难度: 中等
 * 标签: 树, 深度优先搜索
 *
 * 维度         * DFS实现                 * BFS实现
 * 数据结构     * 隐式调用栈               * 显式队列（需维护节点+数值对）
 * 内存消耗     * O(h)（h为树高度）        * O(n)（最坏情况需存储所有节点）
 * 终止时机     * 到达叶子节点自动回溯       * 队列为空时结束循环
 * 数值传递     * 递归参数隐式传递          * 队列元素显式存储
 * 扩展性       * 难中断（递归栈展开不可控） * 可随时break循环
 *
 */
public class LT129SumRootToLeaf {
    // 定义二叉树节点结构
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 使用深度优先搜索计算从根到叶节点的数字之和
    public int sumNumbers(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return dfs(root, 0);
    }

    // 深度优先搜索辅助方法
    private int dfs(TreeNode node, int currentSum) {
        // 计算当前路径的数字
        currentSum = currentSum * 10 + node.val;
        
        // 如果是叶节点，返回当前路径和
        if (node.left == null && node.right == null) {
            return currentSum;
        }
        
        // 递归计算左右子树的路径和
        return dfs(node.left, currentSum) + dfs(node.right, currentSum);
    }

    // 测试用例
    public static void main(String[] args) {
        LT129SumRootToLeaf solution = new LT129SumRootToLeaf();
        
        // 创建测试二叉树
        //       1
        //      / \
        //     2   3
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        
        System.out.println("测试案例1结果: " + solution.sumNumbers(root)); // 应该输出25 (1->2 和 1->3)
        System.out.println("测试案例1结果: " + solution.sumNumbersBFS(root));

        // 创建更复杂的二叉树
        //       4
        //      / \
        //     9   0
        //    / \
        //   5   1
        TreeNode root2 = new TreeNode(4);
        root2.left = new TreeNode(9);
        root2.right = new TreeNode(0);
        root2.left.left = new TreeNode(5);
        root2.left.right = new TreeNode(1);
        
        System.out.println("测试案例2结果: " + solution.sumNumbers(root2)); // 应该输出1026 (4->9->5, 4->9->1, 4->0)
        System.out.println("测试案例2结果: " + solution.sumNumbersBFS(root2));
    }


    public int sumNumbersBFS(TreeNode root) {
        if (root == null) return 0;

        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, root.val));
        int totalSum = 0;

        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.getKey();
            int currentSum = pair.getValue();

            if (node.left == null && node.right == null) {
                totalSum += currentSum;
            }

            if (node.left != null) {
                queue.offer(new Pair<>(node.left, currentSum * 10 + node.left.val));
            }

            if (node.right != null) {
                queue.offer(new Pair<>(node.right, currentSum * 10 + node.right.val));
            }
        }

        return totalSum;
    }

    static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}