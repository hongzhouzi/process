package cn.algorithm.leetcode;

import java.util.*;

/**
 * 51. N 皇后
 * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 上图为 8 皇后问题的一种解法。
 * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
 * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * 示例：
 * 输入：4
 * 输出：[
 * [".Q..",  // 解法 1
 * "...Q",
 * "Q...",
 * "..Q."],
 * ["..Q.",  // 解法 2
 * "Q...",
 * "...Q",
 * ".Q.."]
 * ]
 * 解释: 4 皇后问题存在两个不同的解法。
 * 提示：
 * 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。
 *
 * @author hongzhou.wei
 * @date 2020/9/3
 */
public class P0051_NQueens {
    public static void main(String[] args) {
        int n = 8;
//        List<List<String>> lists = solveNQueens(n);
        Long t = System.currentTimeMillis();
        List<List<String>> lists = new P0051_NQueens().solveNQueens2(n);

        int i = 1;
        for (List<String> list : lists) {
            System.out.println("--------"+i+"-start----------");
            for (String s : list) {
                System.out.println(s);
            }
            System.out.println("--------"+i+++"-end----------");
        }
        System.out.println(System.currentTimeMillis() - t);
    }

    /**
     * 一行/列中从上到下试探放置在该位置，然后依次验证其他行/列能否放置，若存在一种方案能满足就记录下，
     * 另外因为是正方形，所以只需遍历到正方形长度的一半，另外一种方案取对称即可。
     *
     * @param n
     * @return
     */
    static public List<List<String>> solveNQueens(int n) {
        boolean[][] grid = new boolean[n][n];
        int row = 0, col = 0;
        List<List<String>> ret = new LinkedList<>();
        // 遍历每列，遍历一半
        for (int i = 0; i < n; i++) {
            // 每趟遍历前初始化
            row = 0;
            boolean flag = true;
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    grid[x][y] = false;
                }
            }
            while (row < n && flag) {
                // 若是重新遍历就将col = i
                col = row == 0 ? i : 0;
                while (col < n && flag) {
                    // 验证该位置能否放置，能放则放下后继续下一行（todo 需要回溯，当前坐标再往后移动也有存在的情况，自己暂不能解决）
                    if (validation(grid, row, col)) {
                        grid[row++][col] = true;
                        break;
                    }
                    col++;
                    // 若该行所有位置都尝试过还是不满足，就直接跳出
                    if (col == n) {
                        flag = false;
                    }
                }
            }
            // 将符合要求的添加到结果集中
            if (flag) {
                List<String> list = new LinkedList<>();
                for (int x = 0; x < n; x++) {
                    StringBuilder sb = new StringBuilder();
                    for (int y = 0; y < n; y++) {
                        sb.append(grid[x][y] ? 'Q' : '.');
                    }
                    list.add(sb.toString());
                }
                ret.add(list);
            }
        }
        return ret;
    }

    static boolean validation(boolean[][] grid, int row, int col) {
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 横线、竖线、右上左下斜、左上右下斜，有皇后，返回false
                if (grid[i][j]) {
                    if (i == row || j == col || i + j == row + col || i - j == row - col) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private List<List<String>> solutions;
    private char[][]           nQueens;
    private boolean[]          colUsed;
    private boolean[]          diagonals1;
    private boolean[]          diagonals2;
    private int                n;

    public List<List<String>> solveNQueens2(int n) {
        // 初始化
        solutions = new ArrayList<>();
        nQueens = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(nQueens[i], '.');
        }
        colUsed = new boolean[n];
        diagonals1 = new boolean[2 * n - 1];
        diagonals2 = new boolean[2 * n - 1];
        this.n = n;
        // 回溯
        backtracking(0);
        return solutions;
    }

    private void backtracking(int row) {
        // 深度优先遍历到下标为 n，表示 [0.. n - 1] 已经填完，添加到结果集
        if (row == n) {
            List<String> list = new ArrayList<>();
            for (char[] chars : nQueens) {
                list.add(new String(chars));
            }
            solutions.add(list);
            return;
        }

        // 针对下标为 row 的每一列，尝试是否可以放置
        for (int col = 0; col < n; col++) {
            int diagonals1 = row + col;
            int diagonals2 = n - 1 - (row - col);
            // 该位置不能放置，直接跳过，检查下一个
            if (colUsed[col] || this.diagonals1[diagonals1] || this.diagonals2[diagonals2]) {
                continue;
            }
            // 该放置可以放置，放置上去准备递归搜索
            nQueens[row][col] = 'Q';
            colUsed[col] = this.diagonals1[diagonals1] = this.diagonals2[diagonals2] = true;
            backtracking(row + 1);
            // 回溯，改变至之前的状态
            colUsed[col] = this.diagonals1[diagonals1] = this.diagonals2[diagonals2] = false;
            nQueens[row][col] = '.';
        }
    }
}
