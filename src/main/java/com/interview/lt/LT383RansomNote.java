package com.interview.lt;

import java.util.HashMap;
import java.util.Map;

/**
 * 383. 赎金信
 * 给定一个字符串 ransomNote 和另一个字符串 magazine，判断是否能通过剪下magazine中的字符来替换ransomNote中的字符。
 * 难度: 简单
 * 标签: 哈希表, 字符串
 */
public class LT383RansomNote {
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> charCountMap = new HashMap<>();
        
        // 计算magazine中每个字符的出现次数
        for (char c : magazine.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }
        
        // 检查ransomNote中的每个字符
        for (char c : ransomNote.toCharArray()) {
            if (!charCountMap.containsKey(c)) {
                return false;
            }
            
            int count = charCountMap.get(c);
            if (count == 1) {
                charCountMap.remove(c);
            } else {
                charCountMap.put(c, count - 1);
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        LT383RansomNote solution = new LT383RansomNote();
        
        // 测试用例1
        String ransomNote1 = "a";
        String magazine1 = "b";
        boolean result1 = solution.canConstruct(ransomNote1, magazine1);
        System.out.println("Test 1: " + result1 + " 期望输出: false");
        
        // 测试用例2
        String ransomNote2 = "aa";
        String magazine2 = "ab";
        boolean result2 = solution.canConstruct(ransomNote2, magazine2);
        System.out.println("Test 2: " + result2 + " 期望输出: false");
        
        // 测试用例3
        String ransomNote3 = "aa";
        String magazine3 = "aab";
        boolean result3 = solution.canConstruct(ransomNote3, magazine3);
        System.out.println("Test 3: " + result3 + " 期望输出: true");
    }
}