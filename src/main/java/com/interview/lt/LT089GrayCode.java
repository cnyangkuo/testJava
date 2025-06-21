package com.interview.lt;

import java.util.ArrayList;
import java.util.List;

/**
 * 89. 格雷编码
 * 格雷编码是一个二进制序列，其中两个连续的元素在二进制表示下仅改变一位。
 * 给定一个非负整数 n，代表二进制位数，请返回格雷编码序列。
 * 难度: 中等
 * 标签: 数学, 回溯算法
 */
public class LT089GrayCode {
    // 使用镜像反射法生成格雷编码
    public List<Integer> grayCode(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("输入必须是非负整数");
        }
        
        List<Integer> result = new ArrayList<>();
        result.add(0); // 初始只有一个元素0
        
        for (int i = 0; i < n; i++) {
            int size = result.size();
            // 反向遍历已有结果，并在最高位添加1
            for (int j = size - 1; j >= 0; j--) {
                result.add(result.get(j) | (1 << i));
            }
        }
        
        return result;
    }

    // 测试用例
    public static void main(String[] args) {
        LT089GrayCode solution = new LT089GrayCode();
        
        // 测试案例1
        int n1 = 2;
        System.out.println("测试案例1结果:");
        List<Integer> result1 = solution.grayCode(n1);
        System.out.println(result1); // 应该输出[0, 1, 3, 2]
        
        // 测试案例2
        int n2 = 1;
        System.out.println("\n测试案例2结果:");
        List<Integer> result2 = solution.grayCode(n2);
        System.out.println(result2); // 应该输出[0, 1]
        
        // 测试案例3
        int n3 = 3;
        System.out.println("\n测试案例3结果:");
        List<Integer> result3 = solution.grayCode(n3);
        System.out.println(result3); // 可能输出[0, 1, 3, 2, 6, 7, 5, 4]
    }
}