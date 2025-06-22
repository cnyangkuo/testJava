package com.interview.lt;

// 添加LT187重复的DNA序列问题解决方案
import java.util.HashSet;
import java.util.Set;

public class LT187RepeatedDNASequences {
    // 题目：重复的DNA序列（Repeated DNA Sequences）
    // 难度：中等
    // 描述：所有含10个字母的子字符串，找出所有重复出现的序列。
    // 解法：使用哈希集合记录出现过的序列，时间复杂度O(n)
    
    public Set<String> findRepeatedDnaSequences(String s) {
        Set<String> seen = new HashSet<>();
        Set<String> repeated = new HashSet<>();
        
        for (int i = 0; i <= s.length() - 10; i++) {
            String substring = s.substring(i, i + 10);
            if (!seen.add(substring)) {
                repeated.add(substring);
            }
        }
        
        return repeated;
    }
    
    public static void main(String[] args) {
        LT187RepeatedDNASequences solution = new LT187RepeatedDNASequences();
        String s1 = "AAAAACCCCCAAAAACCCCCCAAAAATGTTCCCATGTTCCCA";
        System.out.println("Test case 1: " + solution.findRepeatedDnaSequences(s1)); // 应该包含"AAAAACCCCC"和"CATGCCCC"
        
        String s2 = "AAAAAAAAAAA";
        System.out.println("Test case 2: " + solution.findRepeatedDnaSequences(s2)); // 应该输出空集
    }
}