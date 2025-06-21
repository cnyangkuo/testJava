// 添加LT150逆波兰表达式求值问题解决方案
import java.util.Stack;

public class LT150EvaluateReversePolishNotation {
    // 题目：逆波兰表达式求值（Evaluate Reverse Polish Notation）
    // 难度：中等
    // 描述：给定一个逆波兰表达式，求其数学表达式的值。
    // 解法：使用栈处理运算符和操作数，时间复杂度O(n)，空间复杂度O(n)
    
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
            if (isOperator(token)) {
                // 弹出两个操作数
                int b = stack.pop();
                int a = stack.pop();
                
                // 执行运算
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                }
            } else {
                // 压入操作数
                stack.push(Integer.parseInt(token));
            }
        }
        
        // 结果在栈顶
        return stack.peek();
    }
    
    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }
    
    public static void main(String[] args) {
        LT150EvaluateReversePolishNotation solution = new LT150EvaluateReversePolishNotation();
        
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        System.out.println("Test case 1: " + solution.evalRPN(tokens1)); // 应该输出9
        
        String[] tokens2 = {"4", "13", "5", "/", "+"};
        System.out.println("Test case 2: " + solution.evalRPN(tokens2)); // 应该输出6
        
        String[] tokens3 = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println("Test case 3: " + solution.evalRPN(tokens3)); // 应该输出22
    }
}