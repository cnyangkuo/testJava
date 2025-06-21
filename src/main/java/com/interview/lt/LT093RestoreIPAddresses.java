package com.interview.lt;

import java.util.ArrayList;
import java.util.List;

/**
 * 93. 复原 IP 地址
 * 给定一个只包含数字的字符串，返回所有可能得到的有效IP地址数量。
 * 有效的IP地址由四个8位（0-255）的数字组成，用'.'分隔。
 * 难度: 中等
 * 标签: 字符串, 回溯算法
 */
public class LT093RestoreIPAddresses {
    // 使用回溯算法生成所有可能的有效IP地址
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 0 || s.length() > 12) { // 长度超过12肯定不能组成有效IP
            return result;
        }
        
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }

    // 回溯方法生成有效IP地址
    private void backtrack(String s, int start, List<String> path, List<String> result) {
        // 如果已经找到4个部分且到达字符串末尾，则添加到结果
        if (path.size() == 4 && start == s.length()) {
            result.add(String.join(".", path));
            return;
        }
        
        // 如果已经有4个部分但还没遍历完字符串，或者超出字符串长度但还没找到4个部分，直接返回
        if (path.size() == 4 || start >= s.length()) {
            return;
        }
        
        // 尝试1到3位的子串
        for (int i = 1; i <= 3; i++) {
            if (start + i > s.length()) {
                break; // 超出字符串范围则跳出循环
            }
            
            String part = s.substring(start, start + i);
            
            // 如果部分有效
            if (isValid(part)) {
                path.add(part); // 添加当前部分
                backtrack(s, start + i, path, result); // 递归处理剩余部分
                path.remove(path.size() - 1); // 回溯，移除最后一个部分
            }
        }
    }

    // 判断IP部分是否有效
    private boolean isValid(String part) {
        // 如果以0开头且不是单个0，无效
        if (part.length() > 1 && part.startsWith("0")) {
            return false;
        }
        
        // 如果长度大于3或值超过255，无效
        if (part.length() > 3 || Integer.parseInt(part) > 255) {
            return false;
        }
        
        return true;
    }

    // 测试用例
    public static void main(String[] args) {
        LT093RestoreIPAddresses solution = new LT093RestoreIPAddresses();
        
        // 测试案例1
        String s1 = "25525511135";
        System.out.println("测试案例1结果:");
        List<String> result1 = solution.restoreIpAddresses(s1);
        for (String ip : result1) {
            System.out.println(ip);
        }
        // 应该输出:
        // 255.255.111.35
        // 255.25.511.135
        // 25.525.511.35
        
        // 测试案例2
        String s2 = "00100110";
        System.out.println("\n测试案例2结果:");
        List<String> result2 = solution.restoreIpAddresses(s2);
        for (String ip : result2) {
            System.out.println(ip);
        }
        // 应该输出:
        // 0.0.100.110
        // 0.0.10.0110
        // 0.0.1.00110
        // 0.01.00.110
        // 0.01.0.0110
        // 0.0.01.0110
        // 0.0.0.1010
        // ...其他可能组合
        
        // 测试案例3
        String s3 = "1111";
        System.out.println("\n测试案例3结果:");
        List<String> result3 = solution.restoreIpAddresses(s3);
        for (String ip : result3) {
            System.out.println(ip);
        }
        // 应该输出:
        // 1.1.1.1
    }
}