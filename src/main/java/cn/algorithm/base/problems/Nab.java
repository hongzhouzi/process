package cn.algorithm.base.problems;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


/**
 * 求解质因数-因式分解
 *
 * @author whz
 * @version on:2019-4-6 下午03:43:07
 */
public class Nab {
    private static int N;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            N = sc.nextInt();
            List<Integer> primeFactorList = primeFactor(N);
//			System.out.println(primeFactorList);
            if (primeFactorList.size() > 1) {
                //可分解为多个质因数
                //从前面取一个出来，计算是否满足，若不满足再继续往后尝试，满足就打印结果
                int factor1 = primeFactorList.get(1);
                int factor2 = N / factor1;
                //能整除的情况，保证a和b为整数
                if (judge(factor1, factor2)) {
                    if (factor1 < factor2) {
                        int tmp = factor1;
                        factor1 = factor2;
                        factor2 = tmp;
                    }
                    print(factor1, factor2);
                } else {
                    //需要继续在list中组合，寻找有没有满足的解，若找完了都没有满足的解就无解
                    //to do ………
                }
            } else {
                //不能分解为多个质因数
                if (judge(N, 1)) {
                    //能整除的情况，保证a和b为整数
                    print(N, 1);
                } else {
                    //不能整除的情况，a和b不为整数
                    System.out.println("无解！");
                }
            }
        }
    }

    /**
     * 将N分解质因数
     *
     * @param N
     * @return N的质因数list
     */
    public static List<Integer> primeFactor(int n) {
        //声明list，因子不定长度，LinkedList插入效率高
        List<Integer> list = new LinkedList<Integer>();
        //从最小的质因数开始分解
        for (int i = 2; i <= n; i++) {
            while (n != i) {
                if (n % i != 0) {
                    break;
                }
                list.add(Integer.valueOf(i));
                n = n / i;
            }
        }
        list.add(n);
        return list;
    }

    /**
     * 判断a+b能否整除于2
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean judge(int a, int b) {
        if ((a + b) % 2 == 0 && (a - b) % 2 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据两个因子求出解，并打印出来
     *
     * @param factor1
     * @param factor2
     */
    public static void print(int factor1, int factor2) {
        int a = (factor1 + factor2) / 2;
        int b = (factor1 - factor2) / 2;
        System.out.println("a:" + a + " b:" + b);
        System.out.println(N + "=" + "(" + a + "+" + b + ")*(" + a + "-" + b + ")");
        System.out.println(N + "=  " + (a + b) + " * " + (a - b) + "");
    }
}