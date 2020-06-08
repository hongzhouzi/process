package cn.interview.baodian;

/**
 * 一个整型数组里面，除了一个数字之外，其他数字都出现了两次，找出这个只出现了一次的数字
 * 思路：任何一个数字异或它自己都等于0。那些出现两次的数字全部在异或中会被抵消
 */
public class FindNotDouble {
    public static void main(String[] args) {
        int a[] = {1,2,3,2,4,3,5,4,1};
        System.out.println(find(a));
    }
    static int find(int a[]){
        int len = a.length;
        int result = a[0];
        for (int i=1; i<len; i++){
            result ^=a[i];
        }
        return result;
    }
}
