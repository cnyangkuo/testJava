package com.practice.algorithm;

/**
 * @author yangkuo
 *
 * 核心思路
     * 解析输入字符串，生成所有边。
     * 收集所有节点。
     * 初始化邻接表和入度表。
     * 执行Kahn算法。
 * 图的表示：
     * 将任务视为图的节点，依赖关系视为有向边。
     * 构建邻接表（adjacency）和入度表（inDegree）。
 *          入度表: 记录每个节点的依赖数量， 先A后B（A依赖B），则B的入度就增加1，标识还多少依赖的项目需要完成；决定可执行顺序，依次执行入度为0的节点。
 * 拓扑排序：
     * 使用 Kahn算法（基于入度）实现拓扑排序。
     * 找出所有入度为0的节点，依次处理并更新其邻居的入度。
    * 环检测 若最终拓扑序列长度 < 总节点数，说明存在环（依赖冲突
 * 处理多解性：
     * 拓扑排序结果可能有多个，只要满足依赖关系即可。
 *
 *
 *
 * Kahn算法：基于广度优先搜索的拓扑排序算法。
 * 步骤应该是初始化队列，将所有入度为0的节点加入队列，然后依次取出节点，
 * 处理其邻接节点，减少它们的入度，直到队列为空。
 * 算法的时间复杂度是O(V+E)，适合大规模数据。
 * 可能的应用场景可能是任务调度、课程安排等有依赖关系的场景。
 * 循环依赖：如果存在循环依赖，则无法进行拓扑排序。
 *
 *
 * @date 2025/5/26
 * @description
 */
import java.util.*;
import java.util.stream.Collectors;

public class TaskScheduler {

    public static void main(String[] args) {
        String[] dependencies = {"A->B", "B,C->D", "E->C", "B->F", "G"};
        List<String> order = topologicalSort(dependencies);
        System.out.println("任务执行顺序: " + order.stream()
                .map(String::valueOf)
                .collect(Collectors.joining()));
        /*
            邻接表: {A=[B], B=[D, F], C=[D], E=[C]}
            入度表: {A=0, B=1, C=1, D=2, E=0, F=1, G=0}
            neighbor D inDegree=1
            任务执行顺序: AEGBCFD
         */
    }

    public static List<String> topologicalSort(String[] dependencies) {
        // 1. 构建邻接表和入度表
        Map<String, List<String>> adjList = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();
        Set<String> allTaskNodes = new HashSet<>();
        
        // 2. 解析依赖关系
        for (String dep : dependencies) {
            // 处理单个任务或依赖关系
            if (!dep.contains("->")) {
                allTaskNodes.add(dep.trim());
                continue;
            }
            
            String[] pair = dep.split("->");
            if (pair.length != 2) {
                throw new IllegalArgumentException("无效的依赖格式: " + dep);
            }
            
            String prevPart = pair[0].trim();
            String next = pair[1].trim();
            
            // 处理多个前置任务的情况
            String[] prevTasks = prevPart.split(",");
            for (String prevTask : prevTasks) {
                String task = prevTask.trim();
                
                // 添加任务到全局集合
                allTaskNodes.add(task);
                allTaskNodes.add(next);
                
                // 显式初始化邻接表
                if (!adjList.containsKey(task)) {
                    adjList.put(task, new ArrayList<>());
                }
                adjList.get(task).add(next);
                
                // 更新入度表
                if (!indegree.containsKey(next)) {
                    indegree.put(next, 0);
                }
                indegree.put(next, indegree.get(next) + 1);
            }
        }
        
        // 3. 初始化队列
        Queue<String> queue = new LinkedList<>();
        for (String task : allTaskNodes) {
            if (!indegree.containsKey(task)) {
                queue.offer(task);
            }
        }
        
        // 4. 拓扑排序
        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            result.add(curr);
            
            if (adjList.containsKey(curr)) {
                for (String neighbor : adjList.get(curr)) {
                    indegree.put(neighbor, indegree.get(neighbor) - 1);
                    if (indegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }
        
        // 5. 检测环
        if (result.size() != allTaskNodes.size()) {
            throw new RuntimeException("存在循环依赖，无法完成调度");
        }
        
        return result;
    }
}