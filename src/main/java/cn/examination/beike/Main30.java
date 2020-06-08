package cn.examination.beike;

import java.util.Scanner;

/**
 * @author: whz
 * @date: 2019/8/10
 **/
public class Main30 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long arr[] = new long[n];
        boolean flag= false;
        for(int i=0; i< n; i++){
            arr[i] = Long.valueOf(in.next());
            if(i>0 && arr[i]!=arr[i-1]){
                flag = true;
            }
        }



        int count =0;
        for(int i=0; i<n-1; i++){
            for(int j=i+1; j<n; j++){
                if(Math.max(arr[i], arr[j])*0.9 <= Math.min(arr[i], arr[j])){
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
