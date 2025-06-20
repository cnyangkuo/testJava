package com.interview.lt;

import java.util.HashSet;
import java.util.Set;

/**
 * 217. 存在重复元素
 * 给定一个整数数组，判断是否存在重复元素。
 * 难度: 简单
 * 标签: 数组, 哈希表
 */
public class LT217ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        
        for (int num : nums) {
            if (!numSet.add(num)) {
                return true; // 添加失败说明已存在
            }
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        LT217ContainsDuplicate solution = new LT217ContainsDuplicate();
        
        // 测试用例1
        int[] nums1 = {1, 2, 3, 1};
        boolean result1 = solution.containsDuplicate(nums1);
        System.out.println("Test 1: " + result1 + " 期望输出: true");
        
        // 测试用例2
        int[] nums2 = {3, 1};
        boolean result2 = solution.containsDuplicate(nums2);
        System.out.println("Test 2: " + result2 + " 期望输出: false");
    }
}