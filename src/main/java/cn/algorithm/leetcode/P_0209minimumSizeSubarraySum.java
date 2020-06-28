package cn.algorithm.leetcode;

import java.util.Arrays;

/**
 * 209. 长度最小的子数组
 * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度
 * 最小的连续子数组，并返回其长度。如果不存在符合条件的连续子数组，返回 0。
 * 示例: 
 * 输入: s = 7, nums = [2,3,1,2,4,3]；输出: 2
 * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
 * 进阶:
 * 如果你已经完成了O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。
 *
 * @author hongzhou.wei
 * @date 2020/6/28
 */
public class P_0209minimumSizeSubarraySum {

    public static void main(String[] args) {
        int s = 11;
        int[] nums = new int[]{1, 2, 3, 4, 5};
        System.out.println(minSubArrayLen(s, nums));
        System.out.println(minSubArrayLen1(s, nums));
        System.out.println(minSubArrayLen2(s, nums));
    }

    /**
     * 暴力：在循环里面再来个循环定子数组找最小长度
     *
     * @param s
     * @param nums
     * @return
     */
    static public int minSubArrayLen(int s, int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int ret = Integer.MAX_VALUE;
        for (int i = 0, len = nums.length; i < len; i++) {
            int sum = 0;
            for (int j = i; j < len; j++) {
                sum += nums[j];
                if (sum >= s) {
                    ret = Math.min(j - i + 1, ret);
                    break;
                }
            }
        }
        return ret == Integer.MAX_VALUE ? 0 : ret;
    }

    /**
     * 双指针法：（滑动窗口）
     * 每轮迭代将nums[end]加在sum中并将end后移，若sum>=s，则更新子数组的最小长度end-start+1
     * 然后尝试将nums[start]从sum中减去，然后start后移，直到sum<s时更新子数组的最小长度
     *
     * @param s
     * @param nums
     * @return
     */
    static public int minSubArrayLen1(int s, int[] nums) {
        int sum = 0, minLen = Integer.MAX_VALUE, start = 0, end = 0;
        while (end < nums.length) {
            sum += nums[end];
            while (sum >= s) {
                // 记录最小的长度
                minLen = Math.min(minLen, end - start + 1);
                sum -= nums[start];
                start++;
            }
            end++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * 二分搜索：
     * 1.填充一个sums数组，其中sums[i]表示的是原数组nums前i个元素的和，题中说给定的n个正整数，那么相加的和会越来越大
     * sums中的元素是从小到大递增的（升序）
     * 2.找sums[k]-sums[j]>=s，那么k-j就是满足连续的子数组，但不一定是最小的，由sums[k]-sums[j]>=s得，
     * sums[j]+s<=sums[k]，得出sums[j]+s的值后可以使用二分查找到这个k（如果找到就会返回值的下标，如果没找到就
     * 会返回一个负数，这个负数取反之后就是查找的值应该在数组中的位置）
     *
     * @param s
     * @param nums
     * @return
     */
    static public int minSubArrayLen2(int s, int[] nums) {
        int minLen = Integer.MAX_VALUE;
        int len = nums.length;
        // 初始化sums
        int sums[] = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            sums[i] = sums[i - 1] + nums[i - 1];
        }
        // 二分查找
        for (int i = 0; i <= len; i++) {
            int tar = s + sums[i];
            // 如果找到就会返回值的下标，如果没找到就会返回一个负数，这个负数取反之后就是查找的值应该在数组中的位置
            int index = Arrays.binarySearch(sums, tar);
            if (index < 0) {
                index = ~index;
            }
            if (index <= len) {
                minLen = Math.min(minLen, index - i);
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
