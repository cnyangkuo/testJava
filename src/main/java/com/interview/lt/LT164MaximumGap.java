// 添加LT164最大间距问题解决方案
public class LT164MaximumGap {
    // 题目：最大间距（Maximum Gap）
    // 难度：困难
    // 描述：给定一个未排序的数组，找出其元素的最大间距。
    // 解法：使用桶排序和鸽巢原理，线性时间复杂度O(n)
    
    public int maximumGap(int[] nums) {
        if (nums.length < 2) return 0;
        
        // 找到最小值和最大值
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.min(max, num);
        }
        
        int n = nums.length;
        int bucketSize = Math.max(1, (max - min) / (n - 1));
        int bucketCount = (max - min) / bucketSize + 1;
        
        // 初始化桶
        int[][] buckets = new int[bucketCount][2];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i][0] = Integer.MAX_VALUE;
            buckets[i][1] = Integer.MIN_VALUE;
        }
        
        // 放入桶中
        for (int num : nums) {
            int index = (num - min) / bucketSize;
            buckets[index][0] = Math.min(buckets[index][0], num);
            buckets[index][1] = Math.max(buckets[index][1], num);
        }
        
        // 计算最大间距
        int maxGap = 0;
        int previousMax = min;
        for (int[] bucket : buckets) {
            if (bucket[0] == Integer.MAX_VALUE && bucket[1] == Integer.MIN_VALUE) {
                // 空桶，跳过
                continue;
            }
            maxGap = Math.max(maxGap, bucket[0] - previousMax);
            previousMax = bucket[1];
        }
        
        return maxGap;
    }
    
    public static void main(String[] args) {
        LT164MaximumGap solution = new LT164MaximumGap();
        int[] nums1 = {3,6,9,1};
        System.out.println("Test case 1: " + solution.maximumGap(nums1)); // 应该输出3
        
        int[] nums2 = {10};
        System.out.println("Test case 2: " + solution.maximumGap(nums2)); // 应该输出0
    }
}