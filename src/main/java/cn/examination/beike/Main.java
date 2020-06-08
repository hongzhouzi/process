package cn.examination.beike;

import java.util.Scanner;

/**
 * @author: whz
 * @date: 2019/8/10
 **/
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();//正整数n
        long arr[] = new long[n];
        for(int i=0; i< n; i++){
            arr[i] = Long.valueOf(in.next());
        }

        //标记最小的值位置
        int minLoc = 0;
        long minLocVal = Long.MAX_VALUE;//初始化

        //计算间隔绝对值最小的，值存入val中
        long val[] = new long[n-1];
        for(int i=0; i<n-1; i++){
            val[i] = Math.abs(arr[i] - arr[i+1]);
            if(val[i] < minLocVal){
                minLoc = i;
                minLocVal =val[i];
            }
        }
        System.out.println(arr[minLoc]+" "+arr[minLoc+1]);
    }
}
