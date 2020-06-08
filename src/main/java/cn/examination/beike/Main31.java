package cn.examination.beike;

import java.util.Scanner;

/**
 * @author: whz
 * @date: 2019/8/10
 **/
public class Main31 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int arr[] = new int[n];
        for(int i=0; i< n; i++){
            arr[i] = Integer.valueOf(in.next());
        }

        int count =0;
        for(int i=0; i<n-1; i++){
            for(int j=i+1; j<n; j++){
//                boolean f = arr[i] > arr[j] ? arr[i]*0.9 <= arr[j] : arr[j]*0.9 <= arr[i];
                if(arr[i] > arr[j] ? arr[i]*0.9 <= arr[j] : arr[j]*0.9 <= arr[i]){
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
