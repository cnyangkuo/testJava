package com.interview.lt;

/**
 * 153. 寻找旋转排序数组中的最小值
 * 已知一个长度为 n 的数组，预先按照升序排列，在经过 1 到 n 次旋转后得到输入数组。
 * 请找出数组中最小的元素。
 * 难度: 中等
 * 标签: 数组, 二分查找
 */
public class LT153FindMinimumInRotatedSortedArray {
    // 使用二分查找算法解决旋转排序数组最小值问题
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("输入数组不能为空");
        }

        int left = 0;
        int right = nums.length - 1;
        
        // 当数组不是完全升序时继续查找
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // 如果中间元素大于右指针元素，说明最小值在右侧
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } 
            // 否则最小值在左侧（包括mid本身）
            else {
                right = mid;
            }
        }
        
        // 返回找到的最小值
        return nums[left];
    }

    // 测试用例
    public static void main(String[] args) {
        LT153FindMinimumInRotatedSortedArray solution = new LT153FindMinimumInRotatedSortedArray();
        
        // 测试案例1
        int[] nums1 = {3, 4, 5, 1, 2};
        System.out.println("测试案例1结果: " + solution.findMin(nums1)); // 应该输出1
        
        // 测试案例2
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("测试案例2结果: " + solution.findMin(nums2)); // 应该输出0
        
        // 测试案例3
        int[] nums3 = {1};
        System.out.println("测试案例3结果: " + solution.findMin(nums3)); // 应该输出1
        
        // 测试案例4
        int[] nums4 = {3, 1};
        System.out.println("测试案例4结果: " + solution.findMin(nums4)); // 应该输出1
    }
}