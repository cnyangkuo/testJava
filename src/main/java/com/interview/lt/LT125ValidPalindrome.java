package com.interview.lt;

/**
 * 125. 验证回文串
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 * 难度: 简单
 * 标签: 双指针, 字符串
 */
public class LT125ValidPalindrome {
    // 使用双指针法判断回文串
    public boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }
        
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            // 跳过非字母数字字符
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            // 比较两个字符（忽略大小写）
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }

    // 测试用例
    public static void main(String[] args) {
        LT125ValidPalindrome solution = new LT125ValidPalindrome();
        
        // 测试案例1
        String s1 = "A man, a plan, a canal: Panama";
        System.out.println("测试案例1结果: " + solution.isPalindrome(s1)); // 应该输出true
        
        // 测试案例2
        String s2 = "race a car";
        System.out.println("测试案例2结果: " + solution.isPalindrome(s2)); // 应该输出false
        
        // 测试案例3
        String s3 = " ";
        System.out.println("测试案例3结果: " + solution.isPalindrome(s3)); // 应该输出true
        
        // 测试案例4
        String s4 = "0P";
        System.out.println("测试案例4结果: " + solution.isPalindrome(s4)); // 应该输出false
    }
}