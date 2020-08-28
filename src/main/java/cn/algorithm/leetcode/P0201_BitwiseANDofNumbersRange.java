package cn.algorithm.leetcode;

public class P0201_BitwiseANDofNumbersRange {
    public static void main(String[] args) {
//        int m = 5, n = 7;
        int m = 0, n = 2147483647;
        System.out.println(rangeBitwiseAnd(m, n));
    }

    /**
     * 【暴力】直接遍历[m,n]区间的数进行运算，若结果为0了就直接返回
     *
     * @param m
     * @param n
     * @return
     */
    static public int rangeBitwiseAnd(int m, int n) {
        int ans = n;
        while (m < n) {
            ans &= m++;
            if (ans == 0) {
                return ans;
            }
        }
        return ans;
    }

    /**
     * 【移位找公共前缀】
     *
     * @param m
     * @param n
     * @return
     */
    static public int rangeBitwiseAnd1(int m, int n) {
        int shift = 0;
        // 找到公共前缀，并记录移位次数
        while (m < n) {
            m >>= 1;
            n >>= 1;
            shift++;
        }
        return m << shift;
    }

    /**
     * 【Brian Kernighan 算法】它用于清除二进制串中最右边的 1，关键在于每次对number 和 number−1
     * 之间进行按位与运算后，number 中最右边的 1 会被抹去变成 0。此处将 n 右边的 1 抹去，直到 n<=m 时返回n
     * 时间复杂度：O(log n)
     * 空间复杂度：O(1)
     *
     * @param m
     * @param n
     * @return
     */
    static public int rangeBitwiseAnd2(int m, int n) {
        while (m < n) {
            n &= n - 1;
        }
        return n;
    }
}
