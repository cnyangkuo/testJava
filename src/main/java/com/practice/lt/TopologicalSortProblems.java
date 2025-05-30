package com.practice.lt;

import java.util.*;

/**
 * 拓扑排序相关面试题集合
 * 包含3个典型的拓扑排序问题
 */
public class TopologicalSortProblems {
    /**
     * 问题1：课程表II（LeetCode 210）
     * 思路分析：
     * 1. 构建邻接表表示课程依赖关系
     * 2. 使用BFS进行拓扑排序
     * 3. 统计入度，每次处理入度为0的课程
     * 
     * @param numCourses 课程总数
     * @param prerequisites 课程依赖关系
     * @return 可能的学习顺序
     */
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        // 构建邻接表和入度表
        List<List<Integer>> adjList = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }
        
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prerequisite = pre[1];
            adjList.get(prerequisite).add(course);
            inDegree[course]++;
        }
        
        // 初始化队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int[] result = new int[numCourses];
        int index = 0;
        
        while (!queue.isEmpty()) {
            int course = queue.poll();
            result[index++] = course;
            
            for (int nextCourse : adjList.get(course)) {
                inDegree[nextCourse]--;
                if (inDegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }
        
        // 如果存在环则无法完成所有课程
        if (index != numCourses) {
            return new int[0];
        }
        return result;
    }
    
    /**
     * 问题2：序列重建（LeetCode 444）
     * 思路分析：
     * 1. 根据原始序列构建邻接表
     * 2. 统计每个节点的入度
     * 3. 使用拓扑排序验证序列是否唯一
     * 
     * @param org 原始序列
     * @param seqs 子序列集合
     * @return 是否存在唯一拓扑序列
     */
    public static boolean sequenceReconstruction(int[] org, int[][] seqs) {
        // 构建邻接表和入度表
        Map<Integer, Set<Integer>> adjList = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        
        // 初始化所有节点
        for (int num : org) {
            adjList.put(num, new HashSet<>());
            inDegree.put(num, 0);
        }
        
        // 构建邻接表
        for (int[] seq : seqs) {
            for (int i = 0; i < seq.length - 1; i++) {
                int u = seq[i];
                int v = seq[i + 1];
                
                if (!adjList.containsKey(u)) adjList.put(u, new HashSet<>());
                if (!adjList.containsKey(v)) adjList.put(v, new HashSet<>());
                
                if (!inDegree.containsKey(u)) inDegree.put(u, 0);
                if (!inDegree.containsKey(v)) inDegree.put(v, 0);
                
                if (adjList.get(u).add(v)) {
                    inDegree.put(v, inDegree.get(v) + 1);
                }
            }
        }
        
        // 拓扑排序
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        
        int[] result = new int[org.length];
        int index = 0;
        
        while (!queue.isEmpty()) {
            if (queue.size() > 1) return false; // 不唯一
            int node = queue.poll();
            result[index++] = node;
            
            for (int neighbor : adjList.getOrDefault(node, new HashSet<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // 检查结果是否匹配原始序列
        if (index != org.length) return false;
        for (int i = 0; i < org.length; i++) {
            if (result[i] != org[i]) return false;
        }
        return true;
    }
    
    /**
     * 问题3：外星语词典（LeetCode 269）
     * 思路分析：
     * 1. 通过单词列表构建字符之间的相对顺序
     * 2. 使用拓扑排序确定字母顺序
     * 3. 检查是否存在环或有前缀冲突
     * 
     * @param words 单词列表
     * @return 字符顺序
     */
    public static String alienOrder(String[] words, String order) {
        // 构建邻接表
        Map<Character, Set<Character>> adjList = new HashMap<>();
        Map<Character, Integer> inDegree = new HashMap<>();
        
        // 初始化邻接表
        for (String word : words) {
            for (char c : word.toCharArray()) {
                adjList.putIfAbsent(c, new HashSet<>());
                inDegree.putIfAbsent(c, 0);
            }
        }
        
        // 构建邻接表
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            
            int minLength = Math.min(word1.length(), word2.length());
            for (int j = 0; j < minLength; j++) {
                char c1 = word1.charAt(j);
                char c2 = word2.charAt(j);
                
                if (c1 != c2) {
                    if (!adjList.get(c1).contains(c2)) {
                        adjList.get(c1).add(c2);
                        inDegree.put(c2, inDegree.get(c2) + 1);
                    }
                    break;
                }
            }
        }
        
        // 拓扑排序
        Queue<Character> queue = new LinkedList<>();
        StringBuilder result = new StringBuilder();
        
        for (Map.Entry<Character, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        
        while (!queue.isEmpty()) {
            if (queue.size() > 1) return ""; // 不唯一
            char c = queue.poll();
            result.append(c);
            
            for (char neighbor : adjList.getOrDefault(c, new HashSet<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // 检查是否有环
        if (result.length() != inDegree.size()) return "";
        return result.toString();
    }
    
    public static void main(String[] args) {
        // 测试示例1：课程表II
        int numCourses = 4;
        int[][] prerequisites = {{1,0},{2,0},{3,1},{3,2}};
        int[] result1 = findOrder(numCourses, prerequisites);
        System.out.print("课程学习顺序: ");
        for (int course : result1) {
            System.out.print(course + " ");  // 输出0 1 2 3 或 0 2 1 3
        }
        System.out.println();
        
        // 测试示例2：序列重建
        int[] org = {1,2,3};
        int[][] seqs = {{1,2}, {1,3}, {2,3}};
        boolean result2 = sequenceReconstruction(org, seqs);
        System.out.println("序列是否可重建: " + result2);  // 输出true
        
        // 测试示例3：外星语词典
        String[] words = {"wrt","wrf","er","ett","rftt"};
        String order = alienOrder(words, "wertf");
        System.out.println("外星语顺序: " + order);  // 输出"werft"或"werft"的变体
    }
}