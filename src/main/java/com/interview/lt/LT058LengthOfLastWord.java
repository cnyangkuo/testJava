package com.interview.lt;

/**
 * 58. 最后一个单词的长度
 * 给定一个字符串 s ，返回 s 中最后一个单词的长度。
 * 难度: 简单
 * 标签: 字符串
 */
public class LT058LengthOfLastWord {
    public int lengthOfLastWord(String s) {
        // 去除字符串末尾的空格
        int i = s.length() - 1;
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }
        
        // 如果字符串全是空格，返回0
        if (i < 0) {
            return 0;
        }
        
        // 计算最后一个单词的长度
        int length = 0;
        while (i >= 0 && s.charAt(i) != ' ') {
            length++;
            i--;
        }
        
        return length;
    }
    
    public static void main(String[] args) {
        LT058LengthOfLastWord solution = new LT058LengthOfLastWord();
        
        // 测试用例1
        String s1 = "Hello World";
        int result1 = solution.lengthOfLastWord(s1);
        System.out.println("Test 1: " + result1 + " 期望输出: 5");
        
        // 测试用例2
        String s2 = "   fly me   to   the moon   ";
        int result2 = solution.lengthOfLastWord(s2);
        System.out.println("Test 2: " + result2 + " 期望输出: 4");
        
        // 测试用例3
        String s3 = "luffy is still joyboy";
        int result3 = solution.lengthOfLastWord(s3);
        System.out.println("Test 3: " + result3 + " 期望输出: 6");
    }
}