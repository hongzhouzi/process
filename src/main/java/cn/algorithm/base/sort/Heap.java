package cn.algorithm.base.sort;

import java.util.Arrays;

/**
 * @author: whz
 * @date: 2019/8/12
 **/
public class Heap {
    public static void main(String[] args) {
        int a[] = {1,3,5,9,8,7,4,6};
//        buildHeap(a);
        headSort(a);
        System.out.println(Arrays.toString(a));


    }

    /**
     * 小根堆，“上浮”操作
     * @param arr
     */
    static void upAdjust(int [] arr){
        int childIndex = arr.length - 1;
        int parentIndex = (childIndex - 1) /2;
        int childValue = arr[childIndex];
        while (childIndex > 0 && childValue <arr[parentIndex]){
            //先单向交换，因为最后才把childValue放到它合适的地方，这儿单向交换就OK
            arr[parentIndex] = arr [childIndex];
            childIndex = parentIndex;
            parentIndex = (childIndex - 1) / 2;
        }
        //上面调整完了之后，把childValue放到它合适的位置去
        arr[childIndex] = childValue;
    }

    /**
     * 小根堆，“下沉”操作
     * @param arr
     * @param parentIndex
     * @param len
     */
    static void downAdjust(int []arr, int parentIndex, int len){
        int childIndex = parentIndex*2 + 1;
        int parentValue = arr[parentIndex];
        while (childIndex < len){
            //在堆范围内，若有右孩子，且右孩子的值小于左孩子的值则定位到右孩子
            if(childIndex < len-1 && arr[childIndex] > arr[childIndex+1]){
                childIndex++;
            }
            //若父节点小于任何一个孩子的值，则直接跳出
            if(parentValue <= arr[childIndex]){
                break;
            }
            //单向交换
            arr[parentIndex] = arr[childIndex];
            parentIndex = childIndex;
            childIndex = parentIndex*2 + 1;
        }
        //经过上面的调整后，把parentValue放到它合适的位置去
        arr[parentIndex] = parentValue;
    }

    /**
     * 构建堆
     * @param arr
     */
    static void buildHeap(int []arr){
        //从最后一个非叶子节点开始，依次做下沉操作
        for(int i=(arr.length - 2)/2; i>=0; i--){
            downAdjust(arr, i, arr.length);
        }
    }

    static void headSort(int []arr){
        //先构建堆
        buildHeap(arr);
        //将
        for(int i = arr.length-1; i>0; i--){
            //最后一个元素和第一个元素交互，然后调整堆
            arr[i] = arr[i] ^ arr[0];
            arr[0] = arr[i] ^ arr[0];
            arr[i] = arr[i] ^ arr[0];
            /*int t = arr[i];
            arr[i] = arr[0];
            arr[0] = t;*/
            downAdjust(arr, 0, i);
        }
    }
}

