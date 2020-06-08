package cn.examination.pass58;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int n = in.nextInt();
        int a[][] = new int[m][n];
        for(int i=0 ; i<m; i++){
            for(int j=0; j<n; j++){
                a[i][j]=in.nextInt();
            }
        }

        int i=m-1, j=n-1, sum = a[0][0] + a[m-1][n-1];
        while (i!=0 && j!=0){
            if((j>0 && i>0) && (a[i][j-1] < a[i-1][j])){
                sum += a[i][j-1];
                j--;
            }else {
                sum += a[i-1][j];
                i--;
            }
        }
        System.out.println(sum);
    }
}
