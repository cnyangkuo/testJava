package com.interview.lt;

/**
 * 152. 乘积最大子数组
 * 给你一个整数数组 nums，请你找出数组中乘积最大的非空连续子数组，并返回该子数组所对应的乘积。
 * 难度: 中等
 * 标签: 数组, 动态规划
 */
public class LT152MaximumProductSubarray {
    // 使用动态规划解决乘积最大子数组问题
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 初始化当前最大值、当前最小值和结果
        int currMax = nums[0];
        int currMin = nums[0];
        int result = nums[0];

        // 遍历数组中的每个元素
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            // 解法1
            int prevMax = currMax; // 保存上一状态

            // 状态转移
            // 每个元素有三种可能组合：
            // 1.当前数本身 num
            // 2.与前一最大值相乘 prevMax * num
            // 3.与前一最小值相乘 prevMin * num
            // 即 :
            // currMax = max(num, prevMax * num, prevMin * num)，
            // currMin = min(num, prevMax * num, prevMin * num)
            currMax = Math.max(Math.max(prevMax * num, currMin * num), num);
            currMin = Math.min(Math.min(prevMax * num, currMin * num), num);

            // 更新全局最大值
            result = Math.max(result, currMax);

            // 解法2
//            // 如果当前数字为负数，交换最大值和最小值
//            // 因为负数会使最大值变最小值，最小值变最大值
//            if (num < 0) {
//                int temp = currMax;
//                currMax = currMin;
//                currMin = temp;
//            }
//
//            // 更新当前最大值和最小值
//            currMax = Math.max(num, currMax * num);
//            currMin = Math.min(num, currMin * num);
//
//            // 更新最终结果
//            result = Math.max(result, currMax);
        }

        return result;
    }

    // 测试用例
    public static void main(String[] args) {
        LT152MaximumProductSubarray solution = new LT152MaximumProductSubarray();
        
        // 测试案例1
        int[] nums1 = {2, 3, 5, -2, 4};
        System.out.println("测试案例1结果: " + solution.maxProduct(nums1)); // 应该输出30
        
        // 测试案例2
        int[] nums2 = {-2, 0, -1};
        System.out.println("测试案例2结果: " + solution.maxProduct(nums2)); // 应该输出0
        
        // 测试案例3
        int[] nums3 = {-4, -3, -2};
        System.out.println("测试案例3结果: " + solution.maxProduct(nums3)); // 应该输出12
    }
}