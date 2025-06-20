package com.interview.lt;

/**
 * 167. 两数之和 II - 输入有序数组
 * 给定一个已排序的整数数组，找出目标值 target 的两个数，使它们的和等于 target。
 * 难度: 简单
 * 标签: 数组, 双指针
 */
public class LT167TwoSumII {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                // 返回基于1的索引
                return new int[] {left + 1, right + 1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        throw new IllegalArgumentException("No solution found");
    }
    
    public static void main(String[] args) {
        LT167TwoSumII solution = new LT167TwoSumII();
        
        // 测试用例1
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] result1 = solution.twoSum(nums1, target1);
        System.out.println("Test 1: [" + result1[0] + ", " + result1[1] + "] 期望输出: [1, 2]");
        
        // 测试用例2
        int[] nums2 = {2, 3, 4};
        int target2 = 6;
        int[] result2 = solution.twoSum(nums2, target2);
        System.out.println("Test 2: [" + result2[0] + ", " + result2[1] + "] 期望输出: [1, 3]");
        
        // 测试用例3
        int[] nums3 = {-1, 0};
        int target3 = -1;
        int[] result3 = solution.twoSum(nums3, target3);
        System.out.println("Test 3: [" + result3[0] + ", " + result3[1] + "] 期望输出: [1, 2]");
    }
}