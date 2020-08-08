package cn.algorithm.leetcode;

import java.util.Arrays;

/**
 * 378. 有序矩阵中第K小的元素
 * 给定一个 n x n 矩阵，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
 * 请注意，它是排序后的第 k 小元素，而不是第 k 个不同的元素。
 * 示例：
 * matrix = [
 * [ 1,  5,  9],
 * [10, 11, 13],
 * [12, 13, 15]
 * ],
 * k = 8,
 * 返回 13。
 * 提示：
 * 你可以假设 k 的值永远是有效的，1 ≤ k ≤ n2 。
 *
 * @author hongzhou.wei
 * @date 2020/7/2
 */
public class P_0378_kthSmallestElementInASortedMatrix {
    public static void main(String[] args) {
        int matrix[][] = new int[][]{
            {1, 5, 9},
            {10, 11, 13},
            {12, 13, 15}};
        System.out.println(kthSmallest2(matrix, 8));
    }

    /**
     * 【暴力】
     *
     * @param matrix
     * @param k
     * @return
     */
    static public int kthSmallest1(int[][] matrix, int k) {
        int row = matrix[0].length, col = matrix.length;
        int d[] = new int[row * col];
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                d[index++] = matrix[i][j];
            }
        }
        Arrays.sort(d);
        return d[k - 1];
    }

    /**
     * 【二分查找】
     * 思想非常重要：设定一个中间值，然后在矩阵中从左下角向右上角找比中间数小的数，
     * 计数过程根据一列中上面的数都比下面的数小来计数。若没有达到k个就增大中间值继续找。
     *
     * @param matrix
     * @param k
     * @return
     */
    static public int kthSmallest2(int[][] matrix, int k) {
        int len = matrix.length;
        // 在矩阵值范围 [min,max] 区间内二分查找第 k 小元素。
        int min = matrix[0][0], max = matrix[len - 1][len - 1];
        while (min < max) {
            // 得到中间值
            int midValue = (max + min) >> 1;
            // 根据矩阵元素是否够 k 个小于等于中间值来移动遍历区间的边界。
            if (isEnoughK(matrix, midValue, k)) {
                max = midValue;
            } else {
                min = midValue + 1;
            }
        }
        return min;
    }

    /**
     * 二分查找时判断matrix中小于midValue值是否达到了k个数
     *
     * @param matrix
     * @param midValue
     * @param k
     * @return
     */
    static public boolean isEnoughK(int[][] matrix, int midValue, int k) {
        // 从矩阵左下角开始遍历每一列。
        int i = matrix.length - 1, j = 0;
        // 记录矩阵中元素小于等于 midValue 的个数。
        int num = 0;
        while (i >= 0 && j < matrix.length) {
            // 当前元素小于等于中间值，则当前一列从上到当前元素位置都小于等于中间值。
            if (matrix[i][j] <= midValue) {
                // 累加上当前这列上面的数（上面的数都比当前值小）
                num += i + 1;
                // 下一列
                j++;
            } else {
                // 上一行
                i--;
            }
        }
        // 返回小于等于中间值的元素是否够 k 个。
        return num >= k;
    }


}
