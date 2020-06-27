package cn.algorithm.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素
 * 的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的
 * 第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
 * 示例 1:
 * 输入: [1,2,1]
 * 输出: [2,-1,2]
 * 解释: 第一个 1 的下一个更大的数是 2；
 * 数字 2 找不到下一个更大的数；
 * 第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
 * 注意: 输入数组的长度不会超过 10000。
 *
 * @author hongzhou.wei
 * @date 2020/6/27
 */
public class P0503_nextGreaterElementII {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 1};
//        int[] nums = new int[]{5, 4, 3, 2, 1};
        System.out.println(Arrays.toString(nextGreaterElements1(nums)));
    }

    static public int[] nextGreaterElements(int[] nums) {
        // 1.声明栈、结果集
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[nums.length];
        // 因为有循环，把最后一个元素的下标先入栈，将最后一个作为第一个
        int source[] = new int[nums.length * 2];
        for (int i = 0; i < source.length; i++) {
            source[i] = nums[i % nums.length];
        }
        // 2.遍历数组（或线性结构的容器）
        for (int i = 0; i < source.length; i++) {
            // 3.单调栈相关处理
            // 3.1 判断当前元素入栈前栈中是否有元素可以处理了
            while (!stack.empty() && source[i] > source[stack.peek()]) {
                // 3.2 将现在能处理的元素取出(这步维护着栈的单调性)
                int index = stack.pop();
                // 3.3 计算取出元素对应的值
                if (index >= nums.length) {
                    continue;
                }
                result[index] = source[i];
            }
            // 3.4 当前下标入栈，若当前是最后一个元素就跳过，因为最开始已经处理了
            stack.push(i);
        }
        // 3.5 处理栈中没有处理完元素对应的结果值（如果该值在初始化结果集时处理了这儿就不用处理）
        while (!stack.empty()) {
            int prevIndex = stack.pop();
            if (prevIndex >= nums.length) {
                continue;
            }
            result[prevIndex] = -1;
        }
        return result;
    }

    /**
     * 和非循环的相比，这个需要有两次入栈操作，第二次入栈时处理下标时要模上数组长度
     *
     * @param nums
     * @return
     */
    static public int[] nextGreaterElements1(int[] nums) {
        // 1.声明栈、结果集
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[nums.length];
        Arrays.fill(result, -1);
        // 2.遍历数组（或线性结构的容器）
        for (int i = 0,numsLength = nums.length; i < numsLength * 2; i++) {
            // 3.单调栈相关处理
            // 3.1 判断当前元素入栈前栈中是否有元素可以处理了
            while (!stack.empty() && nums[i % numsLength] > nums[stack.peek()]) {
                // 3.2 将现在能处理的元素取出(这步维护着栈的单调性)
                int index = stack.pop();
                // 3.3 计算取出元素对应的值
                result[index] = nums[i % numsLength];
            }
            // 3.4 当前下标入栈，循环处理需要模上数组长度
            stack.push(i % numsLength);
        }
        return result;
    }
}
