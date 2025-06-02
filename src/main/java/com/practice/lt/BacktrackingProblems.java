package com.practice.lt;

import java.util.*;

/**
 * 回溯算法的本质是递归穷举+状态重置，通过深度优先搜索尝试所有可能的选择路径，
 * 并在不满足条件时回溯到上一状态继续探索。它适合解决以下类型的问题：
     * 组合/子集问题（如组合总和、子集生成）
     * 排列问题（如全排列、字符串排列）
     * 棋盘类问题（如N皇后、数独）
     * 约束满足问题（如八皇后、迷宫路径
 *
 * 回溯算法相关面试题集合
 * 包含3个典型的回溯问题
 */
public class BacktrackingProblems {
    /**
     * 问题1：全排列（LeetCode 46）
     * 思路分析：
     * 1. 使用交换元素的方式生成所有可能的排列
     * 2. 固定第一个位置后递归处理剩余部分
     * 3. 时间复杂度O(n*n!)，空间复杂度O(n)
     *
     * 全排列问题的优化思路演变：
     * 原始嵌套循环思路：
         * 用n层for循环枚举每个位置的可能选择
         * 缺陷：代码层级随输入规模增长，无法处理动态输入
     * 递归替代循环：
         * 将"固定第k个元素"抽象为递归层级
         * 通过参数传递当前状态，避免手动编写多层循环
     * 交换优化法（当前实现）：
         * 在原数组上进行元素交换，标记已选元素
         * 时间复杂度O(n*n!)，空间复杂度O(n)
     * 
     * @param nums 输入数组
     * @return 所有排列
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) return result;
        
        backtrackPermute(result, nums, 0);
        return result;
    }
    
    private static void backtrackPermute(List<List<Integer>> result, int[] nums, int start) {
        if (start == nums.length) {
            // 找到一个排列
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }
        
        for (int i = start; i < nums.length; i++) {
            // 通过交换元素标记已选状态
            swap(nums, start, i);  // 选定当前元素
            backtrackPermute(result, nums, start + 1);
            swap(nums, start, i);  // 回溯，恢复状态
        }
    }
    
    private static void swap(int[] nums, int i, int j) {
        if (i != j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
    
    /**
     * 问题2：组合总和（LeetCode 39）
     * 思路分析：
     * 1. 对候选数组排序以便剪枝
     * 2. 每次从当前位置开始选择元素
     * 3. 如果当前元素超过目标值则剪枝
     * 
     * @param candidates 候选数字数组
     * @param target 目标值
     * @return 所有组合
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return result;
        
        Arrays.sort(candidates);
        backtrackCombination(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }
    
    private static void backtrackCombination(List<List<Integer>> result, List<Integer> tempList, 
                                            int[] candidates, int remaining, int start) {
        if (remaining < 0) return;  // 超过目标值，剪枝
        if (remaining == 0) {
            result.add(new ArrayList<>(tempList));  // 找到一个有效组合
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remaining) break;  // 超过剩余值，直接剪枝
            
            tempList.add(candidates[i]);  // 添加当前元素
            backtrackCombination(result, tempList, candidates, remaining - candidates[i], i);
            tempList.remove(tempList.size() - 1);  // 回溯
        }
    }
    
    /**
     * 问题3：N皇后问题（LeetCode 51）
     * 思路分析：
     * 1. 按行放置皇后
     * 2. 使用数组记录每行皇后的位置
     * 3. 验证列和对角线是否冲突
     * 
     * @param n 棋盘大小
     * @return 所有可能解
     */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n <= 0) return result;
        
        int[] queens = new int[n];
        boolean[] cols = new boolean[n];           // 列是否有皇后
        boolean[] diag1 = new boolean[2 * n - 1];  // 主对角线是否有皇后
        boolean[] diag2 = new boolean[2 * n - 1];  // 副对角线是否有皇后
        
        backtrackQueens(result, queens, 0, n, cols, diag1, diag2);
        return result;
    }
    
    private static void backtrackQueens(List<List<String>> result, int[] queens, int row, int n,
                                      boolean[] cols, boolean[] diag1, boolean[] diag2) {
        if (row == n) {
            // 找到一个解
            result.add(generateBoard(queens, n));
            return;
        }
        
        for (int col = 0; col < n; col++) {
            if (cols[col] || diag1[row - col + n - 1] || diag2[row + col]) continue;
            
            // 放置皇后
            queens[row] = col;
            cols[col] = true;
            diag1[row - col + n - 1] = true;
            diag2[row + col] = true;
            
            // 处理下一行
            backtrackQueens(result, queens, row + 1, n, cols, diag1, diag2);
            
            // 回溯
            cols[col] = false;
            diag1[row - col + n - 1] = false;
            diag2[row + col] = false;
        }
    }
    
    private static List<String> generateBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            char[] rowChars = new char[n];
            Arrays.fill(rowChars, '.');
            rowChars[queens[row]] = 'Q';
            board.add(new String(rowChars));
        }
        return board;
    }
    
    public static void main(String[] args) {
        // 测试示例1：全排列
        int[] nums1 = {1,2,3};
        System.out.println("全排列: " + permute(nums1));  // 输出[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
        
        // 测试示例2：组合总和
        int[] candidates = {2,3,6,7};
        int target = 7;
        System.out.println("组合总和: " + combinationSum(candidates, target));  // 输出[[2,2,3],[7]]
        
        // 测试示例3：N皇后
        int n = 4;
        List<List<String>> result = solveNQueens(n);
        System.out.println("N皇后解法数量: " + result.size());  // 输出2
        for (List<String> solution : result) {
            System.out.println("--- 解法 ---");
            for (String row : solution) {
                System.out.println(row);
            }
        }
    }
}