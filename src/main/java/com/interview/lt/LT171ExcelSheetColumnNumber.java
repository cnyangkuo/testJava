// 添加LT171Excel表列序号问题解决方案
public class LT171ExcelSheetColumnNumber {
    // 题目：Excel表列序号（Excel Sheet Column Number）
    // 难度：简单
    // 描述：给定一个Excel表格的列名称，返回其对应的列序号。
    // 解法：26进制转换为10进制
    
    public int titleToNumber(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            result = result * 26 + (s.charAt(i) - 'A' + 1);
        }
        return result;
    }
    
    public static void main(String[] args) {
        LT171ExcelSheetColumnNumber solution = new LT171ExcelSheetColumnNumber();
        System.out.println("Test case 1: " + solution.titleToNumber("A")); // 应该输出1
        System.out.println("Test case 2: " + solution.titleToNumber("AB")); // 应该输出28
        System.out.println("Test case 3: " + solution.titleToNumber("ZY")); // 应该输出701
        System.out.println("Test case 4: " + solution.titleToNumber("AAA")); // 应该输出703
    }
}