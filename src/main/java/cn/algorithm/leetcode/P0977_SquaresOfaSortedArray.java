package cn.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hongzhou.wei
 * @date 2020/10/16
 */
public class P0977_SquaresOfaSortedArray {
    public static void main(String[] args) {

    }
    static class Solution {
        public int[] sortedSquares(int[] A) {
            int len = A.length,  lastIndex = len;
            int [] ret = new int[len];
            for (int i = 0; i < A.length; i++) {
                int sqrtVal = (int) Math.pow(A[i],2);
                if(A[i] < 0){
                    ret[--lastIndex] = sqrtVal;
                }else {
                    if(lastIndex != len){
                        if(sqrtVal > ret[lastIndex]){
                            int temp = ret[i];
                            ret[i] = ret[lastIndex];
                            ret[i+1] = temp;
                        }
                    }
                }
            }
            Arrays.sort(ret);
            return ret;
        }
    }
}
