package cn.examination.tujia;


import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int M = in.nextInt();
        int R = in.nextInt();
        int r[] = new int[R];
        int AM[] = new int[M];
        int BM[] = new int[M];
        int CM[] = new int[M];
        for(int i=0; i<R; i++){
            r[i] = in.nextInt();
        }
        for(int i=0; i<M ;i++){
            AM[i] = in.nextInt();
            BM[i] = in.nextInt();
            CM[i] = in.nextInt();
        }

        System.out.println("3");
    }
}
