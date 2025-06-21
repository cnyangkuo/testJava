package com.interview.lt;

// 添加LT139单词拆分问题解决方案
import java.util.HashSet;
import java.util.Set;

public class LT139WordBreak {
    // 题目：单词拆分（Word Break）
    // 难度：中等
    // 描述：给定一个字符串和一个词典，判断该字符串能否被拆分成一个或多个字典中的单词。
    // 解法：动态规划，时间复杂度O(n²)，空间复杂度O(n)
    
    public boolean wordBreak(String s, Set<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true; // 空字符串总是可以被拆分
        
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break; // 找到一种拆分方式即可
                }
            }
        }
        
        return dp[s.length()];
    }
    
    public static void main(String[] args) {
        LT139WordBreak solution = new LT139WordBreak();
        Set<String> wordDict1 = new HashSet<>();
        wordDict1.add("leet");
        wordDict1.add("code");
        System.out.println("Test case 1: " + solution.wordBreak("leetcode", wordDict1)); // 应该输出true
        
        Set<String> wordDict2 = new HashSet<>();
        wordDict2.add("apple");
        wordDict2.add("pen");
        wordDict2.add("app");
        System.out.println("Test case 2: " + solution.wordBreak("applepenapp", wordDict2)); // 应该输出true
        
        Set<String> wordDict3 = new HashSet<>();
        wordDict3.add("cats");
        wordDict3.add("dog");
        wordDict3.add("sand");
        wordDict3.add("and");
        wordDict3.add("cat");
        System.out.println("Test case 3: " + solution.wordBreak("catsandog", wordDict3)); // 应该输出false
    }
}