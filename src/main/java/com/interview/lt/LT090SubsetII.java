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

        // 这里的start变量表示当前递归的起始位置，确保同一层级的重复元素只被处理一次。
        for (int i = start; i < nums.length; i++) {
            // 跳过重复元素, 同一递归层级（start相同）时, 当前元素与前一个元素重复，且前一个元素已经被处理过, 此时继续选择会导致同一层级生成重复子集。
            // 如果数组是[1,2,2]，当在第一层递归中选择第二个2的时候，如果前面的2已经被处理过，那么再次选择就会导致重复。
            // 假设当前路径是[1]，然后选择第二个元素2，此时剩下的元素是第三个2。如果不跳过的话，可能会生成[1,2]两次。
            // 因此，需要在同一层级中，如果遇到相同的元素且前一个相同的元素未被使用，则跳过当前元素，以避免重复。
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