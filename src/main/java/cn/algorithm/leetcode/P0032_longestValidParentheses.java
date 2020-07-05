package cn.algorithm.leetcode;

import cn.examination.pass58.Solution;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 32. 最长有效括号
 * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
 * 示例 1:
 * 输入: "(()"
 * 输出: 2
 * 解释: 最长有效括号子串为 "()"
 * 示例 2:
 * 输入: ")()())"
 * 输出: 4
 * 解释: 最长有效括号子串为 "()()"
 *
 * @author hongzhou.wei
 * @date 2020/7/4
 */
public class P0032_longestValidParentheses {

    public static void main(String[] args) {
//        String weekly = "()(()";
//        String weekly = "(()";
//        String weekly = ")()())";
        String s = ")()()())()(()())";

        System.out.println(longestValidParentheses2(s));
    }

    static public int longestValidParentheses2(String s) {
        int maxLen = 0;
        // 计算的是长度，所以处理时存下标最合适
        Deque<Integer> stack = new LinkedList<>();
        // 初始需要把起始断点设置为-1，计算下标相减
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i)=='('){
                stack.push(i);
            }else {
                stack.pop();
                // 栈为空则说明出现了不连续，需更换“断点”
                if(stack.isEmpty()){
                    stack.push(i);
                }
                // 更新最大长度
                else {
                    maxLen  = Math.max(maxLen, i-stack.peek());
                }
            }
        }
        return maxLen;
    }

}
