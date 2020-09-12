package cn.algorithm.leetcode;

import java.util.*;

/**
 * 77. 组合
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 * 示例:
 * 输入: n = 4, k = 2
 * 输出:
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 *
 * @author hongzhou.wei
 * @date 2020/9/8
 */
public class P0077_Combinations {

    public static void main(String[] args) {
        int n = 4, k = 2;
        System.out.println(new Solution().combine(n, k));
    }


    /**
     * 【队列记录下标位置】先组合k=2的情况，并用队列记录下标位置，k>2则依次从前面
     */
    /**
     * 【dfs+回溯】
     */
    static class Solution {
        public List<List<Integer>> combine(int n, int k) {
            List<List<Integer>> res = new ArrayList<>();
            if (k <= 0 || n < k) {
                return res;
            }
            // 从 1 开始是题目的设定
            List<Integer> path = new ArrayList<>();
            dfs(n, k, 1, path, res);
            return res;
        }

        void dfs(int n, int k, int begin, List<Integer> path, List<List<Integer>> res) {
            // 递归终止条件是：path 的长度等于 k
            if (path.size() == k) {
                res.add(new ArrayList<>(path));
                return;
            }

            // 遍历可能的搜索起点
            // 剪枝：搜索起点的上界 + 接下来要选择的元素个数 - 1 = n   n - (k - path.size()) + 1
            for (int i = begin, len = n - (k - path.size()) + 1; i <= len; i++) {
                // 加入临时结果集+回溯
                path.add(i);
                dfs(n, k, i + 1, path, res);
                path.remove(path.size() - 1);
            }
        }

    }

}
