package com.interview.lt;

import java.util.ArrayList;
import java.util.List;

/**
 * 131. 分割回文串
 * 给定一个字符串 s ，返回 s 所有可能的回文子串分割方案。
 * 难度: 中等
 * 标签: 回溯算法, 动态规划
 */
public class LT131PalindromePartitioning {
    // 使用回溯算法解决分割回文串问题
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }
        
        // 使用动态规划预处理回文子串
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int end = 0; end < s.length(); end++) {
            for (int start = 0; start <= end; start++) {
                if (start == end) {
                    dp[start][end] = true; // 单个字符肯定是回文
                } else if (start + 1 == end) {
                    dp[start][end] = (s.charAt(start) == s.charAt(end)); // 两个字符直接比较
                } else {
                    dp[start][end] = (s.charAt(start) == s.charAt(end)) && dp[start + 1][end - 1]; // 状态转移
                }
            }
        }
        
        backtrack(s, 0, new ArrayList<>(), result, dp);
        return result;
    }

    // 回溯方法生成所有可能的回文分割
    private void backtrack(String s, int start, List<String> path, List<List<String>> result, boolean[][] dp) {
        // 如果起始位置到达字符串末尾，添加当前路径到结果
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        // 尝试所有可能的结束位置
        for (int end = start; end < s.length(); end++) {
            // 如果是回文串，则进行递归
            if (dp[start][end]) {
                path.add(s.substring(start, end + 1)); // 添加当前回文子串
                backtrack(s, end + 1, path, result, dp); // 递归处理剩余部分
                path.remove(path.size() - 1); // 回溯，移除最后一个元素
            }
        }
    }

    // 测试用例
    public static void main(String[] args) {
        LT131PalindromePartitioning solution = new LT131PalindromePartitioning();
        
        // 测试案例1
        String s1 = "aab";
        System.out.println("测试案例1结果:");
        List<List<String>> result1 = solution.partition(s1);
        for (List<String> partition : result1) {
            System.out.println(partition);
        }
        // 应该输出:
        // [a, a, b]
        // [aa, b]
        
        // 测试案例2
        String s2 = "a";
        System.out.println("\n测试案例2结果:");
        List<List<String>> result2 = solution.partition(s2);
        for (List<String> partition : result2) {
            System.out.println(partition);
        }
        // 应该输出:
        // [a]
    }
}