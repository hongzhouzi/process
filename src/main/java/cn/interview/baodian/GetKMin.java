package cn.interview.baodian;

import java.util.Arrays;

/**
 * 找数组中第k个最小的数，用快排思想实现
 */
public class GetKMin {
    public static void main(String[] args) {
        int a[] = {1,5,2,6,8,0,6};
        System.out.println(getKMin(a,0,a.length-1,4));
//        System.out.println(getKMin2(a,0,a.length-1,4));
        System.out.println(Arrays.toString(a));
    }


    static int getKMin2(int a[], int s, int e, int k){
        if(s > e){
            return -1;
        }
        int i=s+1, j=e, temp= a[i];
        while (i<j){
            while (i<j && a[j] >= temp){
                j--;
            }
            //交换i，j
            if(i<j)
                a[i++] = a[j];
            while ((i<j) && a[i]<temp ){
                i++;
            }
            //交换i，j
            if(i<j)
                a[j--] = a[i];
        }
        a[i] = temp;
        if(i+1 == k){
            return temp;
        }else if(i+1 >k){
            return getKMin2(a, s, i-1, k);
        }else
            return getKMin2(a, i+1, e,k);
    }

    static int getKMin(int a[], int s, int e, int k){
        if(s > e){
            return -1;
        }
        int pivot = s;
        int i = s, j = e;
        while (i < j){
            while (a[pivot] < a[j] && i<j){
                j--;
            }
            while (a[pivot] >= a[i] && i<j){
                i++;
            }
            if(i<j){
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        int t = a[i];
        a[i] = a[pivot];
        a[pivot] = t;
        if(i+1 == k){
            return a[i];
        }else if(i+1 < k){
            return getKMin(a, i+1, a.length-1,k);
        }else
            return getKMin(a, 0, i-1,k);

        /*getKMin(a, s, i-1,k);

        getKMin(a, i+1, e,k);
        return 0;*/
    }

}
