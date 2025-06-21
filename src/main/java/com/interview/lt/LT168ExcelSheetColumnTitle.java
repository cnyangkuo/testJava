// 添加LT168Excel表列名称问题解决方案
public class LT168ExcelSheetColumnTitle {
    // 题目：Excel表列名称（Excel Sheet Column Title）
    // 难度：简单
    // 描述：给定一个正整数，返回它在Excel表中对应的列名称。
    // 解法：26进制转换，但没有0的概念，需要特殊处理
    
    public String convertToTitle(int columnNumber) {
        StringBuilder result = new StringBuilder();
        
        while (columnNumber > 0) {
            columnNumber--; // 特殊处理，因为没有0的概念
            char c = (char)('A' + (columnNumber % 26));
            result.append(c);
            columnNumber = columnNumber / 26;
        }
        
        return result.reverse().toString();
    }
    
    public static void main(String[] args) {
        LT168ExcelSheetColumnTitle solution = new LT168ExcelSheetColumnTitle();
        System.out.println("Test case 1: " + solution.convertToTitle(1)); // 应该输出"A"
        System.out.println("Test case 2: " + solution.convertToTitle(28)); // 应该输出"AB"
        System.out.println("Test case 3: " + solution.convertToTitle(701)); // 应该输出"ZY"
        System.out.println("Test case 4: " + solution.convertToTitle(2147483647)); // 测试大数
    }
}