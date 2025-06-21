package com.interview.lt;

/**
 * 136. 只出现一次的数字
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。
 * 找出那个只出现了一次的元素。
 * 难度: 简单
 * 标签: 位运算
 */
public class LT136SingleNumber {
    // 使用异或运算解决只出现一次的数字问题
    public int singleNumber(int[] nums) {
        int result = 0;
        
        // 异或运算性质：
        // 1. a ^ a = 0
        // 2. a ^ 0 = a
        // 3. a ^ b = b ^ a
        // 因此所有出现两次的元素都会相互抵消，只剩下单个出现的元素
        for (int num : nums) {
            result ^= num;
        }
        
        return result;
    }

    // 测试用例
    public static void main(String[] args) {
        LT136SingleNumber solution = new LT136SingleNumber();
        
        // 测试案例1
        int[] nums1 = {2, 2, 1};
        System.out.println("测试案例1结果: " + solution.singleNumber(nums1)); // 应该输出1
        
        // 测试案例2
        int[] nums2 = {4, 1, 2, 1, 2};
        System.out.println("测试案例2结果: " + solution.singleNumber(nums2)); // 应该输出4
        
        // 测试案例3
        int[] nums3 = {3, 5, 3};
        System.out.println("测试案例3结果: " + solution.singleNumber(nums3)); // 应该输出5
    }
}