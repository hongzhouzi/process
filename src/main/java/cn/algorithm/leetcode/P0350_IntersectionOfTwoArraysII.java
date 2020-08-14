package cn.algorithm.leetcode;

import java.util.*;

/**
 * 350. 两个数组的交集 II
 * 给定两个数组，编写一个函数来计算它们的交集。
 * 示例 1：
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2,2]
 * 示例 2:
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[4,9]
 * 说明：
 * 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
 * 我们可以不考虑输出结果的顺序。
 * 进阶：
 * 如果给定的数组已经排好序呢？你将如何优化你的算法？
 * 如果 nums1 的大小比 nums2 小很多，哪种方法更优？
 * 如果 nums2 的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
 *
 * @author hongzhou.wei
 * @date 2020/7/13
 */
public class P0350_IntersectionOfTwoArraysII {
    public static void main(String[] args) {
        int nums1[] = new int[]{4, 9, 5};
        int nums2[] = new int[]{9, 4, 1005, 8, 4};
//        System.out.println(Arrays.toString(intersect(nums1, nums2)));
        System.out.println(Arrays.toString(intersect1(nums1, nums2)));
    }

    /**
     * 滑动窗口，处理前需要进行一次排序
     * 时间复杂度：O(n*logn)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    static public int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> ret = new LinkedList<>();
        int i = 0, j = 0;
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                ret.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            }
        }
        int re[] = new int[ret.size()];
        for (int k = 0; k < ret.size(); k++) {
            re[k] = ret.get(k);
        }
        return re;
    }

    /**
     * 滑动窗口优化，不使用额外的存储空间，比较了num1前面的数比较了之后就把需要返回的值暂存在num1前
     * 最后返回时使用Arrays.copyOfRange(nums1, from, to);返回指定范围的值即可
     *
     * @param nums1
     * @param nums2
     * @return
     */
    static public int[] intersect1(int[] nums1, int[] nums2) {
        // 排好序的写法
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        // 定义i，j两个指针，k用于记录交集的索引值
        int i = 0, j = 0, k = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                // 说明num1[i]较小，i右移
                i++;
            } else if (nums1[i] > nums2[j]) {
                // 说明nums2[j]较小，j右移
                j++;
            } else {
                // 两个元素相同，记录在k
                nums1[k++] = nums1[i];
                // 两个指针向前走一步
                i++;
                j++;
            }
        }
        return Arrays.copyOfRange(nums1, 0, k);
    }

    /**
     * 使用map处理，过程中若需要根据数组长度进行位置调换，可手动翻转一下调用入参
     *
     * @param nums1
     * @param nums2
     * @return
     */
    static public int[] intersect2(int[] nums1, int[] nums2) {
        // 这种方式就很nice
        if (nums1.length > nums2.length) {
            return intersect2(nums2, nums1);
        }
        HashMap<Integer, Integer> m = new HashMap<>();
        for (int n : nums1) {
            m.put(n, m.getOrDefault(n, 0) + 1);
        }
        int k = 0;
        for (int n : nums2) {
            int cnt = m.getOrDefault(n, 0);
            if (cnt > 0) {
                nums1[k++] = n;
                m.put(n, cnt - 1);
            }
        }
        return Arrays.copyOfRange(nums1, 0, k);
    }
}