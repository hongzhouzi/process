package cn.algorithm.leetcode.weekly._206;

/**
 * @author hongzhou.wei
 * @date 2020/9/13
 */
public class M1 {
    public static void main(String[] args) {
//        int[][] mat = {{1, 0, 0},
//                {0, 0, 1},
//                {1, 0, 0}};

//        int[][] mat = {{1, 0, 0},
//                {0, 1,0},
//                {0, 0, 1}};

        int[][] mat = {{1, 0},
                {0, 1},
                {0, 0}};
        System.out.println(new M1.Solution().numSpecial(mat));
    }

    static class Solution {
        public int numSpecial(int[][] mat) {
            int count = 0;
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[0].length; j++) {
                    if (mat[i][j] == 1 && f(i, j, mat)) {
                        count++;
                    }
                }
            }
            return count;
        }

        boolean f(int i, int j, int[][] mat) {
            int sum = 0;
            for (int x = 0; x < mat[0].length; x++) {
                sum += mat[i][x];
            }
            for (int x = 0; x < mat.length; x++) {
                sum += mat[x][j];
            }
            return sum == 2;
        }
    }
}
