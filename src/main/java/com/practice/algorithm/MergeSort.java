package com.practice.algorithm;

import java.util.Arrays;

/**
 * 归并排序（Merge Sort）是一种基于分治策略的经典排序算法。其核心思想是将数组不断二分，
 * 直到子数组不可再分，然后将有序的子数组合并成一个有序的整体。
 * 时间复杂度O(nlogn)，空间复杂度O(n)，最差时间复杂度O(nlogn)，空间复杂度O(n)。
 * @author yangkuo
 * @date 2025/5/16
 * @description
 */
public class MergeSort {

    // 主方法：调用归并排序
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    // 归并排序递归函数
    private static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        // 找中间点，避免整数溢出
        int mid = left + (right - left) / 2;

        // 递归排序左半部分
        mergeSort(arr, left, mid);

        // 递归排序右半部分
        mergeSort(arr, mid + 1, right);

        // 合并两个有序子数组
        merge(arr, left, mid, right);
    }

    // 合并函数
    private static void merge(int[] arr, int left, int mid, int right) {
        // 创建临时数组存储合并结果
        int[] temp = new int[right - left + 1];

        int i = left;   // 左半部分起始索引
        int j = mid + 1; // 右半部分起始索引
        int k = 0;      // 临时数组索引

        // 比较左右子数组的元素，将较小的放入临时数组
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // 如果左半部分还有剩余元素
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // 如果右半部分还有剩余元素
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // 将临时数组复制回原数组的对应区间
        for (int l = 0; l < temp.length; l++) {
            arr[left + l] = temp[l];
        }
    }

    // 测试代码
    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5, 6, 13, 17, 14};
        System.out.println("排序前: " + Arrays.toString(arr));
        mergeSort(arr);
        System.out.println("排序后: " + Arrays.toString(arr));
    }
}