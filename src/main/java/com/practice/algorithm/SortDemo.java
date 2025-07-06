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
public class SortDemo {

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5, 6, 13, 17, 14};
        // copy一份到arr2、arr3
        int[] arr2 = arr.clone();
        int[] arr3 = arr.clone();
        int[] arr4 = arr.clone();
        System.out.println("Original array:");
        printArray(arr);

        // 选择排序 迭代方式直接从未排序列表中选择最小元素，放在已排序列表末尾
        // 特点: 时间复杂度始终O(n^2)，属于稳定排序算法，空间复杂度O(1)
        selectionSort(arr4);
        System.out.println("Selection Sorted array:");
        printArray(arr4);

        // 快排 属于分治算法
        // 特点: 时间复杂度O(nlogn)，最差时间复杂度O(n^2)，
        // 属于不稳定排序算法（因为其排序过程中可能打乱相同元素的相对顺序），
        // 空间复杂度O(1)（原地排序，不考虑递归栈），若考虑递归栈为 O(log n)
        // 分解：将数组划分为两部分（partition函数）, 左边比基准值小，右边比基准值大
        // 解决：递归对子数组排序（quickSort函数）
        // 合并：无需额外操作（划分过程已保证有序）
        quickSort(arr);

        System.out.println("Quick Sorted array:");
        printArray(arr);

        // 归并排序 属于分治算法
        // 特点: 时间复杂度始终O(nlogn)，属于稳定排序算法，但空间复杂度O(n)
        // 分解：将数组二分（mergeSort函数）
        // 解决：递归排序子数组（mergeSort函数）
        // 合并：将有序子数组合并（merge函数）
        mergeSort(arr2);
        System.out.println("Merge Sorted array:");
        printArray(arr2);

        // 堆排序 利用堆性质（父节点总数大于或小于子节点）
        // 特点: 时间复杂度始终O(nlogn)，属于不稳定排序算法，空间复杂度O(1)
        // 不稳定原因（虽然结果有序，但相同元素的位置可能发生过交换）
        heapSort(arr3);
        System.out.println("Heap Sorted array:");
        printArray(arr3);

        // 插入排序
        // 特点: 时间复杂度O(n^2)，属于稳定排序算法，空间复杂度O(1)
        int[] arr5 = {5, 2, 9, 1, 5, 6, 13, 17, 14};
        insertionSort(arr5);
        System.out.println("Insertion Sorted array:");
        printArray(arr5);
    }

    /**
     * 选择排序, 核心思想是 repeatedly 选择最小的元素，将它与当前位置的元素交换位置。
     * 时间复杂度O(n^2)，属于稳定排序算法，空间复杂度O(1)
     * @param arr
     * @return
     **/
    private static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    /**
     * 快速排序，核心思想是分治；选择一个基准元素，将数组划分为两部分，一部分比基准元素小，另一部分比基准元素大，然后递归地对这两部分进行快速排序。
     * 时间复杂度O(nlogn)，最差时间复杂度O(n^2)，属于不稳定排序算法，空间复杂度O(1)
     * @param arr
     */
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
        int pivot = arr[high]; // 选择一个元素作为基准元素（比基准元素小的元素都在其左边，比基准元素大的元素都在其右边）
        int i = low - 1; // 指针 i 指向小于基准元素的最后一个元素，初始化为 low-1

        // 遍历数组，双指针移动
        for (int j = low; j < high - 1; j++) {
            if (arr[j] <= pivot) {  // 如果当前元素<=基准元素，交换两个元素 把当前元素移到左边
                i++;             // 指针 i 向右移动，指向>基准值的第一个元素位置
                swap(arr, i, j); // 交换后，i 指向左边最后一个<=基准值的元素
            } else {
                // 当前元素大于基准元素，不需要交换, 移动指针 j
            }
        }

        // 将基准元素放置到正确的位置上，确保比基准元素小的元素都在其左边，比基准元素大的元素都在其右边
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

    /**
     * 归并排序（Merge Sort）是一种基于分治策略的经典排序算法。其核心思想是将数组不断二分，
     * 直到子数组不可再分，然后将有序的子数组合并成一个有序的整体。
     * 时间复杂度O(nlogn)，最差时间复杂度O(nlogn)，空间复杂度O(n)。
     */
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

    /**
     * 堆排序, 核心思想是构建一个最大堆，然后逐步将堆顶元素交换到末尾，最终得到一个有序的数组。
     * 时间复杂度O(nlogn)，最差时间复杂度O(nlogn)，属于不稳定排序算法（因为父子节点交换可能改变相同元素的相对顺序），空间复杂度O(1)
     * @param arr
     */
    private static void heapSort(int[] arr) {
        // 构建最大堆
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // 堆排序,  从最后一个非叶子节点开始, 逐个将最大元素交换到末尾
        for (int i = n - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int i, int j) {
        int largest = j;
        int left = 2 * j + 1;
        int right = 2 * j + 2;
        if (left < i && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < i && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != j) {
            swap(arr, j, largest);
            heapify(arr, i, largest);
        }
    }

    /**
     * 插入排序，核心思想是将每个元素插入到已排序序列中的正确位置
     * 时间复杂度O(n²)，最差时间复杂度O(n²)，属于稳定排序算法（仅当元素严格大于时才移动），空间复杂度O(1)
     * @param arr
     */
    private static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            // 仅当元素严格大于时才后移，保证稳定性
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

}
