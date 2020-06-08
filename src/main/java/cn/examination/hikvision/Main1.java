package cn.examination.hikvision;

public class Main1 {
    public static void main(String[] args) {
        int a[] = {2,5,9,12,15,59};
        System.out.println(find(a,9, 0, a.length-1));
    }
    static int find(int[] a, int val, int right, int left){
        int mid = (right+left)/2;
        if(a[mid] == val){
            return mid;
        }
        if(val < a[mid]){
            find(a, val, right, mid);
        }else {
            find(a, val, mid, left);
        }
        return -1;
    }
}
