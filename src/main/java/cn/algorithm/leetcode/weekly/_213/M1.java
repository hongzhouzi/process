package cn.algorithm.leetcode.weekly._213;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hongzhou.wei
 * @date 2020/11/1
 */
public class M1 {
    public static void main(String[] args) {
//        int arr[] = {49,18,16};        int[][] pieces = {{16,18,49}};
        int arr[] = {1,3,5,7};        int[][] pieces = {{2,4,6,8}};
//        int arr[] = {91,4,64,78};        int[][] pieces = {{78},{4,64},{91}};
//        int arr[] = {85};        int[][] pieces = {{85}};
        System.out.println(new Solution().canFormArray(arr, pieces));
    }

    static class Solution {
        public boolean canFormArray(int[] arr, int[][] pieces) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < pieces.length; i++) {
                map.put(pieces[i][0], i);
            }
            for (int i = 0; i < arr.length; i++) {
                int x = arr[i];
                if (map.containsKey(x)) {
                    Integer index = map.get(x);
                    if ((pieces[index].length > 1)) {
                        for (int j = 1; j < pieces[index].length; j++) {
                            if (pieces[index][j] != arr[++i]) {
                                return false;
                            }
                        }
                    }
                }else {
                    return false;
                }
            }
            return true;
        }
    }
}
