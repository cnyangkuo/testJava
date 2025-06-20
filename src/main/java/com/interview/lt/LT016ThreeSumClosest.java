package com.interview.lt;

import java.util.Arrays;

/**
 * 16. 最接近的三数之和
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，请找出这三个元素使得它们的和最接近 target。
 * 返回这三个元素的和。
 * 难度: 中等
 * 标签: 数组, 双指针
 */
public class LT016ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];
        
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复的第一个数字
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];
                
                // 如果找到精确匹配，直接返回
                if (currentSum == target) {
                    return currentSum;
                }
                
                // 更新最接近的和
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }
                
                // 移动指针
                if (currentSum < target) {
                    left++;
                    // 跳过重复值
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                } else {
                    right--;
                    // 跳过重复值
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                }
            }
        }
        
        return closestSum;
    }
    
    public static void main(String[] args) {
        LT016ThreeSumClosest solution = new LT016ThreeSumClosest();
        
        // 测试用例
        int[] nums = {-1, 0, 1, 8, -1, 4};
        int target = 3;
        int result = solution.threeSumClosest(nums, target);
        System.out.println("最接近的三数之和: " + result);
        // 期望输出: 2 (组合是 -1 + -1 + 4)
    }
}