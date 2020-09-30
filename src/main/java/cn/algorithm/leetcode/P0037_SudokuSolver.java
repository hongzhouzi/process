package cn.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hongzhou.wei
 * @date 2020/9/15
 */
public class P0037_SudokuSolver {
    public static void main(String[] args) {
        char[][] board = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        new P0037_SudokuSolver.Solution1().solveSudoku(board);
//        new P0037_SudokuSolver.Solution().solveSudoku(board);
        System.out.println(Arrays.deepToString(board));
    }

    /**
     * 【思路】
     * 类似人的思考方式去尝试，行，列，还有 3*3 的方格内数字是 1~9 不能重复。
     * 我们尝试填充，如果发现重复了，那么擦除重新进行新一轮的尝试，直到把整个数组填充完成。
     * <p>
     * 【算法步骤】
     * 数独首先行，列，还有 3*3 的方格内数字是 1~9 不能重复。
     * 声明布尔数组，表明行列中某个数字是否被使用了， 被用过视为 true，没用过为 false。
     * 初始化布尔数组，表明哪些数字已经被使用过了。
     * 尝试去填充数组，只要行，列， 还有 3*3 的方格内 出现已经被使用过的数字，我们就不填充，否则尝试填充。
     * 如果填充失败，那么我们需要回溯。将原来尝试填充的地方改回来。
     * 递归直到数独被填充完成。
     */
    static class Solution1 {
        public void solveSudoku(char[][] board) {
            // 三个布尔数组 表明 行, 列, 还有 3*3 的方格的数字是否被使用过
            boolean[][] rowUsed = new boolean[9][10];
            boolean[][] colUsed = new boolean[9][10];
            boolean[][][] boxUsed = new boolean[3][3][10];
            // 初始化
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    int num = board[i][j] - '0';
                    if (1 <= num && num <= 9) {
                        rowUsed[i][num] = true;
                        colUsed[j][num] = true;
                        boxUsed[i / 3][j / 3][num] = true;
                    }
                }
            }
            // 递归+回溯填充数据
            recusiveSolveSudoku(board, rowUsed, colUsed, boxUsed, 0, 0);
        }

        boolean recusiveSolveSudoku(char[][] board, boolean[][] rowUsed, boolean[][] colUsed, boolean[][][] boxUsed, int row, int col) {
            // 逐行填充，每行填充完成返回true
            if (col == board[0].length) {
                col = 0;
                row++;
                if (row == board.length) {
                    return true;
                }
            }
            // 填充过程
            if (board[row][col] == '.') {
                for (int i = 1; i <= 9; i++) {
                    boolean canUsed = !(rowUsed[row][i] || colUsed[col][i] || boxUsed[row / 3][col / 3][i]);
                    if (canUsed) {
                        rowUsed[row][i] = true;
                        colUsed[col][i] = true;
                        boxUsed[row / 3][col / 3][i] = true;

                        board[row][col] = (char) ('0' + i);
                        // 填充完该位置，继续当前行的下个位置
                        if (recusiveSolveSudoku(board, rowUsed, colUsed, boxUsed, row, col + 1)) {
                            // 若全部填充完了就返回了，不需要下面的回溯
                            return true;
                        }

                        // 回溯
                        board[row][col] = '.';
                        rowUsed[row][i] = false;
                        colUsed[col][i] = false;
                        boxUsed[row / 3][col / 3][i] = false;
                    }
                }
            } else {
                // 该位置不需填充，继续当前行的下个位置
                return recusiveSolveSudoku(board, rowUsed, colUsed, boxUsed, row, col + 1);
            }
            return false;
        }
    }


    class Solution {
        public void solveSudoku(char[][] board) {
            if (board == null || board.length != 9 || board[0].length != 9) return;
            boolean[][] row = new boolean[9][9], col = new boolean[9][9], box = new boolean[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] == '.') continue;
                    int num = board[i][j] - '1', k = (i / 3) * 3 + j / 3;
                    row[i][num] = col[j][num] = box[k][num] = true;
                }
            }
            solveSudokuHelper(board, 0, row, col, box);
        }

        /**
         * 用一个数记录一个坐标：n从0开始做累加，i = n / col; j = n % col (其中i j分别对应行列索引，n为记录坐标的数从0开始累加，col为列数)
         */
        boolean solveSudokuHelper(char[][] board, int n, boolean[][] row, boolean[][] col, boolean[][] box) {
            if (n == 81) {
                return true;
            }
            // 对于方阵，可用一个点表示一个坐标
            int i = n / 9, j = n % 9;
            if (board[i][j] != '.') {
                return solveSudokuHelper(board, n + 1, row, col, box);
            }

            int k = (i / 3) * 3 + j / 3;
            for (int num = 0; num < 9; num++) {
                if (row[i][num] || col[j][num] || box[k][num]) {
                    continue;
                }
                board[i][j] = (char) (num + '1');
                row[i][num] = col[j][num] = box[k][num] = true;
                if (solveSudokuHelper(board, n + 1, row, col, box)) {
                    return true;
                }
                row[i][num] = col[j][num] = box[k][num] = false;
            }
            board[i][j] = '.';
            return false;
        }
    }

}
