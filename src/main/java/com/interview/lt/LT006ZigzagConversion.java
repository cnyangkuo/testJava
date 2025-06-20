package com.interview.lt;

/**
 * 6. Z 字形变换
 * 将一个给定字符串根据给定的行数，以Z字形进行排列。
 * 难度: 中等
 * 标签: 字符串
 */
public class LT006ZigzagConversion {
    public String convert(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        
        int currentRow = 0;
        boolean goingDown = false;
        
        for (char c : s.toCharArray()) {
            rows[currentRow].append(c);
            
            // 判断是否需要改变方向
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            
            currentRow += goingDown ? 1 : -1;
        }
        
        // 合并所有行
        for (int i = 1; i < numRows; i++) {
            rows[0].append(rows[i]);
        }
        
        return rows[0].toString();
    }
    
    public static void main(String[] args) {
        LT006ZigzagConversion solution = new LT006ZigzagConversion();
        
        // 测试用例1
        String s1 = "PAYPALISHIRING";
        int numRows1 = 3;
        String result1 = solution.convert(s1, numRows1);
        System.out.println("Test 1: " + result1 + " 期望输出: PAHNAPLSIIGYIR");
        
        // 测试用例2
        String s2 = "LEETCODEISHIRING";
        int numRows2 = 4;
        String result2 = solution.convert(s2, numRows2);
        System.out.println("Test 2: " + result2 + " 期望输出: LIESRETOCDSHIAEG");
        
        // 测试用例3
        String s3 = "A";
        int numRows3 = 1;
        String result3 = solution.convert(s3, numRows3);
        System.out.println("Test 3: " + result3 + " 期望输出: A");
    }
}