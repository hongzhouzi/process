package cn.examination.pass58;

import java.util.Arrays;
import java.util.Scanner;

/**
 6
 3
 6
 3
 5
 6
 2
 */
public class Main3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int a[] = new int[m];
        for(int i=0 ; i<m; i++){
            a[i]=in.nextInt();
        }
        //全初始为1
        int num[] = new int[m];
        for(int i=0; i<m; i++){
            num[i] = 1;
        }
        int i=0;
        while (i<m-1){
            if(i==0 ){
                if(a[i] >a[i+1]){
                    num[i] = num[i+1]+1;
                }
                i++;
                continue;
            }
            if((a[i] > a[i+1]) &&(a[i] > a[i-1]) ){
                num[i] = Math.max(num[i-1], num[i+1])+1;
                i++;
                continue;
            }
            if(((a[i] > a[i+1]) &&(a[i] < a[i-1])) || ((a[i] < a[i+1]) &&(a[i] > a[i-1]))){
                num[i] = Math.min(num[i-1], num[i+1])+1;
            }
            i++;
        }
        int sum=0;
        for(int j=0; j<m; j++){
            sum += num[j];
        }
        System.out.println(Arrays.toString(num));
        System.out.println(sum);
    }
}
