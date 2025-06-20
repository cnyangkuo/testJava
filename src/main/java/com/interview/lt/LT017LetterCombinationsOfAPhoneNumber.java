package com.interview.lt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 17. 电话号码的字母组合
 * 给定一个仅包含数字 2-9 的字符串，返回所有可能的字母组合。
 * 难度: 中等
 * 标签: 哈希表, 字符串, 回溯算法
 */
public class LT017LetterCombinationsOfAPhoneNumber {
    private static final Map<Character, String> digitToLetters = new HashMap<>();
    
    static {
        digitToLetters.put('2', "abc");
        digitToLetters.put('3', "def");
        digitToLetters.put('4', "ghi");
        digitToLetters.put('5', "jkl");
        digitToLetters.put('6', "mno");
        digitToLetters.put('7', "pqrs");
        digitToLetters.put('8', "tuv");
        digitToLetters.put('9', "wxyz");
    }
    
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return result;
        }
        
        backtrack(result, new StringBuilder(), digits, 0);
        return result;
    }
    
    private void backtrack(List<String> result, StringBuilder current, String digits, int index) {
        // 如果已经处理完所有数字
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }
        
        char digit = digits.charAt(index);
        String letters = digitToLetters.get(digit);
        
        // 尝试当前数字对应的所有字母
        for (char letter : letters.toCharArray()) {
            current.append(letter);
            backtrack(result, current, digits, index + 1);
            current.deleteCharAt(current.length() - 1); // 回溯
        }
    }
    
    public static void main(String[] args) {
        LT017LetterCombinationsOfAPhoneNumber solution = new LT017LetterCombinationsOfAPhoneNumber();
        
        // 测试用例
        String digits = "23";
        List<String> result = solution.letterCombinations(digits);
        
        System.out.println("电话号码组合结果:");
        for (String combination : result) {
            System.out.println(combination);
        }
        // 期望输出: ad, ae, af, bd, be, bf, cd, ce, cf
    }
}