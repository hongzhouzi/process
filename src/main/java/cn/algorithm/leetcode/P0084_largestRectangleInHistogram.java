package cn.algorithm.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * 以上是柱状图的示例，其中每个柱子的宽度为 1，给定的高度为 [2,1,5,6,2,3]。
 * 图中阴影部分为所能勾勒出的最大矩形面积，其面积为 10 个单位。
 * <p>
 * 示例:
 * 输入: [2,1,5,6,2,3]
 * 输出: 10
 * <p>
 * 思路：
 * <p>
 * 暴力：当前的和之前所有的做比较，找到最短的板块计算能构成的面积和遍历到当前最大的做比较
 * 时间复杂度：O(n^2)，空间复杂度：O(1)
 * <p>
 * 单调栈：只要是单调递减就说明面积会逐渐变小，逐渐递增就说明面试可能会逐渐增大，计算面积时
 *
 * @author hongzhou.wei
 * @date 2020/6/24
 */
public class P0084_largestRectangleInHistogram {
    public static void main(String[] args) {
        int heights[] = new int[]{2, 1, 5, 6, 2, 3};
        System.out.println(largestRectangleArea(heights));
    }

    static public int largestRectangleArea(int[] heights) {
        // 1.声明栈、结果集
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        // 2.遍历数组（或线性结构的容器）
        for (int i = 0; i < heights.length; i++) {
            // 3.单调栈相关处理
            // 3.1 判断当前元素入栈前栈中是否有元素可以处理了
            while (!stack.empty() && heights[i] > heights[stack.peek()]) {
                // 3.2 将现在能处理的元素取出(这步维护着栈的单调性)
                int index = stack.pop();
            }
            // 3.4 当前下标入栈
            stack.push(i);
        }
        // 3.5 处理栈中没有处理完元素对应的结果值（如果该值在初始化结果集时处理了这儿就不用处理）
        while (!stack.empty()){
            int prevIndex = stack.pop();
        }
        return result;
    }


    /**
     * 参考思路：
     * 1.求出每个柱子左右两边的紧邻的小于其高度的柱子（单调栈）
     * 2.对每个柱子取左右两边高度低的那个计算出当前柱子能和左右两边柱子构成的最大面积
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea_(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];

        Stack<Integer> mono_stack = new Stack<Integer>();
        for (int i = 0; i < n; ++i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                mono_stack.pop();
            }
            left[i] = (mono_stack.isEmpty() ? -1 : mono_stack.peek());
            mono_stack.push(i);
        }

        mono_stack.clear();
        for (int i = n - 1; i >= 0; --i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                mono_stack.pop();
            }
            right[i] = (mono_stack.isEmpty() ? n : mono_stack.peek());
            mono_stack.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }
    public int largestRectangleArea__(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);

        Stack<Integer> mono_stack = new Stack<Integer>();
        for (int i = 0; i < n; ++i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                right[mono_stack.peek()] = i;
                mono_stack.pop();
            }
            left[i] = (mono_stack.isEmpty() ? -1 : mono_stack.peek());
            mono_stack.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }

}