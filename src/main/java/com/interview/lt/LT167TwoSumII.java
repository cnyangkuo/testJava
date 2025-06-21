package com.interview.lt;

/**
 * 167. 两数之和 II - 输入有序数组
 * 给定一个已排序的整数数组，找出目标值 target 的两个数，使它们的和等于 target。
 * 难度: 简单
 * 标签: 数组, 双指针
 */
public class LT167TwoSumII {
    // 题目：两数之和II - 输入有序数组（Two Sum II - Input array is sorted）
    // 难度：简单
    // 描述：给定一个已按照升序排列的有序数组，找到两个数使得它们之和等于目标数。
    // 解法：双指针法，时间复杂度O(n)
    
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                // 结果索引从1开始
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[]{-1, -1}; // 根据题意，这行实际上不会执行
    }
    
    public static void main(String[] args) {
        LT167TwoSumII solution = new LT167TwoSumII();
        int[] nums1 = {2,7,11,15};
        int target1 = 9;
        int[] result1 = solution.twoSum(nums1, target1);
        System.out.println("Test case 1: [" + result1[0] + ", " + result1[1] + "]"); // 应该输出[1, 2]
        
        int[] nums2 = {2,3,4};
        int target2 = 6;
        int[] result2 = solution.twoSum(nums2, target2);
        System.out.println("Test case 2: [" + result2[0] + ", " + result2[1] + "]"); // 应该输出[1, 3]
    }
}