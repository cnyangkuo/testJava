package com.practice.algorithm;

/**
 * @author yangkuo
 * @date 2024/4/7
 * @description
 * 判断一个问题是否可以使用分治算法（Divide-and-Conquer Algorithm）来解答，通常需要考虑以下几个关键特征：
 *
 * 1. **可分解性**：
 *    问题可以被递归地分解为若干个规模较小、结构相似的子问题。这些子问题通常与原问题具有相同的解决逻辑，只是处理的数据规模更小。
 *
 * 2. **子问题相互独立**：
 *    子问题之间不存在相互依赖关系，即求解一个子问题不需要知道其他子问题的解。这意味着可以并行地求解子问题，或者按照任意顺序求解，不影响最终结果。
 *
 * 3. **子问题解的合并性**：
 *    子问题的解可以直接合并成原问题的解，且合并过程的复杂度相对较低。通常，合并过程不会随着问题规模的增长而显著增加复杂度。
 *
 * 4. **基本情况**：
 *    存在一个或多个基本情况（base case），它们是问题分解过程中的最小单位，不能再进一步分解。基本情况可以直接求解，且其解不需要合并。
 *
 * 5. **有效划分**：
 *    分解问题时，子问题的数量不应过多，且每个子问题的规模应足够小，以至于可以直接求解或递归求解的复杂度是可接受的。理想情况下，随着问题规模的增长，子问题数量的增长应慢于问题规模的增长。
 *
 * **判断步骤：**
 *
 * 1. **检查问题结构**：
 *    确定问题是否可以被自然地划分为若干个子问题，这些子问题是否与原问题具有相同的结构和解决逻辑。
 *
 * 2. **识别子问题相互关系**：
 *    分析子问题之间是否存在依赖关系，判断求解一个子问题是否需要知道其他子问题的解。如果子问题相互独立，则分治算法更有可能适用。
 *
 * 3. **确定合并策略**：
 *    描述如何将子问题的解合并成原问题的解，评估合并过程的复杂度和可行性。如果合并过程简单且高效，分治算法更有可能是合适的解决方案。
 *
 * 4. **识别基本情况**：
 *    找出问题分解过程中的基本情况，即不能再进一步分解的最小问题单元。这些基本情况应可以直接求解，且其解不需要与其他子问题的解进行合并。
 *
 * 5. **评估分解的有效性**：
 *    考虑问题分解后的子问题数量和规模，以及求解子问题所需的资源。理想的分治算法应能有效地降低问题规模，使子问题易于求解，并且子问题数量的增长速度应慢于问题规模的增长。
 *
 * 如果以上特征均得到满足，那么问题很可能是适合使用分治算法来解决的。反之，若问题不具备这些特征，可能需要考虑其他算法策略，如动态规划、贪心算法、回溯算法、图论算法等。分治算法尤其适用于那些可以被自然地分解为多个独立子问题、子问题解易于合并且基本情况清晰的问题。
 */
public class QuickSort {

    // 快速排序的主函数
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        int len = arr.length;
        quickSort(arr, 0, len - 1);
    }

    // 快速排序的递归函数
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high); // 将数组划分为两部分
            quickSort(arr, low, pivotIndex - 1); // 对左侧子数组进行快速排序
            quickSort(arr, pivotIndex + 1, high); // 对右侧子数组进行快速排序
        }
    }

    // 划分函数，返回划分后的基准元素的索引
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // 选择最后一个元素作为基准元素
        int i = low - 1; // 指针 i 指向小于基准元素的最后一个元素

        // 遍历数组
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) { // 如果当前元素小于等于基准元素
                i++; // 指针 i 向右移动
                // 交换 arr[i] 和 arr[j]
                swap(arr, i, j);
            }
        }

        // 将基准元素放置到正确的位置上
        swap(arr, i + 1, high);

        // 返回基准元素的索引
        return i + 1;
    }
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 打印数组
    private static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        System.out.println("Original array:");
        printArray(arr);

        quickSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);
    }
}
