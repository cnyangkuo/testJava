package com.practice.lt;

import java.util.*;

/**
 * 双指针相关面试题集合
 * 包含3个典型的双指针问题解决方案
 */
public class TwoPointersProblems {
    /**
     * 问题1：盛最多水的容器（LeetCode 11）
     * 思路分析：
     * 1. 使用左右双指针，初始指向数组首尾
     * 2. 每次移动较小的高度向内收缩
     * 3. 计算面积并更新最大值
     * 
     * @param height 盛水板高度数组
     * @return 最大盛水量
     */
    public static int maxArea(int[] height) {
        if (height == null || height.length < 2) return 0;
        
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            int currentHeight = Math.min(height[left], height[right]);
            int width = right - left;
            int area = currentHeight * width;
            maxArea = Math.max(maxArea, area);
            
            // 移动较小的高度指针
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
    
    /**
     * 问题2：三数之和（LeetCode 15）
     * 思路分析：
     * 1. 先对数组排序
     * 2. 固定第一个数，用双指针在剩余部分找另外两个数
     * 3. 跳过重复值保证结果唯一性
     * 
     * @param nums 输入数组
     * @return 所有满足条件的三元组
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) return result;

        // 先排序
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复值
            if (i > 0 && nums[i] == nums[i-1]) continue;
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 跳过重复值
                    while (left < right && nums[left] == nums[left+1]) left++;
                    while (left < right && nums[right] == nums[right-1]) right--;
                    
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
    
    /**
     * 问题3：字符串转换（LeetCode 186）
     * 思路分析：
     * 1. 将字符串转换为字符数组
     * 2. 使用双指针交换字符
     * 
     * @param s 输入字符串
     * @return 反转后的字符串
     */
    public static String reverseString(String s) {
        if (s == null || s.length() <= 1) return s;
        
        char[] chars = s.toCharArray();
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    public static void main(String[] args) {
        // 测试示例1：盛最多水的容器
        int[] height = {1,8,6,2,5,4,8,3,7};
        System.out.println("最大盛水量: " + maxArea(height));  // 输出49
        
        // 测试示例2：三数之和
        int[] nums1 = {-1,0,1,2,-1,-4};
        System.out.println("三数之和为0的组合: " + threeSum(nums1));  // 输出[[-1,-1,2],[-1,0,1]]
        
        // 测试示例3：字符串转换
        String str = "hello";
        System.out.println("反转后的字符串: " + reverseString(str));  // 输出"olleh"
    }
}