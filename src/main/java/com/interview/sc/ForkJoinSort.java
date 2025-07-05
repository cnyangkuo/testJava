package com.interview.sc;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 使用ForkJoin框架实现的降序归并排序
 * 1. 分治策略：递归拆分数组直到达到阈值
 * 2. 合并阶段：合并两个有序子数组为一个降序数组
 * 3. 线程安全：无共享可变状态，适合并行处理
 */
public class ForkJoinSort extends RecursiveAction {
    private static final int THRESHOLD = 10; // 拆分阈值
    private final int[] array;
    private final int left;
    private final int right;

    // 构造方法初始化排序参数
    public ForkJoinSort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        // 基线条件：子数组长度小于等于阈值时使用插入排序
        if (right - left <= THRESHOLD) {
            insertionSort();
            return;
        }

        // 分治阶段：递归拆分数组
        int mid = (left + right) / 2;
        ForkJoinSort leftTask = new ForkJoinSort(array, left, mid);
        ForkJoinSort rightTask = new ForkJoinSort(array, mid + 1, right);
        
        // 并行执行子任务
        invokeAll(leftTask, rightTask);
        
        // 合并阶段：将两个有序子数组合并为一个降序数组
        merge(mid);
    }

    // 插入排序实现：对小数组进行降序排序
    private void insertionSort() {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= left && array[j] < key) { // 降序比较逻辑
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    // 合并两个有序子数组
    private void merge(int mid) {
        // 创建临时数组存储左半部分
        int[] leftArray = Arrays.copyOfRange(array, left, mid + 1);
        int[] rightArray = Arrays.copyOfRange(array, mid + 1, right + 1);
        
        // 双指针合并
        int i = 0, j = 0, k = left;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] > rightArray[j]) { // 降序比较逻辑
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }
        
        // 处理剩余元素
        while (i < leftArray.length) {
            array[k++] = leftArray[i++];
        }
        while (j < rightArray.length) {
            array[k++] = rightArray[j++];
        }
    }

    // 公开排序接口
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) return;
        
        // 创建ForkJoinPool并执行任务
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ForkJoinSort(array, 0, array.length - 1));
        pool.shutdown();
    }

    // 测试用例
    public static void main(String[] args) {
        // 测试用例1：普通数组
        int[] arr1 = {5, 2, 9, 1, 5, 6};
        sort(arr1);
        System.out.println("降序排序结果1: " + Arrays.toString(arr1)); // [9,6,5,5,2,1]
        
        // 测试用例2：重复元素
        int[] arr2 = {3, 3, 3, 3};
        sort(arr2);
        System.out.println("降序排序结果2: " + Arrays.toString(arr2)); // [3,3,3,3]
        
        // 测试用例3：边界测试
        int[] arr3 = {};
        sort(arr3);
        System.out.println("降序排序结果3: " + Arrays.toString(arr3)); // []
    }
}
// 面试考点说明：
// 1. ForkJoin框架核心组件：RecursiveAction与ForkJoinPool的使用
// 2. 分治策略的递归实现与基线条件选择
// 3. 合并阶段的时间复杂度分析：O(n)
// 4. 并行度控制：阈值设置对性能的影响
// 5. 线程安全：任务间无共享可变状态的设计
// 6. 时间复杂度：T(n) = 2T(n/2) + O(n) => O(n log n)
// 7. 降序排序的关键修改点：比较操作的符号方向