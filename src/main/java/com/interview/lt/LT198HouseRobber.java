package com.interview.lt;

/**
 * 198. 打家劫舍
 * 给定一个表示每个房屋存放金额的非负整数数组，你不能同时偷相邻的房屋。计算并返回抢劫的最大金额。
 * 难度: 简单
 * 标签: 数组, 动态规划
 */
public class LT198HouseRobber {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        
        int prev = nums[0]; // f(n-2)
        int curr = Math.max(nums[0], nums[1]); // f(n-1)
        
        for (int i = 2; i < nums.length; i++) {
            int temp = curr;
            curr = Math.max(curr, prev + nums[i]);
            prev = temp;
        }
        
        return curr;
    }
    
    public static void main(String[] args) {
        LT198HouseRobber solution = new LT198HouseRobber();
        
        // 测试用例1
        int[] nums1 = {1, 2, 3, 1};
        int result1 = solution.rob(nums1);
        System.out.println("Test 1: " + result1 + " 期望输出: 4");
        
        // 测试用例2
        int[] nums2 = {2, 7, 9, 3, 1};
        int result2 = solution.rob(nums2);
        System.out.println("Test 2: " + result2 + " 期望输出: 15");
        
        // 测试用例3
        int[] nums3 = {2, 1, 1, 2};
        int result3 = solution.rob(nums3);
        System.out.println("Test 3: " + result3 + " 期望输出: 4");
    }
}