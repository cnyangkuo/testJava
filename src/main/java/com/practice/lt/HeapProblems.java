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
        Map<Integer, Integer> numFreqMap = new HashMap<>();
        for (int num : nums) {
//            numFreqMap.put(num, numFreqMap.getOrDefault(num, 0) + 1);
            numFreqMap.merge(num, 1, Integer::sum);
        }
        
        // 创建最小堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
//            (a, b) -> numFreqMap.get(a) - numFreqMap.get(b)
            Comparator.comparingInt(numFreqMap::get)
        );
        
        // 维护堆大小不超过k
//        for (int num : numFreqMap.keySet()) {
//            minHeap.offer(num);
//            if (minHeap.size() > k) {
//                minHeap.poll();
//            }
//        }
        numFreqMap.keySet().stream().forEach(num -> {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (numFreqMap.get(num) > numFreqMap.get(minHeap.peek())) {
                minHeap.poll();
                minHeap.offer(num);
            }
        });
        
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
        
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        
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
            current = node;
            
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }
        return dummy.next;
    }

    /**
     * 问题5：Dijkstra算法求单源最短路径
     * ijkstra算法是经典的单源最短路径算法，用于解决带权图中从起点到其他所有节点的最短路径问题。
     *
     * 核心原理
     * 1.贪心策略：
     * 每次从未访问的节点中，选择距离起点最近的节点进行处理（通过最小堆实现）。
     * 一旦确定某个节点的最短距离，后续不再修改该节点的距离（前提是图中没有负权边）。
     * 2.松弛操作（Relaxation）：
     * 对当前节点的邻接边进行遍历，尝试通过当前节点更新其邻居节点的最短距离。
     * 例如：若从节点u到v的边权值为w，且dist[u] + w < dist[v]，则更新dist[v] = dist[u] + w。
     * 3.最小堆优化：
     * 使用最小堆（优先队列）动态维护待处理节点的最短候选距离。
     * 每次从堆顶取出当前距离起点最近的节点，确保贪心策略的正确性。
     *
     * @param n 节点数量
     * @param edges 邻接表 [[u, v, weight]]
     * @param start 起始节点
     * @return 最短距离数组
     */
    public static int[] dijkstraShortestPath(int n, int[][] edges, int start) {
        // 构建邻接表
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w}); // 无向图
        }

        // 初始化距离数组
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // 最小堆：[distance, node]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        minHeap.offer(new int[]{0, start});

        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int d = curr[0], u = curr[1];

            if (d > dist[u]) continue; // 已处理过更短路径

            for (int[] neighbor : adj.get(u)) {
                int v = neighbor[0], w = neighbor[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    minHeap.offer(new int[]{dist[v], v});
                }
            }
        }
        return dist;
    }

    /**
     * 问题6：双堆法求解数据流中的中位数
     */
    public static class MedianFinder {
        private PriorityQueue<Integer> maxHeap; // 大根堆存较小的一半
        private PriorityQueue<Integer> minHeap; // 小根堆存较大的一半

        public MedianFinder() {
            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            // 插入元素
            if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
                maxHeap.offer(num);
            } else {
                minHeap.offer(num);
            }

            // 平衡堆大小
            if (maxHeap.size() > minHeap.size() + 1) {
                minHeap.offer(maxHeap.poll());
            } else if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }

        public double findMedian() {
            if (maxHeap.size() == minHeap.size()) {
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            } else {
                return maxHeap.peek();
            }
        }
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
        System.out.println();

        // 测试示例5：Dijkstra算法
        int n = 5;
        int[][] edges = {
            {0,1,10}, {0,2,3}, {1,2,1}, {1,3,2}, {2,3,8}, {2,4,2}, {3,4,3}
        };
        int start = 0;
        int[] shortestPath = dijkstraShortestPath(n, edges, start);
        System.out.print("Dijkstra最短路径: ");
        for (int i = 0; i < n; i++) {
            /**
             * 节点0到节点0、1、2、3、4的最短距离。
             *  - dist[0] = 0（自身）
             *  - dist[1] = 4（0→2→1，3+1=4）
             *  - dist[2] = 3（0→2）
             *  - dist[3] = 6（0→2→1→3，3+1+2=6）
             *  - dist[4] = 5（0→2→4，3+2=5）
             */
            System.out.print(shortestPath[i] + " ");  // 输出0 4 3 6 5
        }
        System.out.println();

        // 测试示例6：双堆法中位数
        MedianFinder medianFinder = new MedianFinder();
        int[] stream = {1,3,2,4,5,6,7,8,9,10};
        System.out.println("数据流中位数:");
        for (int num : stream) {
            medianFinder.addNum(num);
            System.out.print(medianFinder.findMedian() + " ");
        }
        // 输出1.0 2.0 2.0 2.5 3.0 4.0 5.0 6.0 7.0 8.0
        System.out.println();
    }
}