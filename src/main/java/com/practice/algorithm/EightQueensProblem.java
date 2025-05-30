package com.practice.algorithm;

/**
 * @author yangkuo
 * @date 2024/4/7
 * @description
 *
 * 判断一个问题是否可以使用回溯算法（Backtracking Algorithm）来解答，通常需要关注以下几个关键特征：
 *
 * 1. **可选集合**：
 *    问题可以被表述为从一个或多个可选集合中进行选择，形成一个或多个解的组合。这些集合可以是位置、数字、元素、路径等。每个选择都会影响后续的选择空间。
 *
 * 2. **约束条件**：
 *    存在一系列约束条件，决定了哪些选择是合法的。这些约束可能涉及单个选择（如某个位置只能放特定类型的元素），也可能涉及多个选择之间的关系（如两个元素不能同时处于某种特定状态）。合法的选择组合构成了解空间。
 *
 * 3. **目标状态**：
 *    问题有一个明确的目标状态或目标条件，即所求解应满足的具体要求。目标状态可以是找到一个满足所有约束条件的解，或者是找到满足特定优化目标（如最大值、最小值、特定属性等）的解。
 *
 * 4. **可撤销性**：
 *    作出一个选择后，如果后续发现该选择导致无法达到目标状态或违反了某个约束条件，应当能够撤销该选择并回到之前的状态，尝试其他可能的选择。这通常通过递归调用和回溯返回来实现。
 *
 * 5. **穷举搜索**：
 *    问题的解可以通过穷举所有可能的选择组合来找到，尽管实际应用中常常会结合剪枝策略来避免不必要的搜索，即在搜索过程中遇到明显不可能导致目标状态的选择路径时提前终止。
 *
 * **判断步骤：**
 *
 * 1. **识别可选集合和选择过程**：
 *    确定问题中有哪些可供选择的对象或选项，以及选择的过程是如何进行的。这可能涉及排列、组合、路径选择等问题。
 *
 * 2. **分析约束条件**：
 *    列出所有限制选择的规则或条件，理解它们如何影响解的合法性。这些条件可能涉及到单个选择项，也可能涉及到多个选择项之间的相互关系。
 *
 * 3. **明确目标状态**：
 *    描述所求解应满足的具体条件或目标函数，判断是否可以通过一系列选择达到这个状态。
 *
 * 4. **检查可撤销性**：
 *    确认在搜索过程中，是否能够在作出一个选择后，如果发现该选择不合适，能够恢复到之前的状态并尝试其他选择。这通常意味着问题可以通过递归来描述。
 *
 * 5. **评估搜索空间**：
 *    考虑问题的搜索空间大小，以及是否有有效的剪枝策略可以减少不必要的搜索。如果搜索空间过大但没有有效的剪枝手段，可能需要考虑其他算法。
 *
 * 如果以上特征均得到满足，那么问题很可能是适合使用回溯算法来解决的。反之，若问题不具备这些特征，可能需要考虑其他算法策略，如动态规划、贪心算法、分治算法、图论算法等。回溯算法尤其适用于那些需要探索多种可能性、存在多种约束条件且目标状态明确的问题。
 *
 */
public class EightQueensProblem {

    private static final int N = 8; // 棋盘大小
    private static int solutionCount = 0; // 解法计数器

    // 打印棋盘
    private static void printSolution(int[][] board) {
        solutionCount++;
        System.out.println("Solution " + solutionCount + ":");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // 检查在位置(row, col)是否可以放置皇后
    private static boolean isSafe(int[][] board, int row, int col) {
        // 检查同一列中是否已经有皇后
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) {
                return false;
            }
        }

        // 检查左上方斜线是否有皇后
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // 检查右上方斜线是否有皇后
        for (int i = row, j = col; i >= 0 && j < N; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    // 递归函数来解决八皇后问题
    private static void solveNQueensUtil(int[][] board, int row) {
        if (row == N) {
            // 所有皇后已经放置完成
            printSolution(board);
            return;
        }

        // 尝试在当前行的每个位置放置皇后
        for (int i = 0; i < N; i++) {
            if (isSafe(board, row, i)) {
                // 如果位置安全，放置皇后并递归解决下一行
                board[row][i] = 1;
                solveNQueensUtil(board, row + 1);
                // 回溯，尝试下一个位置
                board[row][i] = 0;
            }
        }
    }

    // 解决八皇后问题的主函数
    public static void solveNQueens() {
        int[][] board = new int[N][N];

        // 初始化棋盘
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = 0;
            }
        }

        solveNQueensUtil(board, 0);
        System.out.println("Total solutions: " + solutionCount);
    }

    public static void main(String[] args) {
        solveNQueens();
    }
}
