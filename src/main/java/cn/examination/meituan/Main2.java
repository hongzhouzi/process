package cn.examination.meituan;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/*
数对是数学中一个重要的概念，类似于计算机中的pair，数对的性质如下：

每个数对(x,y)包含两个实数元素x,y，描述一对数之间的关系。两个数对比大小将先比较第一个数的大小，如果相同再比较第二个数的大小。

现在，有n个数（两两可能相同），他们之间两两将会形成n^2个数对（自己和自己也会形成数对）。我们希望知道，第k小的数对是哪一对数，并输出这一对。

输入
第一行包含两个数n,k，含义如题面所示

接下来一行n个整数，空格隔开。

输出
输出第k小的数对。格式如(x,y)，其中x为数对中第一个数，y为数对中第二个数


样例输入
3 4
3 1 2
样例输出
(2,1)

提示
n≤100000,1≤k≤n^2，这n个数在int范围内[-2147483648,2147483647]

样例解释
数对一共有9个，分别是： (3,3)(3,1)(3,2)(1,3)(1,1)(1,2)(2,3)(2,1)(2,2)
按从小到大的排序后：(1,1)(1,2)(1,3)(2,1)(2,2)(2,3)(3,1)(3,2)(3,3)
第4个为(2,1)
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // n行，每行m个代表各个单科成绩
        int n = sc.nextInt(), k = sc.nextInt();
        int data[] = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = sc.nextInt();
        }
        Arrays.sort(data);
        int count = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n; j++) {
                count++;
                if (count == k) {
                    System.out.println("(" + data[i] + "," + data[j] + ")");
                    break;
                }
            }
        }
    }
}
