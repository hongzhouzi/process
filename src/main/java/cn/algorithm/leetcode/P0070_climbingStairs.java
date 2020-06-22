package cn.algorithm.leetcode;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 注意：给定 n 是一个正整数。
 * 示例 1：输入： 2；输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 * 示例 2：输入：3；输出：3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 *
 * @author hongzhou.wei
 * @date 2020/6/13
 */
public class P0070_climbingStairs {
    public static void main(String[] args) {
        System.out.println(c(3));

    }

    static int cc(int n){
        //递归解法，超时
         /*if(n==1 || n==2)
            return n;
        return climbStairs(n-1) + climbStairs(n-2);*/

        //动态规划
        /*if(n<=2)
            return n;
        int dp[] = new int[n+1];
        dp[0]=0;
        dp[1]=1;
        dp[2]=2;
        for(int i=3; i<=n; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];*/

        //斐波那契公式
        double sqrt5 = Math.sqrt(5);
        double fib = Math.pow((1+sqrt5)/2, n+1) - Math.pow((1-sqrt5)/2, n+1);
        return (int)(fib/sqrt5);
    }
    static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    // 动态规划思想
    static int c(int n) {
        if (n <= 2) {
            return n;
        }
        int i1 = 1;
        int i2 = 2;
        for (int i = 3; i <= n; i++) {
            int temp = i1 + i2;
            i1 = i2;
            i2 = temp;
        }
        return i2;
    }
}
