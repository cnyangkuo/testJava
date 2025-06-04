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
 *          入度表: 记录每个节点的依赖数量，决定可执行顺序，依次执行入度为0的节点.
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
        List<Character> order = topologicalSort(dependencies);
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

    public static List<Character> topologicalSort(String[] dependencies) {
        // 存储所有任务节点
        Set<Character> nodes = new HashSet<>();
        // 邻接表：每个节点指向的后续节点 adjacencyList
        Map<Character, List<Character>> adj = new HashMap<>();
        // 入度表：每个节点的入度数
        Map<Character, Integer> inDegree = new HashMap<>();

        // 解析依赖关系，构建图
        for (String dep : dependencies) {
            String[] parts = dep.split("->");
            if (parts.length == 1) {
                char node = parts[0].charAt(0);
                nodes.add(node);
                inDegree.putIfAbsent(node, 0);
                continue;
            }
            String fromPart = parts[0];
            char to = parts[1].charAt(0);
            String[] froms = fromPart.split(",");
            for (String fromStr : froms) {
                char from = fromStr.charAt(0);
                nodes.add(from);
                nodes.add(to);
                adj.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
                inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
                inDegree.putIfAbsent(from, 0); // 确保from的入度初始化为0
            }
        }

        // 初始化入度表，确保所有节点都有记录
        for (char node : nodes) {
            inDegree.putIfAbsent(node, 0);
        }

        System.out.println("邻接表: " + adj);
        System.out.println("入度表: " + inDegree);

        // 初始化队列，加入所有入度为0的节点
        Queue<Character> queue = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        // 拓扑排序结果
        List<Character> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            char current = queue.poll();
            result.add(current);
            if (adj.containsKey(current)) {
                for (char neighbor : adj.get(current)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    // inDegree == 0 表示所有前置依赖已完成，任务可安全执行， 加入执行队列
                    if (inDegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    } else {
                        System.out.println("neighbor " + neighbor + " inDegree=" +  +inDegree.get(neighbor));
                    }
                }
            }
        }

        // 检查是否有环
        if (result.size() != nodes.size()) {
            throw new IllegalArgumentException("存在循环依赖，无法排序");
        }

        return result;
    }
}