package com.interview.lt;

/**
 * 137. 只出现一次的数字 II
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现三次。
 * 找出那个只出现了一次的元素。
 * 难度: 中等
 * 标签: 位运算
 */
public class LT137SingleNumberII {
    // 使用位运算解决只出现一次的数字 II问题
    public int singleNumber(int[] nums) {
        int ones = 0; // 记录每一位上出现1的次数模3余1的情况
        int twos = 0; // 记录每一位上出现1的次数模3余2的情况
        
        for (int num : nums) {
            // 第一次出现时，ones变为0的地方变为1
            // 如果twos对应位为1，则ones不能变为1
            ones = (ones ^ num) & ~twos;
            
            // 第一次出现时，twos变为0的地方变为1
            // 如果ones对应位为1，则twos不能变为1
            twos = (twos ^ num) & ~ones;
        }
        
        return ones;
    }

    // 测试用例
    public static void main(String[] args) {
        LT137SingleNumberII solution = new LT137SingleNumberII();
        
        // 测试案例1
        int[] nums1 = {2, 2, 3, 2};
        System.out.println("测试案例1结果: " + solution.singleNumber(nums1)); // 应该输出3
        
        // 测试案例2
        int[] nums2 = {0, 1, 0, 1, 0, 1, 99};
        System.out.println("测试案例2结果: " + solution.singleNumber(nums2)); // 应该输出99
        
        // 测试案例3
        int[] nums3 = {4, 4, 4};
        System.out.println("测试案例3结果: " + solution.singleNumber(nums3)); // 应该输出0
    }
}