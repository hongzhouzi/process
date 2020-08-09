package cn.algorithm.leetcode.weekly._201;

import java.util.HashMap;

/**
 * 5471. 和为目标值的最大数目不重叠非空子数组数目 显示英文描述
 * 题目难度Medium
 * 给你一个数组 nums 和一个整数 target 。
 *
 * 请你返回 非空不重叠 子数组的最大数目，且每个子数组中数字和都为 target 。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,1,1,1,1], target = 2
 * 输出：2
 * 解释：总共有 2 个不重叠子数组（加粗数字表示） [1,1,1,1,1] ，它们的和为目标值 2 。
 * 示例 2：
 *
 * 输入：nums = [-1,3,5,1,4,2,-9], target = 6
 * 输出：2
 * 解释：总共有 3 个子数组和为 6 。
 * ([5,1], [4,2], [3,5,1,4,2,-9]) 但只有前 2 个是不重叠的。
 * 示例 3：
 *
 * 输入：nums = [-2,6,6,3,5,4,1,2,8], target = 10
 * 输出：3
 * 示例 4：
 *
 * 输入：nums = [0,0,0], target = 0
 * 输出：3
 *
 *
 * 提示：
 *
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 0 <= target <= 10^6
 *
 * @author hongzhou.wei
 * @date 2020/8/9
 */
public class M3 {
    public static void main(String[] args) {
        /*int [] nums = {1,1,1,1,1};
        int target = 2;*/

        int [] nums = {-1,3,5,1,4,2,-9};
        int target = 6;
        System.out.println(maxNonOverlapping(nums,target));
    }

    /**
     * 贪心
     *
     * @param nums
     * @param target
     * @return
     */
    static public int maxNonOverlapping(int[] nums, int target) {
        int sum = 0, lastIndex = 0, count = 0;
        for (int i = 0; i < nums.length; i++) {
            if(sum < target){
                sum += nums[i];
            }else if(sum > target){
                int k = 0;
                while (sum > target){
                    sum -= nums[lastIndex++];
                }
                if(sum == target){
                    sum = nums[i];
                    count++;
                    continue;
                }
                sum += nums[i];
            }
            if(sum == target){
                sum = nums[i];
                lastIndex = i;
                count++;
            }
        }
        return count;
    }

    public int maxNonOverlapping1(int[] nums, int tg) {
        int n = nums.length;
        HashMap<Integer, Integer> shm = new HashMap<>();
        int[] sums = new int[n+1];
        int[] dp = new int[n+1];
        shm.put(0,0);
        for (int i = 1; i <= n; ++i) {
            sums[i] = sums[i-1]+nums[i-1];
            dp[i] = dp[i-1];
            if (shm.containsKey(sums[i]-tg)) {
                dp[i] = Math.max(dp[i], dp[shm.get(sums[i]-tg)]+1);
            }
            shm.put(sums[i],i);
        }
        return dp[n];
    }
}
