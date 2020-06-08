package cn.interview.baodian;

public class ArraysMaxByRecursion {
    public static void main(String[] args) {
        int a[] = {1,5,2,6,8,0,6};
        System.out.println(f(a,0));
    }
    static int f(int a[], int len){
        if(a.length-1 == len){
            return a[len];
        }
        //从第一个开始，递归过程总是返回较大的值
        return Math.max(a[len], f(a, len+1));
    }
}
