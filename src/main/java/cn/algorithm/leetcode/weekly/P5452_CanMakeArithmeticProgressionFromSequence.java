package cn.algorithm.leetcode.weekly;

import java.util.Arrays;

/**
 * @author hongzhou.wei
 * @date 2020/7/5
 */
public class P5452_CanMakeArithmeticProgressionFromSequence {
    public static void main(String[] args) {
        int a[] = new int[]{2,4,1};
        System.out.println(canMakeArithmeticProgression(a));
    }

    static public boolean canMakeArithmeticProgression(int[] arr) {
        Arrays.sort(arr);
        int d = arr[1] - arr[0];
        for (int i = 1; i < arr.length-1; i++) {
            if(arr[i+1] - arr[i] != d){
                return false;
            }
        }
        return true;
    }
}
