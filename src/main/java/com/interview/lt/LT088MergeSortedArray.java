package com.interview.lt;

/**
 * 88. 合并两个有序数组
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2。
 * 将 nums2 合并为 nums1 并返回 合并后的数组。
 * 难度: 简单
 * 标签: 数组, 双指针
 */
public class LT088MergeSortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 使用三个指针：
        // p1 指向 nums1 的最后一个元素
        // p2 指向 nums2 的最后一个元素
        // p 指向合并后 nums1 的最后一个位置
        
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        
        // 从后向前合并
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }
        
        // 如果 nums2 中还有剩余元素，将它们复制到 nums1 中
        while (p2 >= 0) {
            nums1[p] = nums2[p2];
            p2--;
            p--;
        }
    }
    
    public static void main(String[] args) {
        LT088MergeSortedArray solution = new LT088MergeSortedArray();
        
        // 测试用例1
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = 3;
        
        solution.merge(nums1, m, nums2, n);
        System.out.print("Test 1 Result: [");
        for (int i = 0; i < nums1.length; i++) {
            System.out.print(nums1[i]);
            if (i < nums1.length - 1) System.out.print(", ");
        }
        System.out.println("] 期望输出: [1, 2, 2, 3, 5, 6]");
        
        // 测试用例2 - 其中一个数组为空
        int[] nums3 = {0};
        int m2 = 0;
        int[] nums4 = {1};
        int n2 = 1;
        
        solution.merge(nums3, m2, nums4, n2);
        System.out.print("Test 2 Result: [");
        for (int i = 0; i < nums3.length; i++) {
            System.out.print(nums3[i]);
            if (i < nums3.length - 1) System.out.print(", ");
        }
        System.out.println("] 期望输出: [1]");
    }
}