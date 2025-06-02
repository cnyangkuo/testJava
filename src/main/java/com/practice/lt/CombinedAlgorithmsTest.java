package com.practice.lt;

import java.util.*;

/**
 * 组合算法测试类 - 包含双指针+动态规划、滑动窗口+哈希表等组合解法的典型案例
 */
public class CombinedAlgorithmsTest {
    /**
     * 测试最长有效括号（双指针+动态规划解法）
     * 示例: ")()())" → 4
     */
    public static void testLongestValidParentheses() {
        String input = ")()())";
        int result = longestValidParentheses(input);
        System.out.println("输入: " + input + ", 最长有效括号长度: " + result);
    }

    /**
     * 双指针+动态规划组合解法
     */
    private static int longestValidParentheses(String s) {
        int max = 0, start = 0;
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                if (!stack.isEmpty()) {
                    stack.pop();
                    max = Math.max(max, i - (stack.isEmpty() ? -1 : stack.peek()));
                } else {
                    start = i + 1;
                }
            }
        }
        return max;
    }

    /**
     * 测试最小覆盖子串（滑动窗口+哈希表解法）
     * 示例: S="ADOBECODEBANC", T="ABC" → "BANC"
     */
    public static void testMinWindowSubstring() {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String result = minWindow(s, t);
        System.out.println("输入: S=" + s + ", T=" + t + ", 最小覆盖子串: " + result);
    }

    /**
     * 滑动窗口+哈希表组合解法
     */
    private static String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        
        int left = 0, right = 0, count = t.length();
        int minLen = Integer.MAX_VALUE, startIdx = 0;
        
        while (right < s.length()) {
            char rChar = s.charAt(right++);
            if (need.containsKey(rChar)) {
                int currentCount = need.get(rChar);
                if (currentCount-- > 0) count--;
                need.put(rChar, currentCount);
            }

            // 确保窗口内包含所有目标字符后才进行收缩
            while (count == 0 && left <= right) {  // 添加 left <= right 避免无限收缩
                if (right - left < minLen) {
                    minLen = right - left;
                    startIdx = left;
                }
                
                char lChar = s.charAt(left++);
                if (need.containsKey(lChar)) {
                    int currentCount = need.get(lChar);
                    if (currentCount++ == -1) count++;
                    need.put(lChar, currentCount);  // 替换为可变操作
                }
            }
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(startIdx, startIdx + minLen);
    }
}