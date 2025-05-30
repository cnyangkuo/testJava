package com.practice.lt;

/**
 * 位运算相关面试题集合
 * 包含3个典型的位操作问题
 */
public class BitManipulationProblems {
    /**
     * 问题1：找出只出现一次的数字（LeetCode 136）
     * 思路分析：
     * 1. 使用异或运算的性质：a ^ a = 0，a ^ 0 = a
     * 2. 遍历数组所有元素进行异或运算
     * 3. 最终结果即为只出现一次的数字
     * 
     * @param nums 输入数组
     * @return 只出现一次的数字
     */
    public static int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
    
    /**
     * 问题2：位运算实现加法（LeetCode 371）
     * 思路分析：
     * 1. 使用位运算模拟加法过程
     * 2. 异或运算得到不考虑进位的结果
     * 3. 与运算并左移一位得到进位结果
     * 4. 重复直到没有进位
     * 
     * @param a 加数1
     * @param b 加数2
     * @return 两数之和
     */
    public static int getSum(int a, int b) {
        while (b != 0) {
            int sumWithoutCarry = a ^ b;  // 异或得到不带进位的和
            int carry = (a & b) << 1;      // 与运算并左移得到进位
            a = sumWithoutCarry;
            b = carry;
        }
        return a;
    }
    
    /**
     * 问题3：比特位计数（LeetCode 338）
     * 思路分析：
     * 1. 创建结果数组保存每个数的比特位计数
     * 2. 对于每个数，使用n & (n-1)去除最低位的1
     * 3. 循环直到数变为0，统计1的个数
     * 
     * @param num 输入数字
     * @return 每个数的二进制1的个数
     */
    public static int[] countBits(int num) {
        if (num < 0) return new int[0];
        
        int[] result = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            int count = 0;
            int n = i;
            while (n != 0) {
                n = n & (n - 1);  // 去除最低位的1
                count++;
            }
            result[i] = count;
        }
        return result;
    }
    
    public static void main(String[] args) {
        // 测试示例1：找出只出现一次的数字
        int[] nums1 = {4,1,2,1,2};
        System.out.println("只出现一次的数字: " + singleNumber(nums1));  // 输出4
        
        // 测试示例2：位运算实现加法
        int a = 1;
        int b = 2;
        System.out.println("加法结果: " + getSum(a, b));  // 输出3
        
        // 测试示例3：比特位计数
        int num = 5;
        int[] bits = countBits(num);
        System.out.print("比特位计数: ");
        for (int i = 0; i <= num; i++) {
            System.out.print(bits[i] + " ");  // 输出0 1 1 2 1 2
        }
    }
}