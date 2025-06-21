package com.interview.lt;

/**
 * 130. 被围绕的区域
 * 给定一个二维矩阵 board ，其中元素为 'X' 或 'O' ，找到所有被 'X' 围绕的 'O' 并将其替换为 'X' 。
 * 难度: 中等
 * 标签: 深度优先搜索, 广度优先搜索
 */
public class LT130SurroundedRegions {
    // 定义四个方向：上、右、下、左
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    // 使用深度优先搜索解决被围绕的区域问题
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        
        int rows = board.length;
        int cols = board[0].length;
        
        // 处理第一列和最后一列的'O'
        for (int i = 0; i < rows; i++) {
            if (board[i][0] == 'O') {
                dfs(board, i, 0);
            }
            if (board[i][cols - 1] == 'O') {
                dfs(board, i, cols - 1);
            }
        }
        
        // 处理第一行和最后一行的'O'
        for (int j = 1; j < cols - 1; j++) {
            if (board[0][j] == 'O') {
                dfs(board, 0, j);
            }
            if (board[rows - 1][j] == 'O') {
                dfs(board, rows - 1, j);
            }
        }
        
        // 将所有未被访问的'O'替换为'X'
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == '#') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    // 深度优先搜索标记与边界'O'相连的'O'
    private void dfs(char[][] board, int row, int col) {
        board[row][col] = '#'; // 标记为已访问
        
        // 探索四个方向
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            
            // 如果位置有效且值为'O'，继续DFS
            if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length && board[newRow][newCol] == 'O') {
                dfs(board, newRow, newCol);
            }
        }
    }

    // 测试用例
    public static void main(String[] args) {
        LT130SurroundedRegions solution = new LT130SurroundedRegions();
        
        // 创建测试用例
        char[][] board = {
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X'},
            {'X', 'X', 'O', 'X'},
            {'X', 'O', 'X', 'X'}
        };
        
        System.out.println("原始矩阵:");
        solution.printBoard(board);
        
        // 解决问题
        solution.solve(board);
        
        System.out.println("处理后矩阵:");
        solution.printBoard(board);
    }
    
    // 打印二维矩阵
    private void printBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println(new String(row));
        }
    }
}