package cn.algorithm.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * 请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，
 * 至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，
 * 你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
 * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，
 * 都是在 [30, 100] 范围内的整数。
 *
 * @author hongzhou.wei
 * @date 2020/6/11
 */
public class P0739_dailyTemperatures {
    public static void main(String[] args) {
        int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println(Arrays.toString(dailyTemperatures(temperatures)));
        int[] a = new int[]{1, 1, 4};
        System.out.println(isMonotonic(a));
    }

    /**
     * 单调栈，维护一个从栈底到栈底下标对应值单调递减的单调栈
     * 遍历数组过程中，当下个值大于栈顶元素时就出栈计算出出栈元素的值
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param T
     * @return
     */
    static int[] dailyTemperatures(int[] T) {
        Stack<Integer> stack = new Stack<>();
        int[] ret = new int[T.length];
        for (int i = 0; i < T.length; i++) {
            // 维护一个从栈底到栈顶的下标温度单调递减的单调栈
            // 若不单调则开始出栈，计算下标差值
            while (!stack.empty() && T[i] > T[stack.peek()]) {
                int prevIndex = stack.pop();
                ret[prevIndex] = i - prevIndex;
            }
            // 当前下标入栈
            stack.push(i);
        }
        return ret;
    }

    /**
     * 单调数列，
     *
     * @param A
     * @return
     */
    static boolean isMonotonic(int[] A) {
        if (A.length == 1) {
            return true;
        }
        int a=0,b=0;
        for (int i = 0; i < A.length - 1; i++) {
            if(A[i] < A[i+1]) {
                a=1;
            }else {
                b=1;
            }
        }
        return a+b==1;
    }

    /**
     * 从前往后遍历数组，每遍历一个元素就计算出当前元素对应的等待天数
     * 由于遍历过的值下次不会再用到，所以不需要额外的空间，直接将等待天数
     * 存在传入的参数中即可。
     * 时间复杂度O(n*n)，空间复杂度O(1)
     *
     * @param T
     * @return
     */
    static int[] dailyTemperatures1(int[] T) {
        for (int i = 0; i < T.length; i++) {
            int val = 0;
            for (int j = i + 1; j < T.length; j++) {
                if (T[j] > T[i]) {
                    val = j - i;
                    break;
                }
            }
            T[i] = val;
        }
        return T;
    }
}
