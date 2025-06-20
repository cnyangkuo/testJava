package com.interview.lt;

import java.util.HashMap;
import java.util.Map;

/**
 * 3. 无重复字符的最长子串
 * 给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
 * 难度: 中等
 * 标签: 哈希表, 字符串, 滑动窗口
 */
public class LT003LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            if (charIndexMap.containsKey(currentChar)) {
                // 更新左指针为重复字符上一次出现位置的右边
                left = Math.max(left, charIndexMap.get(currentChar) + 1);
            }
            
            // 更新当前字符的最新位置
            charIndexMap.put(currentChar, right);
            // 计算当前窗口长度并更新最大长度
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    public static void main(String[] args) {
        LT003LongestSubstringWithoutRepeatingCharacters solution = new LT003LongestSubstringWithoutRepeatingCharacters();
        
        // 测试用例1
        String s1 = "abcabcbb";
        int result1 = solution.lengthOfLongestSubstring(s1);
        System.out.println("Test 1: " + result1 + " 期望输出: 3");
        
        // 测试用例2
        String s2 = "bbbbb";
        int result2 = solution.lengthOfLongestSubstring(s2);
        System.out.println("Test 2: " + result2 + " 期望输出: 1");
        
        // 测试用例3
        String s3 = "pwwkew";
        int result3 = solution.lengthOfLongestSubstring(s3);
        System.out.println("Test 3: " + result3 + " 期望输出: 3");
    }
}