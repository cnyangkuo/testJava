package com.interview.lt;

import java.util.HashSet;
import java.util.Set;

/**
 * 128. 最长连续序列
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 难度: 中等
 * 标签: 数组, 哈希表
 */
public class LT128LongestConsecutiveSequence {
    // 使用哈希集合解决最长连续序列问题
    public int longestConsecutive(int[] nums) {
        // 创建一个哈希集合存储所有数字
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int longestStreak = 0;
        
        // 遍历集合中的每个数字
        for (int num : numSet) {
            // 只有当当前数字的前一个数字不在集合中时，才开始计算连续序列
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                
                // 继续查找下一个连续的数字
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }
                
                // 更新最长连续序列长度
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        
        return longestStreak;
    }

    // 测试用例
    public static void main(String[] args) {
        LT128LongestConsecutiveSequence solution = new LT128LongestConsecutiveSequence();
        
        // 测试案例1
        int[] nums1 = {100, 4, 200, 1, 3, 2};
        System.out.println("测试案例1结果: " + solution.longestConsecutive(nums1)); // 应该输出4
        
        // 测试案例2
        int[] nums2 = {0,3,7,2,5,8,4,6,0,1};
        System.out.println("测试案例2结果: " + solution.longestConsecutive(nums2)); // 应该输出9
        
        // 测试案例3
        int[] nums3 = {};
        System.out.println("测试案例3结果: " + solution.longestConsecutive(nums3)); // 应该输出0
    }
}