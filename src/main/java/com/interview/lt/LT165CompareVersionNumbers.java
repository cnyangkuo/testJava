// 添加LT165比较版本号问题解决方案
public class LT165CompareVersionNumbers {
    // 题目：比较版本号（Compare Version Numbers）
    // 难度：中等
    // 描述：比较两个版本号，如果version1 > version2返回1，如果version1 < version2返回-1，否则返回0。
    // 解法：字符串分割+双指针，时间复杂度O(n+m)
    
    public int compareVersion(String version1, String version2) {
        int i = 0, j = 0;
        int n = version1.length(), m = version2.length();
        
        while (i < n || j < m) {
            int num1 = 0;
            while (i < n && version1.charAt(i) != '.') {
                num1 = num1 * 10 + (version1.charAt(i) - '0');
                i++;
            }
            
            int num2 = 0;
            while (j < m && version2.charAt(j) != '.') {
                num2 = num2 * 10 + (version2.charAt(j) - '0');
                j++;
            }
            
            if (num1 > num2) {
                return 1;
            } else if (num1 < num2) {
                return -1;
            }
            
            // 移动到下一个数字
            i++;
            j++;
        }
        
        return 0;
    }
    
    public static void main(String[] args) {
        LT165CompareVersionNumbers solution = new LT165CompareVersionNumbers();
        System.out.println("Test case 1: " + solution.compareVersion("0.1", "1.1")); // 应该输出-1
        System.out.println("Test case 2: " + solution.compareVersion("1.01", "1.001")); // 应该输出0
        System.out.println("Test case 3: " + solution.compareVersion("1.0", "1.0.0")); // 应该输出0
        System.out.println("Test case 4: " + solution.compareVersion("1.0.1", "1")); // 应该输出1
    }
}