package com.interview.lt;

/**
 * 283. 移动零
 * 给定一个数组 nums，编写函数将所有 0 移动到它的末尾，同时保持非零元素的相对顺序。
 * 难度: 简单
 * 标签: 数组, 双指针
 */
public class LT283MoveZeroes {
    public void moveZeroes(int[] nums) {
        int nonZeroIndex = 0; // 指向下一个非零元素的位置
        
        // 第一步：将所有非零元素移动到前面
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[nonZeroIndex++] = nums[i];
            }
        }
        
        // 第二步：将剩余位置填充为0
        while (nonZeroIndex < nums.length) {
            nums[nonZeroIndex++] = 0;
        }
    }
    
    public static void main(String[] args) {
        LT283MoveZeroes solution = new LT283MoveZeroes();
        
        // 测试用例1
        int[] nums1 = {0, 1, 0, 3, 12};
        solution.moveZeroes(nums1);
        System.out.print("Test 1 Result: [");
        for (int i = 0; i < nums1.length; i++) {
            System.out.print(nums1[i]);
            if (i < nums1.length - 1) System.out.print(", ");
        }
        System.out.println("] 期望输出: [1, 3, 12, 0, 0]");
        
        // 测试用例2
        int[] nums2 = {0};
        solution.moveZeroes(nums2);
        System.out.print("Test 2 Result: [");
        for (int i = 0; i < nums2.length; i++) {
            System.out.print(nums2[i]);
            if (i < nums2.length - 1) System.out.print(", ");
        }
        System.out.println("] 期望输出: [0]");
    }
}