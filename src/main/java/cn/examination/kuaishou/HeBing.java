package cn.examination.kuaishou;

import java.util.Scanner;

/**
 * @author: whz
 * @date: 2019/8/25
 **/
public class HeBing {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.nextLine();
        String b = in.nextLine();
        String[] a1 = a.split(" ");
        String[] b1 = b.split(" ");
        StringBuilder sum = new StringBuilder(a1.length+b1.length);
        int j=0;
        if(a1.length<4){
            for(int i=0; i<a1.length;i++)
                sum.append(a1[i]+" ");
        }
        for(int i=0; i<a1.length; i = i+4,j++){
            sum.append(a1[i]+" ");
            if(a1.length == i+1)
                break;
            sum.append(a1[i+1]+" ");

            if(a1.length == i+2)
                break;
            sum.append(a1[i+2]+" ");

            if(a1.length == i+3)
                break;
            sum.append(a1[i+3]+" ");
            sum.append(b1[j]+" ");
        }
        if(j<b1.length){
            while (j<b1.length-1){
                sum.append(b1[j++]+" ");
            }
            sum.append(b1[j++]);
        }
        System.out.println(sum);
    }
}
