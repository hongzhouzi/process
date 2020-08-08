package cn.algorithm.leetcode;

import cn.examination.pass58.Solution;

import java.util.Arrays;

/**
 * @author hongzhou.wei
 * @date 2020/7/12
 */
public class P0174_DungeonGame {
    public static void main(String[] args) {

    }

    /**
     * 动态规划，在移动过程中记录每条路线累计的最大负值
     * @param dungeon
     * @return
     */
   static public int calculateMinimumHP(int[][] dungeon) {
        int n = dungeon.length, m = dungeon[0].length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; ++i) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[n][m - 1] = dp[n - 1][m] = 1;
        for (int i = n - 1; i >= 0; --i) {
            for (int j = m - 1; j >= 0; --j) {
                int minn = Math.min(dp[i + 1][j], dp[i][j + 1]);
                dp[i][j] = Math.max(minn - dungeon[i][j], 1);
            }
        }
        return dp[0][0];
    }
}
