package cn.algorithm.leetcode;

/**
 * 64. 最小路径和
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，
 * 使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 * 示例:
 * 输入:
 * [
 *   [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 *
 * @author hongzhou.wei
 * @date 2020/7/23
 */
public class P0064_MinimumPathSum {

    public static void main(String[] args) {
        int d[][] = new int[][]{
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        System.out.println(minPathSum(d));
        System.out.println(minPathSum1(d));
    }

    /**
     * 【动态规划】
     * 状态：向右或下移动时最小的路径和
     * 状态转移方程：d[i][j] = min((d[i-1][j]+grid[i][j]), (d[i][j-1]+grid[i][j]))
     *
     * @param grid
     * @return
     */
    static public int minPathSum(int[][] grid) {
        int row = grid.length, col = grid[0].length;
        int dp[][] = new int[row][col];
        dp[0][0] = grid[0][0];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 && j == 0) {
                }else
                if (i == 0) {
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
                }else
                if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                }else {
                    dp[i][j] = Math.min((dp[i - 1][j] + grid[i][j]), (dp[i][j - 1] + grid[i][j]));
                }
            }
        }
        return dp[row - 1][col - 1];
    }


    /**
     * 【动态规划】空间优化至线性
     * 状态：向右或下移动到当前坐标最小的路径和
     * 状态转移方程：dp[j] = Math.min(dp[j-1]+grid[i][j], dp[j] + grid[i][j]);
     *
     * @param grid
     * @return
     */
    static public int minPathSum1(int[][] grid) {
        int row = grid.length, col = grid[0].length;
        int dp[] = new int[col];
        dp[0] = grid[0][0];
        // 最左边缘
        for (int i = 1; i < row; i++) {
            dp[i] = dp[i-1] +grid[0][i];
        }
        for (int i = 1; i < row; i++) {
            // 最上边缘
            dp[0] += grid[i][0];
            // 移动列 todo
            for (int j = 1; j < col; j++) {
                dp[j] = Math.min(dp[j-1]+grid[i][j], dp[j] + grid[i][j]);
            }
        }
        return dp[col - 1];
    }

}