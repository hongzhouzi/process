package cn.algorithm.leetcode.weekly._212;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hongzhou.wei
 * @date 2020/10/25
 */
public class M2 {

    public static void main(String[] args) {
        int [] nums = {-12,-9,-3,-12,-6,15,20,-25,-20,-15,-10}, l = {0,1,6,4,8,7}, r = {4,4,9,7,9,10};
//        int [] nums = {4,6,5,9,3,7}, l = {0,0,2}, r = {2,3,5};
        System.out.println(new M2.Solution().checkArithmeticSubarrays(nums,l,r));
    }
    static class Solution {
        public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
            List<Boolean> ret = new ArrayList<>();
            for (int i = 0; i < l.length; i++) {
                int left = l[i], right = r[i];
                int[] curNums = Arrays.copyOfRange(nums, left, right+1);
                Arrays.sort(curNums);
                ret.add(judge(curNums));
            }
            return ret;
        }

        boolean judge(int nums[]) {
            if (nums.length < 2) {
                return true;
            }
            int d = nums[1] - nums[0];
            for (int i = 2; i < nums.length; i++) {
                if (nums[i] - nums[i - 1] != d) {
                    return false;
                }
            }
            return true;
        }
    }
}
