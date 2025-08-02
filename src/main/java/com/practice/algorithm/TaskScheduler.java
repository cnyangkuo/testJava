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
 *          入度表: 记录每个节点的依赖任务数量， B依赖A（A -> B, 先A后B），
 *                  则B的入度就增加1，标识还多少依赖的项目需要完成；决定可执行顺序，依次执行入度为0的节点。
 *          邻接表的Key表示任务，value表示任务的邻接节点，当前任务完成后可以继续完成邻接任务节点。
 *          B依赖A，则邻接表的Key为A，value是包含B的列表，表示任务A完成后可以继续完成任务B。
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
//        String[] dependencies = {"A->B", "B->C"};
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


//        Map<String, List<String>> map1 = new HashMap<>();
//        Map<String, List<String>> map2 = new HashMap<>();
//        String key = "job";
//        String value = "task";
//        String value2 = "task2";
//
//        // 写法1
//        if (!map1.containsKey(key)) {
//            map1.put(key, new ArrayList<>());
//        }
//        map1.get(key).add(value);
//        map1.get(key).add(value2);
//
//        // 写法2
//        map2.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
//        map2.computeIfAbsent(key, k -> new ArrayList<>()).add(value2);
//
//        // 验证结果相同
//        System.out.println("写法1结果: " + map1); // {job=[task]}
//        System.out.println("写法2结果: " + map2); // {job=[task]}
//        System.out.println("结果相等: " + map1.equals(map2)); // true
    }

    public static List<String> topologicalSort(String[] dependencies) {
        // 1. 构建邻接表和入度表
        Map<String, List<String>> adjList = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
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
                String preJob = prevTask.trim();
                
                // 添加任务到全局集合
//                allTaskNodes.add(preJob);
//                allTaskNodes.add(next);
                allTaskNodes.addAll(Arrays.asList(prevTask.trim(), next));
                
                // 显式初始化邻接表
//                if (!adjList.containsKey(preJob)) {
//                    adjList.put(preJob, new ArrayList<>());
//                }
//                adjList.get(preJob).add(next);
                adjList.computeIfAbsent(preJob, k -> new ArrayList<>()).add(next);
                
                // 更新入度表
//              // 方法1 （原始）
//                if (!inDegree.containsKey(next)) {
//                    inDegree.put(next, 0);
//                }
//                inDegree.put(next, indegree.get(next) + 1);

//              // 方法2 （适合复杂计算）
//                inDegree.compute(next, (k, v) -> (v == null) ? 1 : v + 1);

//              // 方法3 （推荐）
                inDegree.merge(next, 1, Integer::sum);

            }
        }
        // 邻接表 {A=[B], B=[C]}
        // 入度表 {B=1, C=1}, 没有A，即A=0
        System.out.println("邻接表: " + adjList);
        System.out.println("入度表: " + inDegree);
        
        // 3. 初始化队列
//      // 写法1
//        Queue<String> queue = new LinkedList<>();
//        for (String task : allTaskNodes) {
//            // 入度为0的节点入队
//            if (!inDegree.containsKey(task)) {
//                queue.offer(task);
//            }
//        }
//      // 写法2
        Queue<String> queue = allTaskNodes.stream().filter(task -> !inDegree.containsKey(task))
                .collect(Collectors.toCollection(() -> new LinkedList<>()));
        // 队列 [A]
        System.out.println("当前没有依赖的队列: " + queue);
        
        // 4. 拓扑排序
        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            result.add(curr);
            
            if (adjList.containsKey(curr)) {
                // 遍历当前节点的邻接节点, 第一轮找到B，第二轮找到C
                for (String neighbor : adjList.get(curr)) {
//                    // 原始写法
//                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
//                    // 优化的原子写法
                    inDegree.merge(neighbor, -1, Integer::sum);
                    // 入度为0的节点入队 （第一轮B入度为0，第二轮C入度为0）
                    if (inDegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    }
                }
                // 第一轮后 {B=0, C=1}, 第二轮后{B=0, C=0}
                System.out.println("入度表: " + inDegree);
                System.out.println("当前没有依赖的队列: " + queue);
            }
        }
        
        // 5. 检测环
        if (result.size() != allTaskNodes.size()) {
            throw new RuntimeException("存在循环依赖，无法完成调度");
        }
        
        return result;
    }
}