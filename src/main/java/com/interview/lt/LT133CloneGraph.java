package com.interview.lt;

import java.util.*;

/**
 * 133. 克隆图
 * 给你无向连通图中一个节点的引用，请你返回该图的深拷贝（克隆）。
 * 难度: 中等
 * 标签: 深度优先搜索, 广度优先搜索
 */
public class LT133CloneGraph {
    // 定义图节点结构
    public static class Node {
        public int val;
        public List<Node> neighbors;
        
        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }
        
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<>();
        }
        
        public Node(int _val, List<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
    
    // 使用深度优先搜索克隆图
    private Map<Node, Node> visited = new HashMap<>();
    
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        
        // 如果该节点已经被访问过，直接返回克隆节点
        if (visited.containsKey(node)) {
            return visited.get(node);
        }
        
        // 创建新节点并标记为已访问
        Node cloneNode = new Node(node.val, new ArrayList<>());
        visited.put(node, cloneNode);
        
        // 递归克隆所有邻居节点
        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(cloneGraph(neighbor));
        }
        
        return cloneNode;
    }

    // 测试用例
    public static void main(String[] args) {
        LT133CloneGraph solution = new LT133CloneGraph();
        
        // 创建测试用例
        // 创建4个节点
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        
        // 建立连接关系
        node1.neighbors.add(node2);
        node1.neighbors.add(node4);
        
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);
        
        node4.neighbors.add(node1);
        node4.neighbors.add(node3);
        
        // 克隆图
        Node clonedNode = solution.cloneGraph(node1);
        
        // 验证克隆后的图
        System.out.println("克隆后的图的邻接关系:");
        solution.printGraph(clonedNode);
    }
    
    // 打印图的结构
    private void printGraph(Node node) {
        if (node == null) {
            return;
        }
        
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        visited.add(node);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print("节点 " + current.val + " 的邻居: ");
            StringBuilder sb = new StringBuilder();
            for (Node neighbor : current.neighbors) {
                sb.append(neighbor.val).append(" ");
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
            System.out.println(sb.toString().trim());
        }
    }
}