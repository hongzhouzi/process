package cn.algorithm.leetcode.weekly._202;

public class M2 {
    public static void main(String[] args) {

        System.out.println(minOperations(6));
    }


    static public int minOperations(int n) {
        int mid = (2* (n/2))+1;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if(i!= (n/2)){
                sum+= Math.abs((2 * i) + 1 -mid);
            }
        }
        return sum /2;
    }
}
