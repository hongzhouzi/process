package cn.algorithm.leetcode.weekly._213;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author hongzhou.wei
 * @date 2020/11/1
 */
public class M2 {
    public static void main(String[] args) {
        System.out.println(new M2.Solution1().countVowelStrings(33));
    }


    static class Solution1 {
        char[] chars = {'a', 'e', 'i', 'o', 'u'};

        public int countVowelStrings(int n) {

            if (n == 1) {
                return 5;
            }
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < chars.length; i++) {
                stringList.add(String.valueOf(chars[i]));
            }
            f(stringList, n);
            return stringList.size();
        }

        void f(List<String> stringList, int n) {
            if (stringList.get(0).length() == n) {
                return;
            }
            for (int i = 0, len = stringList.size(); i < len; i++) {
                String builder = stringList.get(0);
                char c = builder.charAt(builder.length() - 1);
                for (int x = 0; x < 5; x++) {
                    if (chars[x] >= c) {
                        stringList.add(builder + chars[x]);
                    }
                }
                stringList.remove(builder);
            }
            f(stringList, n);
        }

    }

    static class Solution {
        int            res = 0;
        Stack<Integer> sk  = new Stack<>();

        public int countVowelStrings(int n) {
            dfs(n);
            return res;
        }

        public void dfs(int n) {
            if (sk.size() == n) {
                res++;
                return;
            }
            int index = sk.isEmpty() ? 0 : sk.peek();
            for (int i = index; i < 5; i++) {
                sk.push(i);
                dfs(n);
                sk.pop();
            }
        }
    }

    static class Solution2 {
        public int countVowelStrings(int n) {
            int[] arr = new int[5];
            Arrays.fill(arr, 1);

            for (int i = 0; i < n - 1; i++) {
                for (int j = 1; j < 5; j++) {
                    arr[j] += arr[j - 1];
                }
            }

            int res = 0;
            for (int i = 0; i < 5; i++) res += arr[i];

            return res;
        }
    }
}
