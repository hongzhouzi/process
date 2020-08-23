package cn.algorithm.leetcode.weekly._203;

public class M3 {
    public static void main(String[] args) {
        /*int []arr = {3,5,1,2,4};
        int m = 1;*/
        /*int []arr = {3,1,5,4,2};
        int m = 2;*/
        int []arr = {2,1};
        int m = 2;
        System.out.println(findLatestStep(arr,m));
    }

    static public int findLatestStep(int[] arr, int m) {
        int len  = arr.length;
        StringBuilder sb = new StringBuilder();
        for (int i = len; i > 0; i--) {
            sb.append('0');
        }
        for (int i = 0; i < len; i++) {
            sb.setCharAt(arr[i]-1,'1');
        }
        int count = 1;
        for (int i = 1; i < len; i++) {
            if(sb.charAt(i) != sb.charAt(i-1) && sb.charAt(i)=='1'){
                count++;
            }
        }
        if(arr[0]=='0' || arr[len-1] =='0' || count !=m){
            return -1;
        }
        return arr[len-1];
    }
}
