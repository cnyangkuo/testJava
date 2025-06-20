package com.interview.lt;

/**
 * 35. 搜索插入位置
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。
 * 如果目标值不存在数组中，返回它将会被按顺序插入的那一个索引。
 * 难度: 简单
 * 标签: 数组, 二分查找
 */
public class LT035SearchInsertPosition {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    public static void main(String[] args) {
        LT035SearchInsertPosition solution = new LT035SearchInsertPosition();
        
        // 测试用例1
        int[] nums1 = {1, 3, 5, 6};
        int target1 = 5;
        int result1 = solution.searchInsert(nums1, target1);
        System.out.println("Test 1: " + result1 + " 期望输出: 2");
        
        // 测试用例2
        int[] nums2 = {1, 3, 5, 6};
        int target2 = 2;
        int result2 = solution.searchInsert(nums2, target2);
        System.out.println("Test 2: " + result2 + " 期望输出: 1");
        
        // 测试用例3
        int[] nums3 = {1, 3, 5, 6};
        int target3 = 7;
        int result3 = solution.searchInsert(nums3, target3);
        System.out.println("Test 3: " + result3 + " 期望输出: 4");
    }
}