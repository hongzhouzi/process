package cn.interview.baodian;

import java.util.Arrays;

/**
 * 将数组循环右移k位，[1,2,3,4,5,6,7,8] => [7,8,1,2,3,4,5,6]
 * 采用分段逆序的思想
 */
public class ArraryRightMove {
    public static void main(String[] args) {
        int a[] = {1,2,3,4,5,6,7,8};
        shiftK(a, 2);
        System.out.println(Arrays.toString(a));
    }
    static void shiftK(int a[], int k){
        int len = a.length;
        k = k % len;
        //左移k位
        /*reverse(a, 0, k-1);
        reverse(a, k, len-1);
        reverse(a, 0, len-1);*/
        //右移k位
        reverse(a, 0, len-k-1);
        reverse(a, len-k, len-1);
        reverse(a,0, len-1);
    }
    static void reverse(int a[], int s, int e){
        while (s < e){
            a[s] = a[s] ^ a[e];
            a[e] = a[s] ^ a[e];
            a[s] = a[s] ^ a[e];
            s++;
            e--;
        }
    }
}
