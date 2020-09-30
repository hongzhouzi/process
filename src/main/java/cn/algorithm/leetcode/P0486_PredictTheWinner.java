package cn.algorithm.leetcode;

/**
 * 486. 预测赢家
 * 给定一个表示分数的非负整数数组。 玩家 1 从数组任意一端拿取一个分数，随后玩家 2 继续从剩余数组任意一端拿取分数，
 * 然后玩家 1 拿，…… 。每次一个玩家只能拿取一个分数，分数被拿取之后不再可取。直到没有剩余分数可取时游戏结束。
 * 最终获得分数总和最多的玩家获胜。给定一个表示分数的数组，预测玩家1是否会成为赢家。你可以假设每个玩家的玩法都会使他的分数最大化。
 * 示例 1：
 * 输入：[1, 5, 2]
 * 输出：False
 * 解释：一开始，玩家1可以从1和2中进行选择。
 * 如果他选择 2（或者 1 ），那么玩家 2 可以从 1（或者 2 ）和 5 中进行选择。如果玩家 2 选择了 5 ，那么玩家 1 则只剩下 1（或者 2 ）可选。
 * 所以，玩家 1 的最终分数为 1 + 2 = 3，而玩家 2 为 5 。
 * 因此，玩家 1 永远不会成为赢家，返回 False 。
 * 示例 2：
 * 输入：[1, 5, 233, 7]
 * 输出：True
 * 解释：玩家 1 一开始选择 1 。然后玩家 2 必须从 5 和 7 中进行选择。无论玩家 2 选择了哪个，玩家 1 都可以选择 233 。
 * 最终，玩家 1（234 分）比玩家 2（12 分）获得更多的分数，所以返回 True，表示玩家 1 可以成为赢家。
 * 提示：
 * 1 <= 给定的数组长度 <= 20.
 * 数组里所有分数都为非负数且不会大于 10000000 。
 * 如果最终两个玩家的分数相等，那么玩家 1 仍为赢家。
 *
 * @author hongzhou.wei
 * @date 2020/9/1
 */
public class P0486_PredictTheWinner {

    public static void main(String[] args) {
        int[] a = {1, 5, 233, 7};
        System.out.println(PredictTheWinner(a));
    }

    /*
    对于偶数个数字的数组，玩家1一定获胜。因为如果玩家1选择拿法A，玩家2选择拿法B，玩家1输了。则玩家1换一种拿法选择拿法B，因为玩家1是先手，
    所以玩家1一定可以获胜。对于奇数个数字的数组，利用动态规划(dynamic programming)计算。 首先证明最优子结构性质。对于数组[1..n]中的子数
    组[i..j]，假设玩家1在子数组[i..j]中的拿法是最优的，即拿的分数比玩家2多出最多。假设玩家1拿了i，则[i+1..j]中玩家1拿的方法也一定是最优
    的。利用反证法证明：如果玩家1在[i+1..j]中有更优的拿法，即玩家1在[i+1...j]可以拿到更多的分数，则玩家在[i..j]中拿到的分数就会比假设的
    最优拿法拿到的分数更多，显然矛盾。如果玩家1拿了j，同理可证矛盾。 所以当前问题的最优解包含的子问题的解一定也是子问题的最优解。对于只有
    一个数字的子数组,即i=j，dp[i][i] = num[i]，因为玩家1先手拿了这一个分数， 玩家2就没得拿了，所以是最优拿法。 对于两个数字的子数组,即j
    -i=1，dp[i][j]=abs(num[i]-num[j]),玩家1先手拿两个数中大的一个， 所以玩家1一定比玩家2多两个数字差的绝对值，为最优拿法。 对于j-i>1的
    子数组，如果玩家1先手拿了i，则玩家1手里有num[i]分， 则玩家2一定会按照[i+1..j]这个子数组中的最优拿法去拿，于是玩家2此时手里相当于有dp[i+1][j]分，
    于是玩家1比玩家2多num[i]-dp[i+1][j]分。 如果玩家1先手拿了j，则玩家1手里有num[j]分，则玩家2一定会按照[i..j-1]这个子数组中的最优拿法
    去拿，于是玩家2此时手里相当于有dp[i][j-1]分， 于是玩家1比玩家2多num[j]-dp[i][j-1]分。数组的填充方向是从下往上，从左到右，最后填充的是dp[1][n]。
     */

    /**
     * 感觉可以用DP解，但我找不到最优子结构
     *
     * @param nums
     * @return
     */
    static public boolean PredictTheWinner(int[] nums) {
        return true;
    }


    /**
     * 暴力解法
     * 时间复杂度：O(2^n)，其中 n 是数组的长度。
     * 空间复杂度：O(n)，其中 n 是数组的长度。空间复杂度取决于递归使用的栈空间
     *
     * @param nums
     * @return
     */
    public boolean PredictTheWinner1(int[] nums) {
        return total(nums, 0, nums.length - 1, 1) >= 0;
    }

    public int total(int[] nums, int start, int end, int turn) {
        if (start == end) {
            return nums[start] * turn;
        }
        int scoreStart = nums[start] * turn + total(nums, start + 1, end, -turn);
        int scoreEnd = nums[end] * turn + total(nums, start, end - 1, -turn);
        return Math.max(scoreStart * turn, scoreEnd * turn) * turn;
    }

    /**
     * 【DP】
     *
     * @param nums
     * @return
     */
    public boolean PredictTheWinner2(int[] nums) {
        int length = nums.length;
        int[][] dp = new int[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = nums[i];
        }
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }
        return dp[0][length - 1] >= 0;
    }

    /**
     * 【DP】空间优化
     *
     * @param nums
     * @return
     */
    public boolean PredictTheWinner3(int[] nums) {
        int length = nums.length;
        int[] dp = new int[length];
        for (int i = 0; i < length; i++) {
            dp[i] = nums[i];
        }
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                dp[j] = Math.max(nums[i] - dp[j], nums[j] - dp[j - 1]);
            }
        }
        return dp[length - 1] >= 0;
    }



}
