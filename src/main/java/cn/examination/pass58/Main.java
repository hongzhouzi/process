package cn.examination.pass58;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.next();
        String arr[] = str.split(",");
        int count = 1;
        for(int i=1; i<arr.length; i++){
            if(!arr[i].equals(arr[i-1])){
                count++;
            }
        }
        System.out.println(count);
    }
}
