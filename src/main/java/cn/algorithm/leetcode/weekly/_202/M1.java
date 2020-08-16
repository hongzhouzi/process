package cn.algorithm.leetcode.weekly._202;

public class M1 {
    public static void main(String[] args) {
//        int a []= {1,2,34,3,4,5,7,23,12};
        int a []= {2,6,4,1};
        System.out.println(threeConsecutiveOdds(a));

    }

    static public boolean threeConsecutiveOdds(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if((arr[i] & 1) ==1){
                count++;
                if(count >=3){
                    return true;
                }
            }else {
                count = 0;
            }
        }
        return count >= 3;
    }
}
