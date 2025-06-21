package com.interview.lt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 90. 子集 II 
 * 给定一个可能包含重复元素的整数数组 nums，返回所有可能的子集（幂集）。
 * 难度: 中等
 * 标签: 数组, 回溯算法
 */
public class LT090SubsetII {
    // 使用回溯算法解决子集 II 问题
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if (nums == null) {
            return new ArrayList<>();
        }
        
        Arrays.sort(nums); // 先排序，确保重复元素相邻
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    // 回溯方法生成所有可能的子集
    private void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
        result.add(new ArrayList<>(path)); // 添加当前子集
        
        for (int i = start; i < nums.length; i++) {
            // 跳过重复元素
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            
            path.add(nums[i]); // 选择当前元素
            backtrack(nums, i + 1, path, result); // 递归探索
            path.remove(path.size() - 1); // 回溯，撤销选择
        }
    }

    // 测试用例
    public static void main(String[] args) {
        LT090SubsetII solution = new LT090SubsetII();
        
        // 测试案例1
        int[] nums1 = {1, 2, 2};
        System.out.println("测试案例1结果:");
        List<List<Integer>> result1 = solution.subsetsWithDup(nums1);
        for (List<Integer> subset : result1) {
            System.out.println(subset);
        }
        // 可能输出:
        // []
        // [1]
        // [1,2]
        // [1,2,2]
        // [2]
        // [2,2]
        
        // 测试案例2
        int[] nums2 = {1, 1, 1};
        System.out.println("\n测试案例2结果:");
        List<List<Integer>> result2 = solution.subsetsWithDup(nums2);
        for (List<Integer> subset : result2) {
            System.out.println(subset);
        }
        // 可能输出:
        // []
        // [1]
        // [1,1]
        // [1,1,1]
    }
}