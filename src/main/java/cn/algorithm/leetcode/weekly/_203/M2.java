package cn.algorithm.leetcode.weekly._203;

import java.util.Arrays;

public class M2 {
    public static void main(String[] args) {
        int []piles = {2,4,1,2,7,8};
//        int []piles = {9,8,7,6,5,1,2,3,4};
        System.out.println(maxCoins(piles));
    }

    static public int maxCoins(int[] piles) {
        Arrays.sort(piles);
        int n = piles.length /3, ans = 0;
        int i = piles.length-1,count = 0;
        while (i>=0){
            if(count++ == n){
                break;
            }
            ans += piles[i-1];
            i = i-2;
        }
       return ans;
    }

}
