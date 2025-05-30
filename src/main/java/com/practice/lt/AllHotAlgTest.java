package com.practice.lt;

/**
 * 集中式测试类，运行所有10个算法类的测试用例
 * 每个算法类选取1-2个代表性测试用例进行验证
 */
public class AllHotAlgTest {
    public static void main(String[] args) {
        System.out.println("===== 动态规划算法测试 =====");
        DynamicProgrammingProblems.main(args);
        
        System.out.println("\n===== 双指针算法测试 =====");
        TwoPointersProblems.main(args);
        
        System.out.println("\n===== 位运算算法测试 =====");
        BitManipulationProblems.main(args);
        
        System.out.println("\n===== 贪心算法测试 =====");
        GreedyAlgorithmsProblems.main(args);
        
        System.out.println("\n===== 深度优先搜索测试 =====");
        DFSProblems.main(args);
        
        System.out.println("\n===== 广度优先搜索测试 =====");
        BFSProblems.main(args);
        
        System.out.println("\n===== 堆相关算法测试 =====");
        HeapProblems.main(args);
        
        System.out.println("\n===== 滑动窗口算法测试 =====");
        SlidingWindowProblems.main(args);
        
        System.out.println("\n===== 拓扑排序算法测试 =====");
        TopologicalSortProblems.main(args);
        
        System.out.println("\n===== 回溯算法测试 =====");
        BacktrackingProblems.main(args);
    }
}