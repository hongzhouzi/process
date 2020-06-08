package cn.examination.beike;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author: whz
 * @date: 2019/8/10
 **/
public class Main22 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.valueOf(br.readLine().trim());
        long[] nums = new long[n];
        long[] dp = new long[n];
        for(int i = 0; i < n; i++ ) {
            long temp = Long.valueOf(br.readLine().trim());
            nums[i] = temp;
        }

        for(int i = 0; i < n; i++  ) {
            long max = 1;
            for(int j = 0; j < i; j++  ) {
                max = Math.max(max, dp[j]);
            }
            dp[i] = max;
        }
        System.out.println(Arrays.stream(dp).max().orElse(0));
    }
}
