package com.interview.lt;

/**
 * 26. 删除有序数组中的重复项
 * 给定一个升序排列的数组，删除重复项并返回新的长度。
 * 难度: 简单
 * 标签: 数组, 双指针
 */
public class LT026RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        
        int i = 0; // 慢指针，指向最后一个不重复的元素
        
        for (int j = 1; j < nums.length; j++) {
            // 如果发现不同的元素
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        
        return i + 1;
    }
    
    public static void main(String[] args) {
        LT026RemoveDuplicatesFromSortedArray solution = new LT026RemoveDuplicatesFromSortedArray();
        
        // 测试用例1
        int[] nums1 = {1, 1, 2};
        int result1 = solution.removeDuplicates(nums1);
        System.out.println("Test 1: " + result1 + " 期望输出: 2");
        
        // 测试用例2
        int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int result2 = solution.removeDuplicates(nums2);
        System.out.println("Test 2: " + result2 + " 期望输出: 5");
    }
}