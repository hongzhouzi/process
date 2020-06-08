package cn.algorithm.base.sort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author whz
 * @date 2019/8/5
 */
public class QuickSort {
    public static void main(String[] args) {
//        int[] a = {1, 2, 4, 5, 7, 4, 5 ,3 ,9 ,0};
       /* int[] a ={6,1,2,7,9};
        System.out.println(Arrays.toString(a));
//        quickSort(a, 0, a.length-1);
        quickSortMid(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));*/

        List<byte[]> list = new ArrayList<>();
        int i=0;
        while(true){
            list.add(new byte[5*1024*1024]);
            System.out.println("分配次数："+(++i));
        }
    }
    static void quickSort(int[] data, int left, int right){
        //1.递归出口
        if(left > right){
            return;
        }
        //2.标兵指针，基准位置为左边，那么在移动标兵时就需要从右往左找
        int pivot = right;
        int i = left, j = right, pivotKey = data[pivot];
        //3.完成一趟排序，直到指针i和j重合为止
        while(i < j){
            //3.1从右往左找  <----
            while (data[j] > pivotKey && i < j){
                j--;
            }
            //3.2从左往右找  --->
            while (data[i] <= pivotKey && i < j){
                i++;
            }
            //3.3交换
            if(i < j){
                int t = data[i];
                data[i] = data[j];
                data[j] = t;
            }
        }
        //4.调整key的位置
        int t = data[pivot];
        data[pivot] = data[i];
        data[i] = t;
        //------------------------------------------
        System.out.println(Arrays.toString(data));
        //5.递归对key左边和右边快排
        quickSort(data, left, i-1);
        quickSort(data, i+1, right);
    }
    /**
     *  给变量i,j起名"哨兵i,j"。如果先出动哨兵i，显然哨兵i先到达“相遇数”，因为哨兵i只会在大于基准数的位置停下，
     *  这就意味着“相遇数”可能大于基准数，那么交换后左边序列最左边的元素就可能大于归位后的基准数了，与快排一趟结束后，基准数大于左边子序列元素矛盾。
     *  所以考虑到相遇数可能大于基准数，就需要让哨兵j先出发。[4,1,2,3]这种相遇数小于基准数，[4,1,2,5,9]，这种情况相遇数大于基准数
     *  另外在中间取基准值时，标兵在移动过程中，取“=”的情况需要放在下面。不能让先出发的结果碰到自己的等号就停下了
     */

    static void quickSortMid(int []data, int left, int right){
        //1.递归出口
        if(left > right) return;
        //2.
        int midPivot = (left + right)/2;
        int i = left, j = right, pivotKey = data[midPivot];
        //3.一趟排序
        while (i != j){
            //3.1 <---移动
            while (data[j] > pivotKey && i < j){
                j--;
            }
            //3.2 --->移动
            while (data[i] <= pivotKey && i <j){
                i++;
            }

            //3.3交换位置
            if(i < j){
                int p = data[i];
                data[i] = data[j];
                data[j] = p;
            }
        }
        //4.交换基准元素，此时i和j相等
        int p = data[midPivot];
        data[midPivot] = data[i];
        data[i] = p;
        System.out.println(Arrays.toString(data));
        //5.递归调用
        quickSortMid(data, left, i - 1);
        quickSortMid(data,i + 1, right);
    }

    /*private static void quickSort(int[] a, int low, int high) {
        //1,找到递归算法的出口
        if( low > high) {
            return;
        }
        //2, 存
        int i = low;
        int j = high;
        //3,key
        int key = a[ low ];
        //4，完成一趟排序
        while( i< j) {
            //4.1 ，从右往左找到第一个小于key的数
            while(i<j && a[j] > key){
                j--;
            }
            // 4.2 从左往右找到第一个大于key的数
            while( i<j && a[i] <= key) {
                i++;
            }
            //4.3 交换
            if(i<j) {
                int p = a[i];
                a[i] = a[j];
                a[j] = p;
            }
        }
        // 4.4，调整key的位置
        int p = a[i];
        a[i] = a[low];
        a[low] = p;
        //5, 对key左边的数快排
        quickSort(a, low, i-1 );
        //6, 对key右边的数快排
        quickSort(a, i+1, high);
    }*/

}

