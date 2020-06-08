package cn.examination.beike;

import java.util.Scanner;

/**
 * @author: whz
 * @date: 2019/8/10
 **/
public class Main21 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] number = new int[n];
        for (int i = 0; i < n; i++) {
            number[i] = scanner.nextInt();
        }
        System.out.println(lengthOfLIS(number));
    }
    public static int lengthOfLIS(int[] nums) {
        int[] top = new int[nums.length];
        int piles = 0;
        for (int i = 0; i < nums.length; i++) {
            int poker = nums[i];

            /***** 搜索左侧边界的二分查找 *****/
            int left = 0, right = piles;
            while (left < right) {
                int mid = (left + right) / 2;
                if (top[mid] > poker) {
                    right = mid;
                } else if (top[mid] < poker) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            if (left == piles) piles++;
            top[left] = poker;
        }
        return piles;
    }
}
