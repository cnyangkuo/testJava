package com.practice.lt;

import java.util.*;

/**
 * 广度优先搜索（BFS）相关面试题集合
 * 包含3个典型的BFS问题
 */
public class BFSProblems {
    // 二叉树节点定义
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    
    /**
     * 问题1：二叉树层序遍历（LeetCode 102）
     * 思路分析：
     * 1. 使用队列存储每层节点
     * 2. 每次处理当前队列中所有节点，记录为一层
     * 3. 将下一层节点加入队列继续处理
     * 
     * @param root 二叉树根节点
     * @return 层序遍历结果
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(currentLevel);
        }
        return result;
    }
    
    /**
     * 问题2：最小基因变化（LeetCode 433）
     * 思路分析：
     * 1. 使用BFS搜索所有可能的基因变化
     * 2. 将bank转换为集合便于快速查找
     * 3. 每次改变一个字符，尝试所有可能的变化
     * 
     * @param start 起始基因
     * @param end 目标基因
     * @param bank 可能的中间基因
     * @return 最小变化次数
     */
    public static int minMutation(String start, String end, String[] bank) {
        if (start.equals(end)) return 0;
        
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        if (!bankSet.contains(end)) return -1;
        
        char[] chars = {'A', 'C', 'G', 'T'};
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            steps++;
            
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                
                for (int j = 0; j < current.length(); j++) {
                    for (char c : chars) {
                        if (current.charAt(j) == c) continue;
                        
                        String next = current.substring(0, j) + c + current.substring(j + 1);
                        if (next.equals(end) && bankSet.contains(next)) {
                            return steps;
                        }
                        
                        if (bankSet.contains(next) && !visited.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    /**
     * 问题3：打开转盘锁（LeetCode 752）
     * 思路分析：
     * 1. 使用BFS搜索所有可能的密码组合
     * 2. 每次旋转一个轮轴得到新密码
     * 3. 跳过死亡密码和已访问过的组合
     * 
     * @param deadends 死亡密码
     * @param target 目标密码
     * @return 打开最少次数
     */
    public static int openLock(String[] deadends, String target) {
        Set<String> dead = new HashSet<>(Arrays.asList(deadends));
        if (dead.contains("0000") || dead.contains(target)) return -1;
        
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        Set<String> visited = new HashSet<>();
        visited.add("0000");
        
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                
                if (current.equals(target)) return steps;
                
                for (int j = 0; j < 4; j++) {
                    for (int delta : new int[]{-1, 1}) {
                        int digit = (current.charAt(j) - '0' + delta + 10) % 10;
                        String next = current.substring(0, j) + digit + current.substring(j + 1);
                        
                        if (!visited.contains(next) && !dead.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                }
            }
            steps++;
        }
        return -1;
    }
    
    public static void main(String[] args) {
        // 测试示例1：二叉树层序遍历
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        System.out.println("二叉树层序遍历: " + levelOrder(root1));  // 输出[[3],[9,20],[15,7]]
        
        // 测试示例2：最小基因变化
        String start = "AACCGGTT";
        String end = "AAACGGTA";
        String[] bank = {"AACCGGTA","AAACGGTA","AAACGGTA"};
        System.out.println("最小基因变化次数: " + minMutation(start, end, bank));  // 输出2
        
        // 测试示例3：打开转盘锁
        String[] deadends = {"0201","0101","0111","1212","2002"};
        String target = "0202";
        System.out.println("打开转盘锁最少次数: " + openLock(deadends, target));  // 输出6
    }
}