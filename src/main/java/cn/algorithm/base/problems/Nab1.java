package cn.algorithm.base.problems;

import java.util.Scanner;

/**
 * 求解质因数-穷举法
 *
 * @author whz
 * @version on:2019-4-25 下午4:59:03
 */
public class Nab1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt();
            if (N == 0) {
                System.out.println("无数解！");
                continue;
            }
            p(N);
        }
    }

    //穷举法
    static void p(int n) {
        for (int a = 0; a <= (n + 1) / 2; a++) {
            for (int b = 0; b <= a - 1; b++) {
                if (a * a - b * b == n) {
                    System.out.println("a:" + a + " b:" + b);
                }
            }
        }
        System.out.println("若啥都没输出，就表示无解！");
    }
}
