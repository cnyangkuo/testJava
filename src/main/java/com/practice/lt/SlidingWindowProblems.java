package com.practice.lt;

import java.util.*;

/**
 * 滑动窗口相关面试题集合
 * 包含3个典型的滑动窗口问题
 */
public class SlidingWindowProblems {
    /**
     * 问题1：最长不重复子串（LeetCode 3）
     * 思路分析：
     * 1. 使用HashMap记录字符最新位置
     * 2. 维护窗口[left, right]，遇到重复字符时更新左边界
     * 3. 计算并保存最大窗口长度
     * 
     * @param s 输入字符串
     * @return 最长不重复子串长度
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLen = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            // 如果字符已出现且在窗口内，更新左边界
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                left = charIndexMap.get(currentChar) + 1;
            }
            
            charIndexMap.put(currentChar, right);  // 更新字符位置
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
    
    /**
     * 问题2：最小覆盖子串（LeetCode 76）
     * 思路分析：
     * 1. 使用两个哈希表记录目标字符串和当前窗口的字符计数
     * 2. 移动右指针扩大窗口直到覆盖所有要求字符
     * 3. 尝试收缩左指针以找到最小解
     * 
     * @param s 主字符串
     * @param t 目标字符串
     * @return 最小覆盖子串
     */
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return "";
        
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        
        int left = 0, right = 0;
        int valid = 0;
        int minLen = Integer.MAX_VALUE;
        int start = 0;
        
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            
            // 判断是否满足覆盖条件
            while (valid == need.size()) {
                // 更新最小窗口
                if (right - left < minLen) {
                    minLen = right - left;
                    start = left;
                }
                
                // 收缩窗口
                char d = s.charAt(left);
                left++;
                
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
    
    /**
     * 问题3：滑动窗口最大值（LeetCode 239）
     * 思路分析：
     * 1. 使用双端队列维护可能的最大值索引
     * 2. 队列头部始终为当前窗口最大值
     * 3. 移除超出窗口的索引和比当前值小的索引
     * 
     * @param nums 输入数组
     * @param k 窗口大小
     * @return 每个窗口的最大值
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];
        
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int index = 0;
        Deque<Integer> deque = new LinkedList<>();
        
        for (int i = 0; i < n; i++) {
            // 移除超出窗口的索引
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            
            // 移除比当前值小的索引
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            deque.offerLast(i);
            
            // 添加结果
            if (i >= k - 1) {
                result[index++] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        // 测试示例1：最长不重复子串
        String s1 = "abcabcbb";
        System.out.println("最长不重复子串长度: " + lengthOfLongestSubstring(s1));  // 输出3
        
        // 测试示例2：最小覆盖子串
        String s2 = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println("最小覆盖子串: " + minWindow(s2, t));  // 输出"BANC"
        
        // 测试示例3：滑动窗口最大值
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        int[] result = maxSlidingWindow(nums, k);
        System.out.print("滑动窗口最大值: ");
        for (int num : result) {
            System.out.print(num + " ");  // 输出3 3 5 5 6 7
        }
        System.out.println();
    }
}