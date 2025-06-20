package com.interview.lt;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 20. 有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s，判断字符串是否有效。
 * 难度: 简单
 * 标签: 栈, 字符串
 */
public class LT020ValidParentheses {
    public boolean isValid(String s) {
        Map<Character, Character> bracketMap = new HashMap<>();
        bracketMap.put(')', '(');
        bracketMap.put('}', '{');
        bracketMap.put(']', '[');
        
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            // 如果是右括号
            if (bracketMap.containsKey(c)) {
                // 检查栈顶元素是否匹配
                if (stack.isEmpty() || stack.peek() != bracketMap.get(c)) {
                    return false;
                }
                stack.pop(); // 匹配则弹出栈顶
            } else {
                // 如果是左括号，压入栈
                stack.push(c);
            }
        }
        
        // 所有括号都匹配完成
        return stack.isEmpty();
    }
    
    public static void main(String[] args) {
        LT020ValidParentheses solution = new LT020ValidParentheses();
        
        // 测试用例1
        String s1 = "()";
        boolean result1 = solution.isValid(s1);
        System.out.println("Test 1: " + result1 + " 期望输出: true");
        
        // 测试用例2
        String s2 = "()[]{}";
        boolean result2 = solution.isValid(s2);
        System.out.println("Test 2: " + result2 + " 期望输出: true");
        
        // 测试用例3
        String s3 = "(]";
        boolean result3 = solution.isValid(s3);
        System.out.println("Test 3: " + result3 + " 期望输出: false");
    }
}