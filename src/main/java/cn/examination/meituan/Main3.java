package cn.examination.meituan;

import java.util.Scanner;

/*
n个数的伪中位数定义为从小到大排序后第⌊(n+1)/2⌋个数。其中，⌊x⌋的意思是x向下取整。

现在，给你n个数，你需要向其中增加最少的数，使得k成为最后这一组数的伪中位数。

请问你需要加入数的最少数。

输入
输入第一行包含两个数n,k,意为原来数的个数和最后的伪中位数。

接下来一行n个数a_i，空格隔开，代表原来的数。

1≤n≤500,1≤a_i≤100000

输出
输出一个数，你需要加入数的最少数量。


样例输入
4 2
2 3 3 3
样例输出
2
 */
public class Main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // n行，每行m个代表各个单科成绩
        int n = sc.nextInt(), k = sc.nextInt();
        // 记录k目前的位置，从1开始。考虑多个相同值，记录最中间的那个
        int loc=0;
        int a[] = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
            if (a[i] == k) {
                loc = i + 1;
            }
        }
        // 计算x ,0<x<n
        for (int x =0; x < n; x++) {
            if((int) Math.floor((5 + x) / 2f) == loc+x){
                System.out.println(x);
                break;
            }
        }
    }
}
