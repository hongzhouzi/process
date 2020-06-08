package cn.algorithm.base.sort;

import java.util.Arrays;

/**
 * @author whz
 * @date 2019/8/5
 */
public class MergeSort {
    public static void main(String[] args) {
        int [] data = {5,6,3,2,4,9,1};
        int temp[] = new int[data.length];
        divide(data, temp,0, data.length-1);
        System.out.println(Arrays.toString(data));
    }

    /**
     * 递归划分数据
     * @param data 需要排序的原始数据
     * @param temp 向上传递数据
     * @param left 最左
     * @param right 最右
     */
    static void divide(int data[], int temp[], int left, int right){
        if(left < right){
            int middle = (left+right) / 2;
            divide(data, temp, left, middle);
            divide(data, temp,middle + 1, right);
            merge(data, temp, left, middle, right);
        }

    }

    static void merge(int data[], int temp[], int left, int middle, int right){
        int i = left, j = middle + 1, k=0;
        //需要把=也取到，因为要把数组中的元素都找完，另外j=middle+1,因为上面(left+right)/2之后有为0的情况
        while(i <= middle &&  j <= right){
            //双指针在左右两边中选依次选最小的
            if(data[i] <= data[j]){
                temp[k++] = data[i++];
            }else{
                temp[k++] = data[j++];
            }
        }
        //若左边的数已添加完，右边剩余的数都依次添加进去
        while (i <= middle){
            temp[k++] = data[i++];
        }
        //若右边的数已添加完，左边剩余的数都依次添加进去
        while(j <= right){
            temp[k++] = data[j++];
        }
        //把数据复制回原数组
        for( i=0; i < k; i++){
            data[left+i] = temp[i];
        }
    }
}

