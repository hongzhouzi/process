package cn.algorithm.base.topic;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author hongzhou.wei
 * @date 2020/6/21
 */
public class MonotonousStack {
    public static void main(String[] args) {
        int[] temperatures = new int[]{2,1,2,4,3};
        System.out.println(Arrays.toString(fun1(temperatures)));
    }

    /**
     * 暂时得不出结果的数据就入栈，等待时机到了，能得出结果时再取出来处理。
     * 每个元素入栈时需要判断之前栈中的元素是否等到了时机，是否有元素可以取出来处理了
     * 这个时机也同时维护着栈中元素的单调性
     *
     * 由于每个元素都不可能在入栈时就把当前元素处理了，所以其实每个元素都会有入栈操作
     * 最关键的是每个元素入栈时检查栈中的元素是否可以处理了，这个处理过程维护着栈中
     * 元素的单调性
     *
     * @param arr
     * @return
     */
    static public int[] fun1(int[] arr) {
        // 1.声明栈、结果集
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[arr.length];
        // 2.遍历数组（或线性结构的容器）
        for (int i = 0; i < arr.length; i++) {
            // 3.单调栈相关处理
            // 3.1 判断当前元素入栈前栈中是否有元素可以处理了
            while (!stack.empty() && arr[i] > arr[stack.peek()]) {
                // 3.2 将现在能处理的元素取出(这步维护着栈的单调性)
                int index = stack.pop();
                // 3.3 计算取出元素对应的值
                result[index] = arr[i];
            }
            // 3.4 当前下标入栈
            stack.push(i);
        }
        // 3.5 处理栈中没有处理完元素对应的结果值（如果该值在初始化结果集时处理了这儿就不用处理）
        while (!stack.empty()){
            int prevIndex = stack.pop();
            result[prevIndex] = -1;
        }
        return result;
    }

    static int[] fun2(int[] arr) {
        int []ret = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            // 判断当前元素入栈前栈中是否有可以处理的元素
            while (!stack.empty() && stack.peek() < arr[i]){
                // 取出可处理的元素
                stack.pop();

            }
            stack.push(arr[i]);
        }
        return ret;
    }
}
