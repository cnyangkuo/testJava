// 添加LT166分数到循环小数问题解决方案
import java.util.HashMap;
import java.util.Map;

public class LT166FractionToRecurringDecimal {
    // 题目：分数到循环小数（Fraction to Recurring Decimal）
    // 难度：中等
    // 描述：给定一个分数，转换为字符串形式，包括整数部分和小数部分，其中循环小数需要用括号括起来。
    // 解法：模拟除法过程，使用哈希表记录余数出现的位置
    
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        StringBuilder result = new StringBuilder();
        
        // 处理符号
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }
        
        // 使用long避免溢出
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);
        
        // 整数部分
        result.append(num / den);
        num %= den;
        
        if (num == 0) return result.toString();
        
        // 小数部分
        result.append(".");
        Map<Long, Integer> map = new HashMap<>();
        
        while (num != 0) {
            map.put(num, result.length());
            num *= 10;
            result.append(num / den);
            num %= den;
            
            if (map.containsKey(num)) {
                // 发现循环节
                int index = map.get(num);
                result.insert(index, "(");
                result.append(")");
                break;
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        LT166FractionToRecurringDecimal solution = new LT166FractionToRecurringDecimal();
        System.out.println("Test case 1: " + solution.fractionToDecimal(1, 2)); // 应该输出"0.5"
        System.out.println("Test case 2: " + solution.fractionToDecimal(2, 1)); // 应该输出"2"
        System.out.println("Test case 3: " + solution.fractionToDecimal(4, 333)); // 应该输出"0.(012)"
        System.out.println("Test case 4: " + solution.fractionToDecimal(-50, 8)); // 应该输出"-6.25"
    }
}