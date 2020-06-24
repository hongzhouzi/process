package cn.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 给定两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。
 * 找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
 * nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个
 * 比 x 大的元素。如果不存在，对应位置输出 -1 。
 * 示例 1:
 * 输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * 输出: [-1,3,-1]
 * 解释:
 *     对于num1中的数字4，你无法在第二个数组中找到下一个更大的数字，因此输出 -1。
 *     对于num1中的数字1，第二个数组中数字1右边的下一个较大数字是 3。
 *     对于num1中的数字2，第二个数组中没有下一个更大的数字，因此输出 -1。
 * 示例 2:
 * 输入: nums1 = [2,4], nums2 = [1,2,3,4].
 * 输出: [3,-1]
 * 解释:
 *     对于 num1 中的数字 2 ，第二个数组中的下一个较大数字是 3 。
 *     对于 num1 中的数字 4 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
 *
 * @author hongzhou.wei
 * @date 2020/6/24
 */
public class P0496_nextGreaterElementI {
    public static void main(String[] args) {
        int [] nums1 = new int[]{2,4};
        int [] nums2 = new int[]{1,2,3,4};
        System.out.println(Arrays.toString(nextGreaterElement(nums1,nums2)));
    }

    static public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // 1.声明栈、结果集
        Stack<Integer> stack = new Stack<>();
        Map<Integer,Integer> resMap = new HashMap<>();
        // 2.遍历数组（或线性结构的容器）
        for (int i = 0; i < nums2.length; i++) {
            // 3.单调栈相关处理
            // 3.1 判断当前元素入栈前栈中是否有元素可以处理了
            while (!stack.empty() && nums2[i] > nums2[stack.peek()]) {
                // 3.2 将现在能处理的元素取出(这步维护着栈的单调性)
                int index = stack.pop();
                // 3.3 计算取出元素对应的值
                resMap.put(nums2[index],nums2[i]);
            }
            // 3.4 当前下标入栈
            stack.push(i);
        }
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] =  resMap.getOrDefault(nums1[i],-1);
        }
        return nums1;
    }
}
