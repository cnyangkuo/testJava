// 添加LT190颠倒二进制位问题解决方案
public class LT190ReverseBits {
    // 题目：颠倒二进制位（Reverse Bits）
    // 难度：简单
    // 描述：颠倒给定32位无符号整数的二进制位。
    // 解法：逐位颠倒，使用位操作
    
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            // 将result左移一位，为新位腾出空间
            result <<= 1;
            // 取n的最低位
            int bit = n & 1;
            // 将bit添加到result的最低位
            result |= bit;
            // n右移一位，处理下一位
            n >>= 1;
        }
        return result;
    }
    
    public static void main(String[] args) {
        LT190ReverseBits solution = new LT190ReverseBits();
        
        // 测试案例1：输入是8位的二进制数00000001
        int n1 = 1;
        System.out.println("Test case 1: " + Integer.toBinaryString(solution.reverseBits(n1))); // 应该输出10000000
        
        // 测试案例2：输入是8位的二进制数11100100
        int n2 = 0b11100100;
        System.out.println("Test case 2: " + Integer.toBinaryString(solution.reverseBits(n2))); // 应该输出00100111
        
        // 测试案例3：输入是最大的32位整数
        int n3 = Integer.MAX_VALUE;
        System.out.println("Test case 3: " + Integer.toBinaryString(solution.reverseBits(n3))); // 应该输出所有位反转
    }
}