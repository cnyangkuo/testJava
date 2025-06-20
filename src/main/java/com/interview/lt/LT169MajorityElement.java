package com.interview.lt;

/**
 * 169. 多数元素
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊n/2⌋ 的元素。
 * 难度: 简单
 * 标签: 数组, 哈希表, 分治算法
 */
public class LT169MajorityElement {
    // 使用摩尔投票法（Moore Voting Algorithm）
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
        
        // 测试用例1
        int[] nums1 = {3, 2, 3};
        int result1 = solution.majorityElement(nums1);
        System.out.println("Test 1: " + result1 + " 期望输出: 3");
        
        // 测试用例2
        int[] nums2 = {2, 2, 1, 1, 1, 2, 2};
        int result2 = solution.majorityElement(nums2);
        System.out.println("Test 2: " + result2 + " 期望输出: 2");
        
        // 测试用例3
        int[] nums3 = {1, 1, 2, 2, 2};
        int result3 = solution.majorityElement(nums3);
        System.out.println("Test 3: " + result3 + " 期望输出: 2");
    }
}