package cn.algorithm.leetcode;

import java.util.Arrays;
import java.util.Random;

/**
 * 215. 数组中的第K个最大元素
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 * 示例 1:
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * <p>
 * 示例 2:
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * 说明:
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 *
 * @author hongzhou.wei
 * @date 2020/6/29
 */
public class P0215_kthLargestElementInAnArray {
    public static void main(String[] args) {
        int nums[] = new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k = 4;
//        int nums[] = new int[]{6};
//        int k = 1;
//        System.out.println(findKthLargest0(nums, k));
        System.out.println(new P0215_kthLargestElementInAnArray().findKthLargest(nums, k));
    }

    /**
     * 使用库函数排序，最后直接从数组中取对应的元素就OK了
     *
     * @param nums
     * @param k
     * @return
     */
    static public int findKthLargest0(int[] nums, int k) {
        if (nums.length == 1) {
            return nums[0];
        }
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * 快排思想，最多只需要找一半的就可以了，如果k大于数组长度的一半，就转成length-k找小的
     *
     * @param nums
     * @param k
     * @return
     */
    static public int findKthLargest1(int[] nums, int k) {
        if (nums.length == 1) {
            return nums[0];
        }
        int len = nums.length, left = 0, right = len - 1, pos = k;
        if (k < len / 2) {
            pos = len - k;
        }
        while (left < right) {
            while (left < right && nums[left] < nums[right]) {
                left++;
            }
        }
        return 0;
    }

   /* public int findKthLargest_1(int[] nums, int k) {
        int begin = 0;
        int end = nums.length - 1;
        int resIndex = 0;

        while(true){
            resIndex = partition(nums, begin, end);
            if(resIndex == nums.length - k){
                break;
            }else if(resIndex  < nums.length - k){
                begin = resIndex + 1;
            }else{
                end = resIndex - 1;
            }
        }

        return nums[resIndex];
    }

    public int partition(int[] num, int begin, int end){
        int pivot = num[begin];
        int pos = begin;
        for(int cur = begin +1 ; cur <= end; cur++){
            if(num[cur] < pivot){
                pos++;
                swap(num, cur, pos);
            }
        }
        swap(num, begin, pos);
        return pos;
    }

    public void swap(int[] num, int i, int j){
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }*/


    // =================================快速选择算法，LeetCode参考=======================================

    Random random = new Random();

    /**
     * 时间复杂度：O(n)，证明过程可以参考「《算法导论》9.2：期望为线性的选择算法」。
     * 空间复杂度：O(log n)，递归使用栈空间的空间代价的期望为 O(log n)。
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public int quickSelect(int[] a, int l, int r, int index) {
        int q = randomPartition(a, l, r);
        // 找到了第k大的元素就直接返回，未找到就继续在子区间找
        if (q == index) {
            return a[q];
        } else {
            return q < index ? quickSelect(a, q + 1, r, index) : quickSelect(a, l, q - 1, index);
        }
    }

    /**
     * 在左右两边中的数随机选择一个位置，保证
     *
     * @param a
     * @param l
     * @param r
     * @return
     */
    public int randomPartition(int[] a, int l, int r) {
        // 生成l到r区间内的随机数，并将左边的数和随机的位置交换
        int i = random.nextInt(r - l + 1) + l;
        swap(a, i, r);
        // 让随机位置的数和右边的数做比较
        return partition(a, l, r);
    }

    /**
     * 交换位置，左边位置的数（随机位置）和最右边的数比较，左边位置的数都小于最右边的数
     *
     * @param a
     * @param l
     * @param r
     * @return
     */
    public int partition(int[] a, int l, int r) {
        int x = a[r], i = l - 1;
        for (int j = l; j < r; ++j) {
            if (a[j] <= x) {
                swap(a, ++i, j);
            }
        }
        swap(a, i + 1, r);
        return i + 1;
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // ================================基于堆排序的选择方法==========================
    /*
    堆其实就是利用完全二叉树的结构来维护的一维数组
    大顶堆：每个结点的值都大于或等于其左右孩子结点的值
    小顶堆：每个结点的值都小于或等于其左右孩子结点的值
    （堆的这种特性非常的有用，堆常常被当做优先队列使用，因为可以快速的访问到“最重要”的元素）
     */

    public int findKthLargest2(int[] nums, int k) {
        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= nums.length - k + 1; --i) {
            swap(nums, 0, i);
            --heapSize;
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    /**
     * 构建大顶堆
     * @param a
     * @param heapSize
     */
    public void buildMaxHeap(int[] a, int heapSize) {
        for (int i = heapSize / 2; i >= 0; --i) {
            maxHeapify(a, i, heapSize);
        }
    }

    public void maxHeapify(int[] a, int i, int heapSize) {
        int l = i * 2 + 1, r = i * 2 + 2, largest = i;
        if (l < heapSize && a[l] > a[largest]) {
            largest = l;
        }
        if (r < heapSize && a[r] > a[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(a, i, largest);
            maxHeapify(a, largest, heapSize);
        }
    }
}
