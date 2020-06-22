package cn.algorithm.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，
 * 可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Marcos 贡献此图。
 * <p>
 * 示例:
 * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]；输出: 6
 *
 * @author hongzhou.wei
 * @date 2020/6/21
 */
public class P0042_trappingRainWater {
    public static void main(String[] args) {
        int[] temperatures = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2,1};
        System.out.println(trap(temperatures));

    }

    /**
     * 接的雨水量取决于水洼的大小，而水洼的大小由两边最高
     * 点和中间最低减去挖槽高度，再乘以左右边际的宽度决定。
     * 所以我们可以构造一个单调栈(递减)，遇到非递减就计算出当前水平面容纳水的大小，
     * 最后依次将这些大小相加就能得到总的容量大小。
     * 注意这儿将每个水洼的容量按照水平面拆分成若干个矩形计算，水平面取决于每个递增和递减值
     * 之间的最大高度决定。
     *
     * @param height
     * @return
     */
    static public int trap(int[] height) {
        // 1.声明栈、结果集
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        // 2.遍历数组（或线性结构的容器）
        for (int i = 0; i < height.length; i++) {
            // 3.单调栈相关处理（构造一个单调递减栈）
            // 3.1 判断当前元素入栈前栈中是否有元素可以处理了
            while (!stack.empty() && height[i] > height[stack.peek()]) {
                // 3.2取出栈顶元素，当做中间低处的洼槽（维护着单调性）
                int bottom = stack.pop();
                // 需要有栈是否为空的判断，防止没有左边界时抛异常
                if(stack.isEmpty()){
                    continue;
                }
                int left = stack.peek();
                // 3.3 计算当前柱子与前面柱子围的洼槽容积，
                // 取两边短中的那个柱子高度减去洼槽高度作为高，下标距离-1作为宽
                result += (Math.min(height[i], height[left]) - height[bottom]) * (i - left - 1);
            }
            // 当前元素下标入栈
            stack.push(i);
        }
        return result;
    }
}
