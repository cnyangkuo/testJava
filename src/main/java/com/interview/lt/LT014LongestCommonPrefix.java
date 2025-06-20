package com.interview.lt;

/**
 * 14. 最长公共前缀
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 难度: 简单
 * 标签: 字符串
 */
public class LT014LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        // 以第一个字符串为基准进行比较
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            
            // 比较其他所有字符串的当前字符
            for (int j = 1; j < strs.length; j++) {
                // 如果当前字符串已经结束或者字符不匹配
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        
        // 所有字符都匹配的情况
        return strs[0];
    }
    
    public static void main(String[] args) {
        LT014LongestCommonPrefix solution = new LT014LongestCommonPrefix();
        
        // 测试用例1
        String[] strs1 = {"flower", "flow", "flight"};
        String result1 = solution.longestCommonPrefix(strs1);
        System.out.println("Test 1: " + result1 + " 期望输出: fl");
        
        // 测试用例2
        String[] strs2 = {"dog", "racecar", "car"};
        String result2 = solution.longestCommonPrefix(strs2);
        System.out.println("Test 2: " + result2 + " 期望输出: \"\"");
        
        // 测试用例3
        String[] strs3 = {"a"};
        String result3 = solution.longestCommonPrefix(strs3);
        System.out.println("Test 3: " + result3 + " 期望输出: a");
    }
}