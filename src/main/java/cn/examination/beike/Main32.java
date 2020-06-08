package cn.examination.beike;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author: whz
 * @date: 2019/8/10
 **/
public class Main32 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] number = new int[n];
        for (int i = 0; i < n; i++) {
            number[i] = scanner.nextInt();
        }
        Arrays.sort(number);
        int sign = 0;
        for (int i = 0; i < n; i++){
            for (int j = i+1; j < n; j++) {
                if (number[i] == number[j]) {
                    sign++;
                    continue;
                }
                if (Double.valueOf(number[i]) < Double.valueOf(number[j]) * Double.valueOf(0.9)) {
                    break;
                }else {
                    sign++;
                }
            }
        }
        System.out.println(sign);
    }
}
