package com.practice.lt;

import java.util.*;

/**
 *
 * @author yangkuo
 * @date 2025/5/29
 * @description
 */
public class DemoAlg {
    public static void main(String[] args) {
        int[] nums = {-1,0,1,2,-1,-4};
        System.out.println(threeSum(nums));

        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(strs));

        System.out.println("findAnagrams:");
        System.out.println(findAnagrams("cbaebabacd", "abc")); // 输出: [0, 6]

        System.out.println("findAnagrams2:");
        System.out.println(findAnagrams2("cbaebabacd", "abc")); // 预期: [0, 6]

        System.out.println("minWindow:");
        System.out.println(minWindow("ADOBECODEBANC", "ABC")); // 输出: BANC

        int[] nums2 = {1,2,3,4,5,6,7};
        rotate(nums2, 3);
        System.out.println(Arrays.toString(nums2));

        // 构建链表 4,2,1,3
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);
        ListNode listNode = sortList(head);
        System.out.println("排序后的链表:");
        while (listNode != null) {
            System.out.print(listNode.val + " ");
            listNode = listNode.next;
        }
        System.out.println();

        System.out.println("wordBreakExample1:" + wordBreak("leetcode", Arrays.asList("leet", "code")));
        System.out.println("wordBreakExample2:" + wordBreak("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")));
    }

    /**
     * 单词拆分 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
     * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
     * 示例 1：
     *
     * 输入: s = "leetcode", wordDict = ["leet", "code"]
     * 输出: true
     * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
     * 示例 2：
     *
     * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
     * 输出: false
     * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
     *      注意，你可以重复使用字典中的单词。
     *
     *  为什么选择动态规划？
//         * 最优子结构：如果一个字符串 s[0...n] 可以被拆分为单词，那么它的前缀 s[0...i] 也一定可以被拆分为单词
//         * 重叠子问题：不同长度的前缀会重复计算相似的子问题
//         * 状态转移明确：可以通过较短的已知可拆分前缀推导出更长的可拆分前缀
     *
     * 为什么使用一维数组而不是二维数组？
//         * 我们使用了一维布尔型数组 dp[] 而不是二维数组，原因如下：
//         * 问题特性：只需要记录到每个位置是否可拆分，而不是需要记录具体的拆分方式
//         * 状态转移可行性：可以通过检查所有可能的前缀-后缀组合来完成状态转移
     *
     * 为什么使用嵌套 for 循环？
     * 种嵌套循环的设计基于以下考虑：
//         * 外层循环遍历字符串的每个结束位置 i（从 1 到 s.length()）
//         * 内层循环尝试所有可能的起始位置 j（从 0 到 i-1）
//         * 对于每个位置 i，检查是否存在某个 j 使得：
//             * 前缀 s[0...j] 可以拆分（dp[j] 为 true）
//             * 后缀 s[j...i] 在字典中
     *
     * 需要二维 dp 数组的场景
     * 需要定义二维 dp 数组的情况通常包括：
//         * 需要记录区间信息的问题：
//             * 回文子串：dp[i][j] 表示 s[i...j] 是否是回文
//             * 最长回文子串
//             * 不同的二叉搜索树：给定 1~n，求不同的 BST 数量
//         * 矩阵/二维网格相关问题：
//             * 不同路径
//             * 最小路径和
//             * 编辑距离
//         * 涉及两个序列的比较：
//             * 最长公共子序列
//             * 最短公共超序列
//             * 交错字符串
//         * 区间调度优化问题：
//             * 俄罗斯套娃信封问题
//             * 最佳买卖股票时机含冷冻期
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        // 使用动态规划解决单词拆分问题
        // 创建一个长度为 s.length() + 1 的动态规划数组 dp
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                // 如果当前子串可以拼接出字典中的某个单词，则更新dp[i]
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    private static ListNode sortList2(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode curNode = head.next;
        head.next = null;
        while (curNode != null) {
            ListNode nextNode = curNode.next;
            insertOneNode(dummy, curNode);
            curNode = nextNode;
        }

        return dummy.next;
    }

    private static void insertOneNode(ListNode dummy, ListNode curNode) {
        // 找到当前节点在链表中应该在的位置，然后插入curlNode
        ListNode pre = dummy;
        while (pre.next != null && pre.next.val < curNode.val) {
            pre = pre.next;
        }
        curNode.next = pre.next;
        pre.next = curNode;
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 排序链表 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     *
     * 使用归并排序算法对链表进行排序。该实现通过快慢指针分割链表，递归排序子链表，并合并有序子链表完成最终排序。
     *
     * @param head
     * @return
     */
    public static ListNode sortList(ListNode head) {
        // 空链表或单节点链表直接返回
        if (head == null || head.next == null) {
            return head;
        }

        // 使用快慢指针找到链表中点，分割为两个子链表
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;     // 指针走一步
            fast = fast.next.next;// 指针走两步
        }
        
        // 递归排序两个子链表
        ListNode rightHead = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(rightHead);
        
        // 合并两个有序链表
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (left != null && right != null) {
            if (left.val < right.val) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }
        
        // 连接剩余部分
        current.next = left != null ? left : right;
        
        return dummy.next;
    }

    /**
     * 轮转数组
     * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * 示例 1:
     *
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     *
     * @param nums
     * @param k
     */
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        if (k > n) {
            k = k % n;
        }
        if (k == 0) {
            return ;
        }
        int left = n - k;
        for (int i = 0; i < left; i++) {
            int temp = nums[i];
            nums[i] = nums[i + k];
            nums[i + k] = temp;
        }
    }

    /**
     * 最小覆盖子串
     *
     * 给定两个字符串 s 和 t，找到 s 中包含 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * 注意：
     *
     * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
     * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
     *
     * 使用两个哈希表分别记录：
     * need：目标字符串中每个字符的需求数量
     * window：当前窗口中每个字符的实际数量
     * 使用 valid 变量跟踪窗口中已满足需求的字符数
     * 当窗口满足条件时，尝试收缩左边界以找到最小解
     *
     * @param s 主字符串
     * @param t 模式字符串（需要覆盖的字符）
     * @return 最小覆盖子串
     */
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return "";

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0;
        int valid = 0;
        int len = Integer.MAX_VALUE;
        int start = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            // 判断当前窗口是否满足条件
            while (valid == need.size()) {
                // 更新最小覆盖子串
                if (right - left < len) {
                    len = right - left;
                    start = left;
                }

                // 尝试收缩窗口
                char d = s.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }


    /**
     * 使用滑动窗口查找字符串 s 中所有 p 的字母异位词的起始索引
     * @param s 主字符串
     * @param p 模式字符串
     * @return 所有异位词的起始索引
     */
    public static List<Integer> findAnagrams2(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s == null || p == null || s.length() < p.length()) return res;

        Map<Character, Integer> pMap = new HashMap<>();
        Map<Character, Integer> windowMap = new HashMap<>();

        // 初始化模式串 p 的字符统计
        for (char c : p.toCharArray()) {
            pMap.put(c, pMap.getOrDefault(c, 0) + 1);
        }

        int matched = 0;
        int required = pMap.size();
        int windowSize = p.length();

        // 初始化滑动窗口
        for (int i = 0; i < windowSize; i++) {
            char c = s.charAt(i);
            windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
        }

        // 判断初始窗口是否匹配
        for (Map.Entry<Character, Integer> entry : pMap.entrySet()) {
            if (windowMap.getOrDefault(entry.getKey(), 0).equals(entry.getValue())) {
                matched++;
            }
        }

        if (matched == required) {
            res.add(0);
        }

        // 开始滑动窗口
        for (int i = windowSize; i < s.length(); i++) {
            char leftChar = s.charAt(i - windowSize);
            char rightChar = s.charAt(i);

            // 移除左边字符
            windowMap.put(leftChar, windowMap.get(leftChar) - 1);
            if (windowMap.get(leftChar) == 0) {
                windowMap.remove(leftChar);
            }
            if (pMap.containsKey(leftChar)) {
                if (windowMap.getOrDefault(leftChar, 0) == pMap.get(leftChar) - 1) {
                    matched--;
                }
            }

            // 添加右边字符
            windowMap.put(rightChar, windowMap.getOrDefault(rightChar, 0) + 1);
            if (pMap.containsKey(rightChar)) {
                if (windowMap.get(rightChar).equals(pMap.get(rightChar))) {
                    matched++;
                }
            }

            // 检查是否匹配
            if (matched == required) {
                res.add(i - windowSize + 1);
            }
        }
        return res;
    }

    /**
     * 滑动窗口 + 双指针 + 匹配计数,  查找字符串 s 中所有 p 的字母异位词的起始索引
     *
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     *
     * 示例 1:
     *
     * 输入: s = "cbaebabacd", p = "abc"
     * 输出: [0,6]
     * 解释:
     * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
     * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
     *
     * 提示:
     * 1 <= s.length, p.length <= 3 * 104
     * s 和 p 仅包含小写字母
     *
     * 使用一个变量 match 来跟踪字符匹配情况
     * 使用 Map<Character, Integer> 支持更灵活的字符类型（如非字母）
     * 在滑动窗口时动态更新匹配状态，避免全量比较
     *
     * 优点：性能更高，适用于更大规模数据
     * 缺点：实现较复杂，理解门槛稍高
     *
     * @param s 主字符串
     * @param p 模式字符串
     * @return 所有异位词的起始索引
     */
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s == null || p == null || s.length() < p.length()) {
            return res;
        }

        // 创建两个数组，分别用于存储模式串 p 和主字符串 s 的字符统计，（可以省去排序Arrays.sort，仅需26个计数器）
        int[] pCount = new int[26];
        int[] sCount = new int[26];

        // 初始化模式串 p 的字符统计
        for (int i = 0; i < p.length(); i++) {
            pCount[p.charAt(i) - 'a']++;
        }
//        for (char c : p.toCharArray()) {
//            pCount[c - 'a']++;
//        }

        int windowSize = p.length();

        // 初始化滑动窗口的第一个窗口
        for (int i = 0; i < windowSize; i++) {
            sCount[s.charAt(i) - 'a']++;
        }

        if (Arrays.equals(pCount, sCount)) {
            res.add(0);
        }

        // 滑动窗口遍历剩余字符
        for (int i = windowSize; i < s.length(); i++) {
            // 移除窗口最左边字符
            sCount[s.charAt(i - windowSize) - 'a']--;
            // 添加窗口最右边字符
            sCount[s.charAt(i) - 'a']++;

            // 比较当前窗口与 p 的字符统计是否一致
            if (Arrays.equals(pCount, sCount)) {
                res.add(i - windowSize + 1);
            }
        }

        return res;
    }

    /**
     * 经典的 哈希 + 排序 策略来识别字母异位词；
     * 使用 HashMap 分组 按排序后的字符串作为 key，相同 key 的字符串为一组
     * 字母异位词分组
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     *
     * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
     *
     * 示例 1:
     *
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        if (strs == null || strs.length == 0) {
            return res;
        }
        // 创建一个 Map，用于存储字母异位词
        // key：排序后的字符串
        // value：原始字符串列表
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            res.add(entry.getValue());
        }
        return res;
    }

    /**
     *
     * 使用经典的 双指针法 来解决三数之和为 0 的问题
     *
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
     *
     * 注意：答案中不可以包含重复的三元组。
     *
     * 示例 1：
     *
     * 输入：nums = [-1,0,1,2,-1,-4]
     * 输出：[[-1,-1,2],[-1,0,1]]
     * 解释：
     * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
     * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
     * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
     * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
     * 注意，输出的顺序和三元组的顺序并不重要。
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return res;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            // 跳过重复的 i
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 跳过重复值
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    // 移动指针，避免死循环
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
            // 跳过 i 的重复值
            while (i < nums.length - 2 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return res;
    }
}
