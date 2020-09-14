package cn.algorithm.leetcode.weekly._206;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author hongzhou.wei
 * @date 2020/9/13
 */
public class M2 {
    public static void main(String[] args) {
        int n = 4;
        int [][]preferences = {{1, 2, 3}, {3, 2, 0}, {3, 1, 0}, {1, 2, 0}}, pairs = {{0, 1}, {2, 3}};
        System.out.println(new M2.Solution().unhappyFriends(n,preferences, pairs));

    }

    static class Solution {
        public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
            // 遍历paris，通过perferences校验是否统计
            return 1;
        }
    }


    public class Solution0 {
        public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
            int[][] inv = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n - 1; j++) {
                    inv[i][preferences[i][j]] = j;
                }
            }
            int[] partner = new int[n];
            for (int[] p : pairs) {
                partner[p[0]] = p[1];
                partner[p[1]] = p[0];
            }

            int ans = 0;
            for (int i = 0; i < n; i++) {
                boolean happy = true;
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (inv[i][partner[i]] > inv[i][j] &&
                            inv[j][partner[j]] > inv[j][i]) {
                        happy = false;
                    }
                }
                if(!happy){
                    ans++;
                }
            }
            return ans;
        }
    }


    class Solution1 {
        public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
            int m = pairs.length;
            int[] ops = new int[n];
            Arrays.fill(ops, -1);
            for(int[] pa : pairs) {
                ops[pa[0]] = pa[1];
                ops[pa[1]] = pa[0];
            }

            int[] opord = new int[n];
            for(int i = 0;i < n;i++) {
                for(int j = 0;j < n-1;j++) {
                    if(preferences[i][j] == ops[i]) {
                        opord[i] = j;
                        break;
                    }
                }
            }

            int un = 0;
            outer:
            for(int i = 0;i < n;i++) {
                for(int j = 0;j < opord[i];j++) {
                    int x = preferences[i][j];
                    for(int k = 0;k < opord[x];k++) {
                        if(preferences[x][k] == i) {
                            un++;
                            continue outer;
                        }
                    }
                }
            }
            return un;
        }
    }

    class Solution3 {

        public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
            HashMap<Integer, Integer> maps[] = new HashMap[n], map = new HashMap<>();
            for (int i = 0; i < n; i++) {
                maps[i] = new HashMap<>();
            }
            for (int i = 0; i < preferences.length; i++) {
                for (int j = 0; j < preferences[i].length; j++) {
                    maps[i].put(preferences[i][j], j);
                }
            }
            for (int[] pair : pairs) {
                map.put(pair[0], pair[1]);
                map.put(pair[1], pair[0]);
            }
            int count = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < preferences[i].length; j++) {
                    if (preferences[i][j] == map.get(i)) {
                        break;
                    }
                    if (maps[preferences[i][j]].get(i) < maps[preferences[i][j]].get(map.get(preferences[i][j]))) {
                        count++;
                        break;
                    }
                }
            }
            return count;
        }
    }

}
