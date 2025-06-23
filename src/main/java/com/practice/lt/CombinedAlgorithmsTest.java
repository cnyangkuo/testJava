package com.practice.lt;

import java.util.*;

/**
 * 组合算法测试类 - 包含双指针+动态规划、滑动窗口+哈希表等组合解法的典型案例
 */
public class CombinedAlgorithmsTest {
    /**
     * 测试最长有效括号（栈+动态规划解法）
     * 示例: ")()())()" → 4 ， 输入")()())()"，最长有效括号是"()()"，总长度为4
     */
    public static void testLongestValidParentheses() {
        String input = ")()())()";
        int result = longestValidParentheses(input);
        System.out.println("输入: " + input + ", 最长有效括号长度: " + result);
    }

    /**
     * 动态规划思路解析：
     * 1. 栈中保存最后一个未匹配左括号的位置
     * 2. 当遇到右括号时：
     *    - 弹出栈顶元素表示匹配一个左括号
     *    - 若栈为空，说明当前字符无法匹配，记录新基准点
     *    - 若栈非空，计算i - stack.peek()为当前有效长度
     * 3. 示例分析: ")()())()"
     * 索引: 0 1 2 3 4 5 6 7
     * 字符: ) ( ) ( ) ) ( )
//     i=0: ')', 栈初始为[-1]。弹出-1后栈空，压入0。
//     i=1: '(', 压入1。栈[0,1]
//     i=2: ')', 弹出1，栈变为[0]。此时i - stack.peek()=2-0=2 → max=2.
//     i=3: '(', 压入3. 栈[0,3]
//     i=4: ')', 弹出3，栈变为[0]. i -0=4 → max=4. 实际最长有效子串应为")()())"中的"()()"
//     i=5: ')', 弹出0 → 栈空，压入5.
//     i=6: '(', 压入6.
//     i=7: ')', 弹出6，栈为空，压入7.
     */
    private static int longestValidParentheses(String s) {
        int max = 0;
        // 使用栈来跟踪未匹配的左括号的位置。栈初始为空，压入-1作为基准。
        // 当遇到左括号时，压入其索引；
        // 遇到右括号时，弹出栈顶，如果栈为空，说明没有匹配的左括号，此时将当前索引压入栈作为新的基准
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // 添加初始基准点
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    // 当前字符无法匹配，记录新基准点位置
                    stack.push(i);
                } else {
                    max = Math.max(max, i - stack.peek());
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
            char r = s.charAt(right);
            right++;

            if (need.containsKey(r)) {
                window.put(r, window.getOrDefault(r, 0) + 1);
                if (window.get(r).equals(need.get(r))) {
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
                char l = s.charAt(left);
                left++;

                if (need.containsKey(l)) {
                    if (window.get(l).equals(need.get(l))) {
                        valid--;
                    }
                    window.put(l, window.get(l) - 1);
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}