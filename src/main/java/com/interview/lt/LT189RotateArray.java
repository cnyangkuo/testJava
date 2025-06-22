package com.interview.lt;

// 添加LT189旋转数组问题解决方案
public class LT189RotateArray {
    // 题目：旋转数组（Rotate Array）
    // 难度：简单
    // 描述：将数组nums向右旋转k次，其中k是非负整数。
    // 解法：三次反转法，时间复杂度O(n)，空间复杂度O(1)
    
    public void rotate(int[] nums, int k) {
        k %= nums.length; // 处理k大于数组长度的情况
        reverse(nums, 0, nums.length - 1); // 反转整个数组
        reverse(nums, 0, k - 1); // 反转前k个元素
        reverse(nums, k, nums.length - 1); // 反转剩下的元素
    }
    
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
    
    public static void main(String[] args) {
        LT189RotateArray solution = new LT189RotateArray();
        int[] nums1 = {1,2,3,4,5,6,7};
        int k1 = 3;
        solution.rotate(nums1, k1);
        System.out.print("Test case 1: ");
        printArray(nums1); // 应该输出[5, 6, 7, 1, 2, 3, 4]
        
        int[] nums2 = {-1,-100,3,99};
        int k2 = 2;
        solution.rotate(nums2, k2);
        System.out.print("Test case 2: ");
        printArray(nums2); // 应该输出[3, 99, -1, -100]
    }
    
    private static void printArray(int[] nums) {
        System.out.print("[");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
            if (i < nums.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}