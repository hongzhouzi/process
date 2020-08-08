package cn.algorithm.leetcode.weekly._198;

/**
 * @author hongzhou.wei
 * @date 2020/7/12
 */
public class MM {
    public static void main(String[] args) {

    }
    public int numIdenticalPairs(int[] nums) {
        int len = nums.length, count = 0;
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                if(nums[i]==nums[j]){
                    count++;
                }
            }
        }
        return count;
    }
}
