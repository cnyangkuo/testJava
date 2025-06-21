// 添加LT151翻转字符串里的单词问题解决方案
public class LT151ReverseWordsInAString {
    // 题目：翻转字符串里的单词（Reverse Words in a String）
    // 难度：中等
    // 描述：给定一个字符串，返回其变换后的版本，其中每个单词被翻转。
    // 解法：使用StringBuilder手动处理，时间复杂度O(n)，空间复杂度O(n)
    
    public String reverseWords(String s) {
        StringBuilder result = new StringBuilder();
        int n = s.length();
        int i = 0;
        
        while (i < n) {
            // 跳过空格
            while (i < n && s.charAt(i) == ' ') {
                i++;
            }
            
            if (i >= n) break;
            
            // 找到单词的结尾
            int j = i + 1;
            while (j < n && s.charAt(j) != ' ') {
                j++;
            }
            
            // 提取子字符串并添加到结果前
            String word = s.substring(i, j);
            
            // 如果是第一个单词，不需要加空格
            if (result.length() == 0) {
                result.append(word);
            } else {
                result.insert(0, word + " ");
            }
            
            i = j + 1; // 移动到下一个单词开始位置
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        LT151ReverseWordsInAString solution = new LT151ReverseWordsInAString();
        
        System.out.println("Test case 1: \"" + solution.reverseWords("the sky is blue") + "\""); // 应该输出"blue is sky the"
        
        System.out.println("Test case 2: \"" + solution.reverseWords("  hello world  ") + "\""); // 应该输出"world hello"
        
        System.out.println("Test case 3: \"" + solution.reverseWords("a good   example") + "\""); // 应该输出"example good a"
    }
}