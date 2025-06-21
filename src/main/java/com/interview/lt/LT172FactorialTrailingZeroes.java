// 添加LT172阶乘后的零问题解决方案
public class LT172FactorialTrailingZeroes {
    // 题目：阶乘后的零（Factorial Trailing Zeroes）
    // 难度：简单
    // 描述：给定一个整数n，返回n!结果中的后缀零的数量。
    // 解法：计算5的因子数量，时间复杂度O(log n)
    
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }
    
    public static void main(String[] args) {
        LT172FactorialTrailingZeroes solution = new LT172FactorialTrailingZeroes();
        System.out.println("Test case 1: " + solution.trailingZeroes(3)); // 应该输出0
        System.out.println("Test case 2: " + solution.trailingZeroes(5)); // 应该输出1
        System.out.println("Test case 3: " + solution.trailingZeroes(100)); // 应该输出24
        System.out.println("Test case 4: " + solution.trailingZeroes(1000)); // 应该输出249
    }
}