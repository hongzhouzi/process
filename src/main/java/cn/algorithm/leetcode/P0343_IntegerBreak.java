package cn.algorithm.leetcode;

/**
 * 343. 整数拆分
 * 给定一个正整数 n，将其拆分为至少两个正整数的和，并使这些整数的乘积最大化。 返回你可以获得的最大乘积。
 * 示例 1:
 * 输入: 2
 * 输出: 1
 * 解释: 2 = 1 + 1, 1 × 1 = 1。
 * 示例 2:
 * 输入: 10
 * 输出: 36
 * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
 * 说明: 你可以假设 n 不小于 2 且不大于 58。
 *
 * @author hongzhou.wei
 * @date 2020/7/30
 */
public class P0343_IntegerBreak {
    public static void main(String[] args) {
        int n = 10;
        System.out.println(integerBreak(n));
    }

    /**
     * 【动态规划】
     * 将n进行拆分，每次拆分过程记录乘积最大的结果，直到拆分到不能拆分 拆分至1
     * 状态：i拆分得到的最大乘积
     * 转移方程：dp[i]= max{max(j×(i−j),j×dp[i−j])}
     * 1≤j<i
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(n)
     *
     * @param n
     * @return
     */
    static public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            int curMax = 0;
            for (int j = 1; j < i; j++) {
                curMax = Math.max(curMax, Math.max(j * (i - j), j * dp[i - j]));
            }
            dp[i] = curMax;
        }
        return dp[n];
    }

    /**
     * 【动态规划】优化
     *
     * @param n
     * @return
     */
    static public int integerBreak0(int n) {
        if (n < 4) {
            return n - 1;
        }
        int[] dp = new int[n + 1];
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i] = Math.max(Math.max(2 * (i - 2), 2 * dp[i - 2]), Math.max(3 * (i - 3), 3 * dp[i - 3]));
        }
        return dp[n];
    }

    /**
     * 数学方法：
     * 若将给定的正整数拆分成尽可能多的某个特定的正整数，则这些正整数的乘积最大。
     * 定义函数 f(x) 表示将给定的正整数 n 拆分成尽可能多的正数 x 的情况下的最大乘积，则可以将 n 分成n/x项
     * 此时 f(x)= x ^ (n/x) 目标是求 f(x)的最大值。
     * y=(n/x)^x(x>0)的最大值，可得x=e时y最大，但只能分解成整数，故x取2或3，
     * 由于6=2+2+2=3+3，显然2^3=8<9=3^2,故应分解为多个3
     *
     * 根据 n 除以 3 的余数进行分类讨论：
     * 如果余数为 0，即 n=3m(m≥2)，则将 n 拆分成 m 个 3；
     * 如果余数为 1，即 n=3m+1(m≥1)，由于 2×2>3×1，因此将 n 拆分成 m−1 个 3 和 2 个 2；
     * 如果余数为 2，即 n=3m+2(m≥1)，则将 n 拆分成 m 个 3 和 1 个 2。
     *
     * @param n
     * @return
     */
    static public int integerBreak1(int n) {
        if (n <= 3) {
            return n - 1;
        }
        int a = 1;
        while (n > 4) {
            n = n - 3;
            a = a * 3;
        }
        return a * n;
    }

    public int integerBreak2(int n) {
        if (n <= 3) {
            return n - 1;
        }
        int quotient = n / 3;
        int remainder = n % 3;
        if (remainder == 0) {
            return (int) Math.pow(3, quotient);
        } else if (remainder == 1) {
            return (int) Math.pow(3, quotient - 1) * 4;
        } else {
            return (int) Math.pow(3, quotient) * 2;
        }
    }
}
