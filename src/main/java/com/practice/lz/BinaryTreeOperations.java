package com.practice.lz;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树常见算法题实现
 * 考察点：递归、遍历、序列化等
 * @param <T> 泛型类型
 */
public class BinaryTreeOperations<T> {
    // 二叉树节点定义
    private static class TreeNode<T> {
        T val;
        TreeNode<T> left;
        TreeNode<T> right;
        
        TreeNode(T val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
    
    private TreeNode<T> root;
    
    public BinaryTreeOperations() {
        this.root = null;
    }
    
    /**
     * 题目1：二叉树的序列化与反序列化
     * 核心思想：前序遍历+分隔符
     * @return 序列化字符串
     */
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode<T> node, StringBuilder sb) {
        if (node == null) {
            sb.append("#,");
            return;
        }
        
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }
    
    /**
     * 反序列化方法
     * @param data 序列化字符串
     * @return 构建的二叉树根节点
     */
    public TreeNode<T> deserialize(String data) {
        Queue<String> nodes = new LinkedList<>();
        nodes.addAll(Arrays.asList(data.split(",")));
        return deserializeHelper(nodes);
    }
    
    private TreeNode<T> deserializeHelper(Queue<String> nodes) {
        String val = nodes.poll();
        if (val.equals("#")) {
            return null;
        }
        
        TreeNode<T> node = new TreeNode<>((T) val);
        node.left = deserializeHelper(nodes);
        node.right = deserializeHelper(nodes);
        return node;
    }
    
    /**
     * 题目2：验证二叉搜索树
     * 核心思想：中序遍历验证有序性
     * @return true if BST
     */
    public boolean isValidBST() {
        return isValidBSTHelper(root, null, null);
    }

    /**
     * 递归方法实现
     * @param node 当前节点
     * @param min 最小值限制
     * @param max 最大值限制
     * @return true if BST
     */
    private boolean isValidBSTHelper(TreeNode<T> node, T min, T max) {
        if (node == null) return true;
        
        if ((min != null && ((Comparable<T>) node.val).compareTo(min) <= 0) || 
            (max != null && ((Comparable<T>) node.val).compareTo(max) >= 0)) {
            return false;
        }
        
        return isValidBSTHelper(node.left, min, node.val) && 
               isValidBSTHelper(node.right, node.val, max);
    }
    
    /**
     * 辅助方法：创建测试二叉树
     * @param values 层序遍历构造数组
     */
    public void createTree(T[] values) {
        if (values == null || values.length == 0) return;
        
        Queue<TreeNode<T>> queue = new LinkedList<>();
        root = new TreeNode<>(values[0]);
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode<T> current = queue.poll();
            
            if (i < values.length && values[i] != null) {
                current.left = new TreeNode<>(values[i]);
                queue.offer(current.left);
            }
            i++;
            
            if (i < values.length && values[i] != null) {
                current.right = new TreeNode<>(values[i]);
                queue.offer(current.right);
            }
            i++;
        }
    }
    
    /**
     * 辅助方法：打印二叉树（层序遍历）
     */
    public void printTree() {
        if (root == null) {
            System.out.println("空树");
            return;
        }
        
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode<T> node = queue.poll();
                if (node != null) {
                    System.out.print(node.val + " ");
                    queue.offer(node.left);
                    queue.offer(node.right);
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        // 测试用例1：序列化和反序列化
        BinaryTreeOperations<Integer> tree1 = new BinaryTreeOperations<>();
        Integer[] elements1 = {1, 2, 3, null, null, 4, 5};
        tree1.createTree(elements1);
        System.out.println("原始二叉树层序遍历:");
        tree1.printTree();
        
        String serialized = tree1.serialize();
        System.out.println("序列化结果: " + serialized);
        
        BinaryTreeOperations<Integer> deserializedTree = new BinaryTreeOperations<>();
        deserializedTree.root = deserializedTree.deserialize(serialized);
        System.out.println("反序列化后层序遍历:");
        deserializedTree.printTree();
        
        // 测试用例2：验证二叉搜索树
        BinaryTreeOperations<Integer> tree2 = new BinaryTreeOperations<>();
        Integer[] elements2 = {5, 1, 4, null, null, 3, 6};
        tree2.createTree(elements2);
        System.out.println("测试验证二叉搜索树:");
        tree2.printTree();
        System.out.println("是否是有效BST: " + tree2.isValidBST());
    }
}