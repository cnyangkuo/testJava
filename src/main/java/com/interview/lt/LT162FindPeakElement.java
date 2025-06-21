// 添加LT162寻找峰值问题解决方案
public class LT162FindPeakElement {
    // 题目：寻找峰值（Find Peak Element）
    // 难度：中等
    // 描述：峰值是指其值大于左右相邻值的元素。数组可能包含多个峰值，返回到索引即可。
    // 解法：使用二分查找，时间复杂度O(log n)
    
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > nums[mid + 1]) {
                // 峰值在左边，包括mid
                right = mid;
            } else {
                // 峰值在右边，不包括mid
                left = mid + 1;
            }
        }
        return left;
    }
    
    public static void main(String[] args) {
        LT162FindPeakElement solution = new LT162FindPeakElement();
        int[] nums1 = {1,2,3,1};
        System.out.println("Test case 1: " + solution.findPeakElement(nums1)); // 应该输出2
        
        int[] nums2 = {1,2,1,3,5,6,4};
        System.out.println("Test case 2: " + solution.findPeakElement(nums2)); // 可能输出1或5
    }
}