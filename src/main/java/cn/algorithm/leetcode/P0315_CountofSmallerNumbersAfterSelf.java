package cn.algorithm.leetcode;

import java.util.*;

/**
 * 315. 计算右侧小于当前元素的个数
 * 给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
 * 示例:
 * 输入: [5,2,6,1]
 * 输出: [2,1,1,0]
 * 解释:
 * 5 的右侧有 2 个更小的元素 (2 和 1).
 * 2 的右侧仅有 1 个更小的元素 (1).
 * 6 的右侧有 1 个更小的元素 (1).
 * 1 的右侧有 0 个更小的元素.
 *
 * @author hongzhou.wei
 * @date 2020/7/11
 */
public class P0315_CountofSmallerNumbersAfterSelf {
    public static void main(String[] args) {
        int d[] = new int[]{6,1,2,5,3,4};
        System.out.println(countSmaller1(d).toString());
    }

    /**
     * 暴力：超时
     *
     * @param nums
     * @return
     */
    static public List<Integer> countSmaller(int[] nums) {
        List<Integer> ret = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    count++;
                }
            }
            ret.add(count);
        }
        return ret;
    }

    /**
     * 【思路不对】从右往左计算，当前数小于右边一个更小的数，就在更小的记录上+1即可
     * 看了题解后懵了，涉及的一些东西触及了知识盲区，不死磕了，学题解的吧
     *
     * @param nums
     * @return
     */
    static public List<Integer> countSmaller1(int[] nums) {
        int len = nums.length;
        if(len == 0){
            return new ArrayList<>();
        }
        // 最后一个右侧小于它的个数为0
        int result[] = new int[len];
        result[len-1] = 0;
        for (int i = len - 2; i >= 0; i--) {
            for (int j = i + 1; j < len; j++) {
                // 找到右边更小的一个数，当前记录数在此基础上+1，跳出
                // 存在多个相等的情况没有考虑到
                if(nums[i] > nums[j]){
                    result[i] = result[j]+1;
                    // 若存在多个小于等于它的数，都相等的情况
                    for(int k = j; k<len-1; k++){
                        if(nums[k] <= nums[k+1]){
                            result[i]++;
                        }else {
                            break;
                        }
                    }
                    break;
                }
            }
        }
        List<Integer> ret = new ArrayList<>(len);
        for (int i = 0; i < result.length; i++) {
            ret.add(result[i]);
        }
        return ret;
    }


}
