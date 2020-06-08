package cn.examination.vipkid;

import java.util.Scanner;

/**
 * @author: whz
 * @date: 2019/9/3
 **/
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();//正整数n
        System.out.println(countOne2(n));
    }
    static int countOne2(int n){
        int count = 0 ;
        while (n > 0){
            if(n != 0) {//判断最后一位是否为1
                n = n & (n - 1);//对n或运算（类似于自减）
                count++;
            }
        }
        return count;
    }
}
