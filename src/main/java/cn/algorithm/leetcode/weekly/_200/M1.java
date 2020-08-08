package cn.algorithm.leetcode.weekly._200;

public class M1 {
    public static void main(String[] args) {
        int arr[] = new int[]{3,0,1,1,9,7}, a = 7, b = 2, c = 3;
        System.out.println(new M1().countGoodTriplets(arr,a,b,c));

    }
    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                for (int k = j+1; k < arr.length; k++) {
                    if(Math.abs(arr[i] -arr[j]) <= a
                    && Math.abs(arr[j] -arr[k])<= b
                    && Math.abs(arr[k] -arr[i])<= c
                            ){
                        count++;
                    }
                }
            }

        }
        return count;
    }
}
