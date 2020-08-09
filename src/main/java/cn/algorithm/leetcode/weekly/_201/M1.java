package cn.algorithm.leetcode.weekly._201;

import java.util.LinkedList;

/**
 * @author hongzhou.wei
 * @date 2020/8/9
 */
public class M1 {
    public static void main(String[] args) {
//        String s = "leEeetcode";
        String s = "s";
//       String s = "abBAcC";
        System.out.println(makeGood(s));
    }

    static public String makeGood(String s) {
        if (s.length() == 0) {
            return "";
        }
        LinkedList<Character> stack = new LinkedList<>();
        stack.push(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            if (!stack.isEmpty() && Math.abs(stack.peek() - s.charAt(i)) == 32) {
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

    public String makeGood1(String s) {
        for (int i = 0; i < s.length()-1; ++i) {
            if (Math.abs(s.charAt(i)-s.charAt(i+1))==32) {
                return makeGood(s.substring(0,i)+s.substring(i+2,s.length()));
            }
        }
        return s;
    }

}
