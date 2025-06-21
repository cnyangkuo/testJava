package com.interview.lt;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author yangkuo
 * @date 2025/6/21
 * @description
 */
public class TreeNode<T> {
    T val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}


    TreeNode(T x) {
        val = x;
    }
    
    TreeNode(T x, TreeNode left, TreeNode right) {
        val = x;
        this.left = left;
        this.right = right;
    }

    public TreeNode createTree(T[] values) {
        if (values == null || values.length == 0) {
            return null;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        TreeNode root = new TreeNode<>(values[0]);
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
        return root;
    }
        
}
