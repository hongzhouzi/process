package cn.algorithm.leetcode;

import java.util.*;

/**
 * 40. 组合总和 II
 * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中
 * 所有可以使数字和为 target 的组合。
 * candidates 中的每个数字在每个组合中只能使用一次。
 * 说明：
 * 所有数字（包括目标数）都是正整数。
 * 解集不能包含重复的组合。
 * 示例 1:
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 所求解集为:
 * [
 * [1, 7],
 * [1, 2, 5],
 * [2, 6],
 * [1, 1, 6]
 * ]
 * 示例 2:
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 所求解集为:
 * [
 * [1,2,2],
 * [5]
 * ]
 *
 * @author hongzhou.wei
 * @date 2020/9/10
 */
public class P0040_CombinationSumII {
    public static void main(String[] args) {
        /*int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;*/
        int[] candidates = {2,5,2,1,2};
        int target = 5  ;
        System.out.println(new Solution().combinationSum2(candidates, target));
    }


    static class Solution {
        List<List<Integer>> res = new LinkedList<>();
        int[] candidates;
        int target;
        Set<List> set = new HashSet<>();

        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            Arrays.sort(candidates);
            this.candidates = candidates;
            this.target = target;
            dfs(new LinkedList<>(), 0, 0);
            return res;
        }

        void dfs(List<Integer> path, int pathSum, int startIndex) {
            if (pathSum == target) {
                if (!set.contains(path)) {
                    List<Integer> temp = new LinkedList<>(path);
                    res.add(temp);
                    set.add(temp);
                }
                return;
            }
            for (int i = startIndex; i < candidates.length; i++) {
                if (candidates[i] + pathSum <= target) {
                    path.add(candidates[i]);
                    dfs(path, pathSum + candidates[i], i + 1);
                    path.remove(path.size() - 1);
                }
            }
        }    }
}
