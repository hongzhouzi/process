package cn.algorithm.leetcode;

/**
 * 309. 最佳买卖股票时机含冷冻期
 * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​设计一个算法
 * 计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 示例:
 * 输入: [1,2,3,0,2]
 * 输出: 3
 * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 *
 * @author hongzhou.wei
 * @date 2020/7/10
 */
public class P0309_BestTimetoBuyandSellStockwithCooldown {
    public static void main(String[] args) {
        int d[] = new int[]{1, 2, 3, 0, 2};
        System.out.println(maxProfit(d));
    }

    /**
     * 有买入、卖出、冷冻期三种状态，那么在描述状态转移方程时也需要分三种状态讨论
     * 1.刚买入了，当前持有股票对应的累计最大收益 f[i][0]
     * 2.刚卖出了，当前没有持有股票，并处于冷冻期，不能买股票 f[i][1]
     * 3.刚卖出了，当前没有持有股票，并不处于冷冻期，能买股票 f[i][2]
     * 对于这三种状态对应的转移情况：
     * 1.当前持有了股票，则可能是前一天买的，今日收益和昨天一样；
     * 也可能是当天买的，则昨天没股票且不为冷冻期，为负收益。
     * f[i][0] = max(f[i-1][0], f[i-1][2] - price[i])
     * <p>
     * 2.当前没有持有股票，处于冷冻期，股票收益和前一天的一样？？？
     * f[i][1] = f[i-1][0]
     * (  f[i][1] = f[i-1][0] +prices[i] )
     * <p>
     * 3.当前没有持有股票，不处于冷冻期，刚卖出（收益+今天的价格）
     * 或不买卖（收益和前一天一样,前一天）
     * f[i][2] = max(f[i][2]+price[i], f[i-1][2])
     * （  f[i][2] = max(f[i-1][1], f[i-1][2])  ）
     * <p>
     * 最后计算手中不持有股票情况中的最大值
     * 临界情况：
     * f[0][0] = -price[0]
     * f[0][1] = 0
     * f[0][2] = 0
     *
     * @param prices
     * @return
     */
    static public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        int f[][] = new int[n][3];
        f[0][0] = -prices[0];
        for (int i = 1; i < n; i++) {
            f[i][0] = Math.max(f[i - 1][0], f[i-1][2] - prices[i]);
            f[i][1] = f[i - 1][0] + prices[i];
            f[i][2] = Math.max(f[i-1][1], f[i-1][2]);
        }
        return Math.max(f[n - 1][1], f[n - 1][2]);
    }


    public int maxProfit1(int[] prices) {
        int dp_i_0=0,dp_i_1=Integer.MIN_VALUE;
        int dp_pre_2=0;
        for(int i=0;i<prices.length;i++){
            int tmp=dp_i_0;
            dp_i_0=Math.max(dp_i_0,dp_i_1+prices[i]);
            dp_i_1=Math.max(dp_i_1,dp_pre_2-prices[i]);
            dp_pre_2=tmp;
        }
        return dp_i_0;
    }
}
