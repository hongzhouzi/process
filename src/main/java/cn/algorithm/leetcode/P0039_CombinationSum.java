package cn.algorithm.leetcode;

import java.util.*;

/**
 * 39. 组合总和
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的数字可以无限制重复被选取。
 * 说明：
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。
 * 示例 1：
 * 输入：candidates = [2,3,6,7], target = 7,
 * 所求解集为：
 * [
 * [7],
 * [2,2,3]
 * ]
 * 示例 2：
 * <p>
 * 输入：candidates = [2,3,5], target = 8,
 * 所求解集为：
 * [
 * [2,2,2,2],
 * [2,3,3],
 * [3,5]
 * ]
 * 提示：
 * 1 <= candidates.length <= 30
 * 1 <= candidates[i] <= 200
 * candidate 中的每个元素都是独一无二的。
 * 1 <= target <= 500
 *
 * @author hongzhou.wei
 * @date 2020/9/9
 */
public class P0039_CombinationSum {

    public static void main(String[] args) {
//        int[] candidates = {1, 2};
//        int target = 4;
        int[] candidates = {2, 3, 5};
        int target = 8;
        System.out.println(new P0039_CombinationSum.Solution().combinationSum(candidates, target));
        System.out.println(new P0039_CombinationSum.Solution1().combinationSum(candidates, target));
    }

    static class Solution {
        List<List<Integer>> ret = new LinkedList<>();
        int[]               candidates;
        int                 target;
        Set<List>           set = new HashSet<>();

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            // 剪枝的基础
            Arrays.sort(candidates);
            this.candidates = candidates;
            this.target = target;
            dfs(new LinkedList<>(), 0);
            return ret;
        }

        void dfs(List<Integer> path, int pathSum) {
            if (pathSum == target) {
                // 排序去重
                ArrayList<Integer> addPath = new ArrayList<>(path);
                Collections.sort(addPath);
                if (!set.contains(addPath)) {
                    ret.add(addPath);
                    set.add(addPath);
                }
                return;
            }
            for (int i = 0; i < candidates.length; i++) {
                // 剪枝，先对candidates排序，后面如果pathSum比target大就不用遍历了
                if (pathSum + candidates[i] <= target) {
                    path.add(candidates[i]);
                    dfs(path, pathSum + candidates[i]);
                    path.remove(path.size() - 1);
                }
            }
        }
    }


    /**
     * 优化去重环节：遍历时按照一定顺序从前往后遍历就可以保证结果集中是有序的而且不会重复
     */
    static class Solution1 {
        List<List<Integer>> ret = new ArrayList<>();
        int[]               candidates;
        int                 target;

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            Arrays.sort(candidates);
            this.candidates = candidates;
            this.target = target;
            dfs(new ArrayList<>(), 0, 0);
            return ret;
        }

        void dfs(List<Integer> path, int pathSum, int startIndex) {
            if (pathSum == target) {
                ret.add(new ArrayList<>(path));
                return;
            }
            for (int i = startIndex; i < candidates.length; i++) {
                // 剪枝，先对candidates排序，后面如果pathSum比target大就不用遍历了
                if (pathSum + candidates[i] <= target) {
                    path.add(candidates[i]);
                    // 因为允许一个数出现多次，遍历时这儿依然从i开始
                    dfs(path, pathSum + candidates[i], i);
                    path.remove(path.size() - 1);
                }
            }
        }
    }
}
