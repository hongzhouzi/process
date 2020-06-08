package cn.examination.meituan;

import java.util.Scanner;

/*
有这么一段伪代码
input a,b,m,x
while true:
  x=(a*x+b)%m
  print(x)
end while

输出的x由于是在取模意义下的，所以会出现循环。
比如，a=2, b=1, m=5, x=2的时候，输出的序列将会如下：
0,1,3,2,0,1,3,2,0,1,3,2....
其中：0,1,3,2 称为最短的循环节。
现在给定a,b,m,x的值，请你计算最短循环节的长度。
肯定小于模长
 */
public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt(), b = sc.nextInt(), m = sc.nextInt(), x = sc.nextInt();
        int arr[] = new int[2 * m];
        int i = 0;
        while (i < 2 * m) {
            x = (a * x + b) % m;
            arr[i++] = x;
        }
        int slow = 0, fast = 1;
        while (fast < 2 * m){
            if(arr[fast] == arr[slow]){
                break;
            }
            fast ++;
        }
        System.out.println(fast );
    }
}
