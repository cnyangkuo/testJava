package com.interview.lt;

/**
 * 27. 移除元素
 * 给定一个整数数组 nums 和一个整数 val，在原地移除所有等于 val 的元素，并返回移除后的长度。
 * 难度: 简单
 * 标签: 数组, 双指针
 */
public class LT027RemoveElement {
    public int removeElement(int[] nums, int val) {
        int i = 0; // 慢指针，指向下一个要保留的元素位置
        
        for (int j = 0; j < nums.length; j++) {
            // 如果当前元素不等于val
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        
        return i;
    }
    
    public static void main(String[] args) {
        LT027RemoveElement solution = new LT027RemoveElement();
        
        // 测试用例1
        int[] nums1 = {3, 2, 2, 3};
        int val1 = 3;
        int result1 = solution.removeElement(nums1, val1);
        System.out.println("Test 1: " + result1 + " 期望输出: 2");
        
        // 测试用例2
        int[] nums2 = {0, 1, 2, 2, 3, 0, 4, 2};
        int val2 = 2;
        int result2 = solution.removeElement(nums2, val2);
        System.out.println("Test 2: " + result2 + " 期望输出: 5");
    }
}