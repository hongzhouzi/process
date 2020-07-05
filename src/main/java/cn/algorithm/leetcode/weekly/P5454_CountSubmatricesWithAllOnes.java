package cn.algorithm.leetcode.weekly;

/**
 * 5454. 统计全 1 子矩形
 * 给你一个只包含 0 和 1 的 rows * columns 矩阵 mat ，请你返回有多少个 子矩形 的元素全部都是 1 。
 * 示例 1：
 * 输入：mat = [[1,0,1],
 * [1,1,0],
 * [1,1,0]]
 * 输出：13
 * 解释：
 * 有 6 个 1x1 的矩形。
 * 有 2 个 1x2 的矩形。
 * 有 3 个 2x1 的矩形。
 * 有 1 个 2x2 的矩形。
 * 有 1 个 3x1 的矩形。
 * 矩形数目总共 = 6 + 2 + 3 + 1 + 1 = 13 。
 * 示例 2：
 * 输入：mat = [[0,1,1,0],
 * [0,1,1,1],
 * [1,1,1,0]]
 * 输出：24
 * 解释：
 * 有 8 个 1x1 的子矩形。
 * 有 5 个 1x2 的子矩形。
 * 有 2 个 1x3 的子矩形。
 * 有 4 个 2x1 的子矩形。
 * 有 2 个 2x2 的子矩形。
 * 有 2 个 3x1 的子矩形。
 * 有 1 个 3x2 的子矩形。
 * 矩形数目总共 = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24 。
 * 示例 3：
 * 输入：mat = [[1,1,1,1,1,1]]
 * 输出：21
 * 示例 4：
 * 输入：mat = [[1,0,1],[0,1,0],[1,0,1]]
 * 输出：5
 * <p>
 * 提示：
 * 1 <= rows <= 150
 * 1 <= columns <= 150
 * 0 <= mat[i][j] <= 1
 *
 * @author hongzhou.wei
 * @date 2020/7/5
 */
public class P5454_CountSubmatricesWithAllOnes {

    public static void main(String[] args) {
        int mat[][] = {
                {0, 1, 1, 0},
                {0, 1, 1, 1},
                {1, 1, 1, 0}};
        System.out.println(new P5454_CountSubmatricesWithAllOnes().numSubmat(mat));
    }

    /**
     * 矩阵里每个点(i.j)统计他这行左边到他这个位置最多有几个连续的1，
     * 存为left[i][j]。然后对于每个点(i.j)，我们固定子矩形的右下角为(i.j)，
     * 利用left从该行i向上寻找子矩阵左上角为第k行的矩阵个数。
     * 每次将子矩阵个数加到答案中即可。
     * 时间复杂度O(nnm)，空间复杂度O(nm)。
     *
     * @param mat
     * @return
     */
    public int numSubmat(int[][] mat) {
        int row = mat.length, col = mat[0].length;
        // 统计这行左边到当前位置最多有几个连续的1
        int left[][] = new int[row][col];
        for (int i = 0; i < row; i++) {
            int now = 0;
            for (int j = 0; j < col; j++) {
                if (mat[i][j] > 0) {
                    now++;
                }else {
                    now = 0;
                }
                left[i][j] =  now;
            }
        }
        int count = 0,min = Integer.MAX_VALUE;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                min = Integer.MAX_VALUE;
                for(int k = i; k>=0; k--){
                    min = Math.min(left[k][j],min);
                    count += min;
                }
            }
        }
        return count;
    }
}
