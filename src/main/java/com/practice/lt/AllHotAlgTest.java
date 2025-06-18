package com.practice.lt;

/**
 * 集中式测试类，运行所有10个算法类的测试用例
 * 每个算法类选取1-2个代表性测试用例进行验证
 * 判断算法类型的系统性方法：
 * 一、题型特征速查表：
     * 动态规划
         * 关键词：最大/最小值、最长/最短、路径数、子序列
         * 例：最长递增子序列、最小路径和
         * 复杂度优势：将指数级暴力解降为O(n²)
     * 双指针
         * 关键词：有序数组、两数之和、删除重复项
         * 例：三数之和、盛最多水容器
         * 复杂度优势：从O(n²)降为O(n)
     * 滑动窗口
         * 关键词：最长/最短子串、无重复字符
         * 例：最小覆盖子串、最长重复字符
         * 复杂度优势：避免O(n²)暴力枚举
     * 贪心算法
         * 关键词：最大收益、最少次数、跳跃游戏
         * 例：买卖股票、区间调度
         * 复杂度优势：O(nlogn)排序 + O(n)遍历
     * DFS/BFS
         * 关键词：路径搜索、连通区域、树结构
         * 例：岛屿数量、二叉树最大深度
         * 复杂度差异：BFS常用于最短路径，DFS内存占用低
 *
 *
 * 二、决策流程图：
     * 第一步：看问题类型
         * 最优化 → 动态规划/贪心
         * 路径搜索 → DFS/BFS
         * 子数组问题 → 滑动窗口/双指针
     * 第二步：看数据规模
         * n > 1e5 → 需要O(n)或O(nlogn)算法
         * n < 1e4 → 可接受O(n²)解法
     * 第三步：看状态依赖
         * 需要历史状态 → 动态规划
         * 无需历史状态 → 贪心/双指针
     * 第四步：验证可行性
         * 检查是否满足最优子结构（DP）
         * 检查是否满足贪心选择性质
 *
 * 另外：组合算法可能优化时间和空间复杂度，比如：
 * 1. 双指针 + 动态规划：如最长有效括号。 （O(n²) → O(n)）
 * 2. 滑动窗口 + 哈希表：如最小覆盖子串。 （O(n) + O(1)）
 * 3. 贪心 + 优先队列：如任务调度。      （O(nlogn)）
 * 4. BFS + 位运算：如状态压缩的最短路径问题。（状态压缩）
 *
 *
 * 三、经典场景对比：
 * | 场景 | 可行算法 | 时间复杂度对比 |
 * |---------------------|----------------------|-----------------------|
 * | 最长递增子序列 | DP / 二分优化 | O(n²) → O(nlogn) |
 * | 三数之和 | 双指针 / 哈希表 | O(n²) vs O(n²) |
 * | 最小路径和 | DP / BFS | O(n²) 状态压缩 vs O(2^n)暴力 |
 * | 字符串排列检测 | 滑动窗口 / 排序 | O(n) vs O(nlogn) |
 *
 *
 * 四、实践技巧：
     * 观察样例输入输出
         * 若存在多阶段决策痕迹 → 动态规划
         * 若输出呈现局部有序性 → 贪心
     * 画图辅助分析
         * 树形结构 → DFS/BFS
         * 矩阵问题 → DP二维数组
     * 极端情况验证
         * 当n=1时的边界条件适合哪些算法？
         * 当数据已有序时哪种算法表现更好？
 * 示例分析：跳水板问题
     * 暴力解：生成所有组合 → O(2^n)
     * 动态规划：记录中间结果 → O(n)
     * 数学推导：直接计算 → O(1) → 最终选择数学解法，但需通过动态规划发现数学规律
     *
     *
 */
public class AllHotAlgTest {
    // 跳水板问题动态规划解法（组合数）
    /**
     * 动态规划解法：计算组成目标长度的不同组合数（不考虑顺序）。
     * 状态转移方程：dp[i] += dp[i - plank]，其中plank遍历所有板长且i >= plank
     * 时间复杂度：O(target * k)，k为板的数量
     * 空间复杂度：O(target)
     * @param planks 可用板长数组
     * @param target 目标长度
     * @return 组合数
     */
    public int divingBoardDP(int[] planks, int target) {
        int[] dp = new int[target + 1];
        // dp[i]表示长度为i时的组合数
        // 初始化dp[0]为1, 因为长度为0只有一种方式，即不用任何板。
        dp[0] = 1;
        for (int plank : planks) {
            // 动态规划方程应该是dp[i] = sum(dp[i - p]) for p in planks if i >= p
            for (int i = plank; i <= target; i++) {
                dp[i] += dp[i - plank];
            }
        }
        return dp[target];
    }

    // 跳水板问题数学推导解法（排列数）
    /**
     * 数学推导解法：通过排列组合公式直接计算可能的组合数。
     * 核心思想：当存在重复元素时，使用容斥原理计算不同排列数
     * 时间复杂度：O(target * k)
     * 空间复杂度：O(target)
     * @param planks 可用板长数组
     * @param target 目标长度
     * @return 排列数
     */
    public int divingBoardMath(int[] planks, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int plank : planks) {
                if (i >= plank) {
                    dp[i] += dp[i - plank];
                }
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        AllHotAlgTest solution = new AllHotAlgTest();
        // 给定k种不同长度的木板，每种无限使用，求组成总长度为n的不同组合数。
        int[] planks = {1, 2, 3};  // 示例板长
        int target = 4;            // 目标长度

        // 测试动态规划解法, 计算不考虑顺序的组合数，采用标准动态规划实现, （四种组合：1+1+1+1, 1+1+2, 1+3, 2+2）
        int dpResult = solution.divingBoardDP(planks, target);
        System.out.println("动态规划解法结果: " + dpResult);  // 应输出4

        // 测试数学推导解法, 计算排列数，采用数学推导实现, （七种排列：1+1+1+1, 1+1+2, 1+2+1, 2+1+1, 1+3, 3+1, 2+2）
        int mathResult = solution.divingBoardMath(planks, target);
        System.out.println("数学推导解法结果: " + mathResult);  // 应输出7

        System.out.println("\n===== 动态规划算法测试 =====");
        DynamicProgrammingProblems.main(args);
        
        System.out.println(" ===== 双指针+动态规划组合测试 =====");
        CombinedAlgorithmsTest.testLongestValidParentheses();  // 新增组合算法测试
        
        System.out.println(" ===== 滑动窗口+哈希表组合测试 =====");
        CombinedAlgorithmsTest.testMinWindowSubstring();  // 新增组合算法测试
        
        System.out.println("\n===== 双指针算法测试 =====");
        TwoPointersProblems.main(args);
        
        System.out.println("\n===== 位运算算法测试 =====");
        BitManipulationProblems.main(args);
        
        System.out.println("\n===== 贪心算法测试 =====");
        GreedyAlgorithmsProblems.main(args);
        
        System.out.println("\n===== 深度优先搜索测试 =====");
        DFSProblems.main(args);
        
        System.out.println("\n===== 广度优先搜索测试 =====");
        BFSProblems.main(args);
        
        System.out.println("\n===== 堆相关算法测试 =====");
        HeapProblems.main(args);
        
        System.out.println("\n===== 滑动窗口算法测试 =====");
        SlidingWindowProblems.main(args);
        
        System.out.println("\n===== 拓扑排序算法测试 =====");
        TopologicalSortProblems.main(args);
        
        System.out.println("\n===== 回溯算法测试 =====");
        BacktrackingProblems.main(args);
    }
}