package com.practice.lt;

import java.util.*;

/**
 * 堆相关面试题集合
 * 包含3个典型的堆应用问题
 */
public class HeapProblems {
    // 链表节点定义
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    
    /**
     * 问题1：前K个高频元素（LeetCode 347）
     * 思路分析：
     * 1. 使用HashMap统计元素频率
     * 2. 使用最小堆保存K个高频元素
     * 3. 遍历Map.EntrySet，维护堆大小不超过k
     * 
     * @param nums 输入数组
     * @param k 前k个高频
     * @return 结果列表
     */
    public static int[] topKFrequent(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];
        
        // 统计频率
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // 创建最小堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (a, b) -> frequencyMap.get(a) - frequencyMap.get(b)
        );
        
        // 维护堆大小不超过k
        for (int num : frequencyMap.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // 构建结果数组
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }
        return result;
    }
    
    /**
     * 问题2：数据流中第K大的元素（LeetCode 703）
     * 思路分析：
     * 1. 使用最小堆维护前K大的元素
     * 2. 当堆大小小于k时直接添加
     * 3. 当前元素大于堆顶时替换
     * 
     * @param nums 输入数组
     * @param k 第k大
     * @return 第k大元素
     */
    public static int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) return Integer.MIN_VALUE;
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : nums) {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }
        return minHeap.peek();
    }
    
    /**
     * 问题3：合并K个有序链表（LeetCode 23）
     * 思路分析：
     * 1. 使用最小堆存储每个链表的当前节点
     * 2. 每次从堆中取出最小元素添加到结果链表
     * 3. 将该节点的下一个节点加入堆中
     * 
     * @param lists 有序链表数组
     * @return 合并后的有序链表
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
            (a, b) -> a.val - b.val
        );
        
        // 将所有链表的头节点加入堆
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            current.next = node;
            current = current.next;
            
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }
        return dummy.next;
    }
    
    public static void main(String[] args) {
        // 测试示例1：前K个高频元素
        int[] nums1 = {1,1,1,2,2,3};
        int k1 = 2;
        int[] result1 = topKFrequent(nums1, k1);
        System.out.print("前K个高频元素: ");
        for (int num : result1) {
            System.out.print(num + " ");  // 输出1 2
        }
        System.out.println();
        
        // 测试示例2：数据流中第K大元素
        int[] nums2 = {3,1,5,12,2,11,4};
        int k2 = 3;
        System.out.println("第K大元素: " + findKthLargest(nums2, k2));  // 输出5
        
        // 测试示例3：合并K个有序链表
        ListNode[] lists = new ListNode[3];
        lists[0] = new ListNode(1);
        lists[0].next = new ListNode(4);
        lists[0].next.next = new ListNode(5);
        
        lists[1] = new ListNode(1);
        lists[1].next = new ListNode(3);
        lists[1].next.next = new ListNode(4);
        
        lists[2] = new ListNode(2);
        lists[2].next = new ListNode(6);
        
        ListNode result2 = mergeKLists(lists);
        System.out.print("合并K个有序链表: ");
        while (result2 != null) {
            System.out.print(result2.val + " ");
            result2 = result2.next;
        }
        // 输出1 1 2 3 4 4 5 6
    }
}