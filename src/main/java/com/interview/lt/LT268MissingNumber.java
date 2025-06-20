package com.interview.lt;

/**
 * 268. 缺失的数字
 * 给定一个包含 [0, n] 中的 n 个数的数组 nums ，找出 [0, n] 范围内没有出现在数组中的那个数。
 * 难度: 简单
 * 标签: 数组, 数学
 */
public class LT268MissingNumber {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        
        for (int num : nums) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }
    
    public static void main(String[] args) {
        LT268MissingNumber solution = new LT268MissingNumber();
        
        // 测试用例1
        int[] nums1 = {3, 0, 1};
        int result1 = solution.missingNumber(nums1);
        System.out.println("Test 1: " + result1 + " 期望输出: 2");
        
        // 测试用例2
        int[] nums2 = {9, 6, 4, 2, 3, 5, 7, 0, 1};
        int result2 = solution.missingNumber(nums2);
        System.out.println("Test 2: " + result2 + " 期望输出: 8");
        
        // 测试用例3
        int[] nums3 = {0};
        int result3 = solution.missingNumber(nums3);
        System.out.println("Test 3: " + result3 + " 期望输出: 1");
    }
}