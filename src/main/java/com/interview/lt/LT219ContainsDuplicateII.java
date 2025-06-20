package com.interview.lt;

import java.util.HashMap;
import java.util.Map;

/**
 * 219. 存在重复元素 II
 * 给定一个整数数组和一个整数 k，判断是否存在两个不同索引 i 和 j，使得 nums[i] = nums[j] 且 |i - j| ≤ k。
 * 难度: 简单
 * 标签: 数组, 哈希表
 */
public class LT219ContainsDuplicateII {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> numMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            if (numMap.containsKey(nums[i])) {
                if (i - numMap.get(nums[i]) <= k) {
                    return true;
                }
            }
            // 更新该数字最后一次出现的索引
            numMap.put(nums[i], i);
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        LT219ContainsDuplicateII solution = new LT219ContainsDuplicateII();
        
        // 测试用例1
        int[] nums1 = {1, 2, 3, 1};
        int k1 = 3;
        boolean result1 = solution.containsNearbyDuplicate(nums1, k1);
        System.out.println("Test 1: " + result1 + " 期望输出: true");
        
        // 测试用例2
        int[] nums2 = {1, 0, 1, 1};
        int k2 = 1;
        boolean result2 = solution.containsNearbyDuplicate(nums2, k2);
        System.out.println("Test 2: " + result2 + " 期望输出: true");
        
        // 测试用例3
        int[] nums3 = {1, 2, 3, 4, 5, 6, 7, 8};
        int k3 = 3;
        boolean result3 = solution.containsNearbyDuplicate(nums3, k3);
        System.out.println("Test 3: " + result3 + " 期望输出: false");
    }
}