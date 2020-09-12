package cn.algorithm.leetcode;

import java.util.*;

/**
 * 216. 组合总和 III
 * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
 * 说明：
 * 所有数字都是正整数。
 * 解集不能包含重复的组合。
 * 示例 1:
 * 输入: k = 3, n = 7
 * 输出: [[1,2,4]]
 * 示例 2:
 * 输入: k = 3, n = 9
 * 输出: [[1,2,6], [1,3,5], [2,3,4]]
 *
 * @author hongzhou.wei
 * @date 2020/9/11
 */
public class P0216_CombinationSumIII {

    public static void main(String[] args) {
//        int k = 3, n = 9;
        int k = 3, n = 7;
        System.out.println(new P0216_CombinationSumIII.Solution().combinationSum3(k, n));
        System.out.println(new P0216_CombinationSumIII.Solution1().combinationSum3(k, n));
        System.out.println(new P0216_CombinationSumIII.Solution2().combinationSum3(k, n));
    }

    static class Solution {
        List<List<Integer>> ret = new LinkedList<>();
        int                 target;
        int                 numCount;
        Set<List>           set = new HashSet<>();

        public List<List<Integer>> combinationSum3(int k, int n) {
            this.target = n;
            this.numCount = k;
            dfs(new LinkedList<>(), 0, 1);
            return ret;
        }

        void dfs(List<Integer> path, int pathSum, int startIndex) {
            if (pathSum == target && numCount == path.size()) {
                ArrayList<Integer> addPath = new ArrayList<>(path);
                if (!set.contains(addPath)) {
                    ret.add(addPath);
                    set.add(addPath);
                }
                return;
            }
            for (int i = startIndex; i <= 9; i++) {
                if (pathSum + i <= target) {
                    path.add(i);
                    dfs(path, pathSum + i, i + 1);
                    path.remove(path.size() - 1);
                }
            }
        }

    }


    static class Solution1 {
        List<List<Integer>> ret = new LinkedList<>();
        int                 target;
        int                 numCount;

        public List<List<Integer>> combinationSum3(int k, int n) {
            this.target = n;
            this.numCount = k;
            dfs(new LinkedList<>(), 0, 1);
            return ret;
        }

        void dfs(List<Integer> path, int pathSum, int startIndex) {
            if (pathSum == target && numCount == path.size()) {
                ret.add(new ArrayList<>(path));
                return;
            }
            for (int i = startIndex; i <= 9; i++) {
                if (pathSum + i <= target && path.size() <= numCount) {
                    path.add(i);
                    dfs(path, pathSum + i, i + 1);
                    path.remove(path.size() - 1);
                }
            }
        }

    }

    static class Solution2 {
        public List<List<Integer>> combinationSum3(int k, int n) {
            List<List<Integer>> ret = new ArrayList<>();
            dfs(k, n, ret, new ArrayList<>(), 0, 1);
            return ret;
        }

        void dfs(int k, int n, List<List<Integer>> ret, List<Integer> path, int pathSum, int startIndex) {
            if (pathSum == n && k == path.size()) {
                ret.add(new ArrayList<>(path));
                return;
            }
            for (int i = startIndex; i <= 9; i++) {
                if (pathSum + i <= n && path.size() <= k) {
                    path.add(i);
                    dfs(k, n, ret, path, pathSum + i, i + 1);
                    path.remove(path.size() - 1);
                }
            }
        }
    }
}
