package com.interview.lt;

import java.util.HashMap;
import java.util.Map;

/**
 * 242. 有效的字母异位词
 * 给定两个字符串 s 和 t，当它们包含的字符及数量完全相同时返回 true。
 * 难度: 简单
 * 标签: 哈希表, 字符串
 */
public class LT242ValidAnagram {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        Map<Character, Integer> charCountMap = new HashMap<>();
        
        // 计算s中每个字符的出现次数
        for (char c : s.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }
        
        // 减去t中每个字符的出现次数
        for (char c : t.toCharArray()) {
            if (!charCountMap.containsKey(c)) {
                return false;
            }
            int count = charCountMap.get(c) - 1;
            if (count == 0) {
                charCountMap.remove(c);
            } else {
                charCountMap.put(c, count);
            }
        }
        
        return charCountMap.isEmpty();
    }
    
    public static void main(String[] args) {
        LT242ValidAnagram solution = new LT242ValidAnagram();
        
        // 测试用例1
        String s1 = "anagram";
        String t1 = "nagaram";
        boolean result1 = solution.isAnagram(s1, t1);
        System.out.println("Test 1: " + result1 + " 期望输出: true");
        
        // 测试用例2
        String s2 = "rat";
        String t2 = "car";
        boolean result2 = solution.isAnagram(s2, t2);
        System.out.println("Test 2: " + result2 + " 期望输出: false");
    }
}