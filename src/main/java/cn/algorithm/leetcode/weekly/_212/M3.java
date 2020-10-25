package cn.algorithm.leetcode.weekly._212;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author hongzhou.wei
 * @date 2020/10/25
 */
public class M3 {
    public static void main(String[] args) {

    }

    /*
    走过的路就不能走了，标记为不可达
    dp[m][n] = min abs(dp[m][n-1], dp[m][n+1], dp[m-1][n], dp[m+1][n] ) 不适用
     */
    static class Solution {
        public int minimumEffortPath(int[][] heights) {
            int [][] grid = heights;
            int row = grid.length, col = grid[0].length;
            int max = -1;
            int dp[][] = new int [row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {


                }
            }
            return dp[row-1][col-1];
        }
    }

    static class Solution1 {
        public int minimumEffortPath(int[][] heights) {
            int l = -1;
            int r = 1000000;
            int n = heights.length;
            int m = heights[0].length;
            int[] dx = {0, 1, 0, -1};
            int[] dy = {1, 0, -1, 0};

            while (l + 1 < r) {
                int mid = (l + r) / 2;
                boolean[][] visited = new boolean[n][m];
                Queue<int[]> queue= new LinkedList<>();
                queue.add(new int[] {0, 0});
                visited[0][0] = true;
                while (!queue.isEmpty()) {
                    int[] cur = queue.poll();
                    for (int d = 0; d < 4; d++) {
                        int x = cur[0] + dx[d];
                        int y = cur[1] + dy[d];
                        if (x <0 || x >= n || y <0 || y >= m) continue;
                        if (visited[x][y]) continue;
                        if (Math.abs(heights[x][y] - heights[cur[0]][cur[1]]) <= mid) {
                            queue.add(new int[] {x, y});
                            visited[x][y] = true;
                        }
                    }
                }
                if (visited[n-1][m-1]) {
                    r = mid;
                } else {
                    l = mid;
                }
            }
            return r;
        }

    }


    class Solution3 {
        public int minimumEffortPath(int[][] heights) {
            this.h = heights;
            n = heights.length;
            m = heights[0].length;
            visited = new boolean[n][m];
            int l = 0;
            int r = (int) 1e6;
            while (l < r) {
                int mid = (l + r) / 2;
                for (int i = 0; i < n; i++) {
                    Arrays.fill(visited[i], false);
                }
                dfs(0, 0, heights[0][0], mid);
                if (visited[n - 1][m - 1]) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            return l;
        }

        int[][] dirs = new int[][]{
                {1, 0},
                {0, 1},
                {-1, 0},
                {0, -1}
        };
        int[][] h;
        boolean[][] visited;
        int n;
        int m;

        public void dfs(int i, int j, int src, int t) {
            if (i < 0 || i >= n || j < 0 || j >= m || visited[i][j] || Math.abs(h[i][j] - src) > t) {
                return;
            }
            visited[i][j] = true;
            for (int[] dir : dirs) {
                dfs(i + dir[0], j + dir[1], h[i][j], t);
            }
        }
    }
}
