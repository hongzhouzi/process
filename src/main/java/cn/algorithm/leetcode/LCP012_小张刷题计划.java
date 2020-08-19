package cn.algorithm.leetcode;

import java.util.Arrays;

/**
 * LCP 12. 小张刷题计划
 * 为了提高自己的代码能力，小张制定了 LeetCode 刷题计划，他选中了 LeetCode 题库中的 n 道题，编号从 0 到 n-1，
 * 并计划在 m 天内按照题目编号顺序刷完所有的题目（注意，小张不能用多天完成同一题）。
 * 在小张刷题计划中，小张需要用 time[i] 的时间完成编号 i 的题目。此外，小张还可以使用场外求助功能，通过询问他
 * 的好朋友小杨题目的解法，可以省去该题的做题时间。为了防止“小张刷题计划”变成“小杨刷题计划”，小张每天最多使用一次求助。
 * 我们定义 m 天中做题时间最多的一天耗时为 T（小杨完成的题目不计入做题总时间）。请你帮小张求出最小的 T是多少。
 * 示例 1：
 * 输入：time = [1,2,3,3], m = 2
 * 输出：3
 * 解释：第一天小张完成前三题，其中第三题找小杨帮忙；第二天完成第四题，并且找小杨帮忙。这样做题时间最多的一天花费了 3 的时间，并且这个值是最小的。
 * 示例 2：
 * 输入：time = [999,999,999], m = 4
 * 输出：0
 * 解释：在前三天中，小张每天求助小杨一次，这样他可以在三天内完成所有的题目并不花任何时间。
 * 限制：
 * 1 <= time.length <= 10^5
 * 1 <= time[i] <= 10000
 * 1 <= m <= 1000
 *
 * @author hongzhou.wei
 * @date 2020/8/19
 */
public class LCP012_小张刷题计划 {
    public static void main(String[] args) {
        /*int []time = {1,2,7,4,7,7};
        int m = 2 ;*/
        int []time = {50,47,68,33,35,84,25,49,91,75};
        int m = 1 ;
        System.out.println(minTime(time,m));
    }
    static public int minTime(int[] time, int m) {
        int len = time.length;
        // 全部求助小杨，自己不用花时间
        if(len <= m){
            return 0;
        }

        // 1.初始化二分搜索边界（不同题目初始化的边界不同）
        int left = 0, right = 0;
        for (int i = 0; i < time.length; i++) {
            right += time[i];
        }
        // 2.开始二分搜索+验证；两个数相邻时就结束条件
        while (right - left > 1){
            int mid = (right - left) / 2 + left;
            // 4.缩小验证范围，最小化最大值：验证符合要求后，验证有没有更小的符合要求的值，则缩小大值范围
            if (check(time, mid, m)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        //right是验证通过的，而left是验证未通过，两个碰面的就说明范围缩小到left和right，而right验证通过，left验证未通过，故返回right
        return right;
    }
    // 3.验证mid值是否符合要求（不同题目验证逻辑不同）
    static boolean check(int[] nums, int mid,int m){
        // 每组划分 m 的最大和，贪心划分看有多少组
        int count = 1, curSum = 0, curMax = 0;
        for (int i = 0; i < nums.length; i++) {
            curMax = curMax < nums[i] ? nums[i] : curMax;
            if(curSum - curMax + nums[i] <= mid) {
                curSum += nums[i];
            }else {
                curSum = nums[i];
                curMax = nums[i];
                count++;
            }
        }
        return count <= m;
    }
}
