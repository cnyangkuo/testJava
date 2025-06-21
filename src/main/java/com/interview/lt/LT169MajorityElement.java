package com.interview.lt;

/**
 * 169. 多数元素
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊n/2⌋ 的元素。
 * 难度: 简单
 * 标签: 数组, 哈希表, 分治算法
 */
// 添加LT169多数元素问题解决方案
public class LT169MajorityElement {
    // 题目：多数元素（Majority Element）
    // 难度：简单
    // 描述：给定一个大小为n的数组，找到其中的多数元素，该元素出现次数大于⌊n/2⌋次。
    // 解法：摩尔投票算法，时间复杂度O(n)，空间复杂度O(1)
    
    public int majorityElement(int[] nums) {
        int count = 0;
        Integer candidate = null;
        
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        
        return candidate;
    }
    
    public static void main(String[] args) {
        LT169MajorityElement solution = new LT169MajorityElement();
        int[] nums1 = {3,2,3};
        System.out.println("Test case 1: " + solution.majorityElement(nums1)); // 应该输出3
        
        int[] nums2 = {2,2,1,1,1,2,2};
        System.out.println("Test case 2: " + solution.majorityElement(nums2)); // 应该输出2
        
        int[] nums3 = {5,5,5,5,5,1,2,3};
        System.out.println("Test case 3: " + solution.majorityElement(nums3)); // 应该输出5
    }
}