package com.interview.lt;

/**
 * 28. 找出字符串中第一个匹配项的下标
 * 给定两个字符串 haystack 和 needle，请在 haystack 中找出 needle 第一次出现的位置。
 * 难度: 简单
 * 标签: 双指针, 字符串
 */
public class LT028FindTheIndexOfTheFirstOccurrenceInAString {
    public int strStr(String haystack, String needle) {
        // 边界情况处理
        if (needle.length() == 0) {
            return 0;
        }
        if (haystack.length() < needle.length()) {
            return -1;
        }
        
        // 使用双指针进行匹配
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            boolean match = true;
            for (int j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return i;
            }
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        LT028FindTheIndexOfTheFirstOccurrenceInAString solution = new LT028FindTheIndexOfTheFirstOccurrenceInAString();
        
        // 测试用例1
        String haystack1 = "hello";
        String needle1 = "ll";
        int result1 = solution.strStr(haystack1, needle1);
        System.out.println("Test 1: " + result1 + " 期望输出: 2");
        
        // 测试用例2
        String haystack2 = "aaaaa";
        String needle2 = "bba";
        int result2 = solution.strStr(haystack2, needle2);
        System.out.println("Test 2: " + result2 + " 期望输出: -1");
        
        // 测试用例3
        String haystack3 = "abcde";
        String needle3 = "";
        int result3 = solution.strStr(haystack3, needle3);
        System.out.println("Test 3: " + result3 + " 期望输出: 0");
    }
}