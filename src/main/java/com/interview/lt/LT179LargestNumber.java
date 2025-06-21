// 添加LT179最大数问题解决方案
import java.util.Arrays;
import java.util.Comparator;

public class LT179LargestNumber {
    // 题目：最大数（Largest Number）
    // 难度：中等
    // 描述：给定一组非负整数，重新排列它们的顺序组成最大的数字。
    // 解法：自定义排序规则，比较s1+s2和s2+s1的大小
    
    public String largestNumber(int[] nums) {
        // 将整数数组转换为字符串数组
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        
        // 自定义排序规则
        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                // 比较a+b和b+a的大小
                return (b + a).compareTo(a + b);
            }
        });
        
        // 如果最大的数是0，则整个结果也是0
        if (strs[0].equals("0")) {
            return "0";
        }
        
        // 构建结果字符串
        StringBuilder result = new StringBuilder();
        for (String str : strs) {
            result.append(str);
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        LT179LargestNumber solution = new LT179LargestNumber();
        int[] nums1 = {10, 2};
        System.out.println("Test case 1: " + solution.largestNumber(nums1)); // 应该输出"210"
        
        int[] nums2 = {3, 30, 34, 5, 9};
        System.out.println("Test case 2: " + solution.largestNumber(nums2)); // 应该输出"9534330"
        
        int[] nums3 = {0, 0};
        System.out.println("Test case 3: " + solution.largestNumber(nums3)); // 应该输出"0"
    }
}