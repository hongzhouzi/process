package cn.examination.beike;

import java.util.Scanner;

/**
 * @author: whz
 * @date: 2019/8/10
 **/
public class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long arr[] = new long[n];
        for(int i=0 ;i <n; i++){
            arr[i] = in.nextInt();
        }

        int maxLen=0;
        for(int i=0; i<n; i++){
            long lastMin = arr[i];//初始化上一次最小的元素
            int len = 1;//自己的长度算一个
            for(int j=i; j<n; j++){
                if(arr[j] > lastMin){
                    lastMin = arr[j];
                    len++;
                }
            }
            //记录此趟循环，要求的最大的长度
            if(len >maxLen){
                maxLen = len;

            }
        }
        System.out.println(maxLen+1);
    }
}
