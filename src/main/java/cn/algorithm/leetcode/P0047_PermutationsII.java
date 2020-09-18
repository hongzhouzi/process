package cn.algorithm.leetcode;

import java.util.*;

/**
 * 47. 全排列 II
 * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
 * 示例:
 * 输入: [1,1,2]
 * 输出:
 * [
 * [1,1,2],
 * [1,2,1],
 * [2,1,1]
 * ]
 *
 * @author hongzhou.wei
 * @date 2020/9/18
 */
public class P0047_PermutationsII {
    public static void main(String[] args) {
//        int a[] = {1,2,3};
        int a[] = {1, 2, 1};
        System.out.println(new P0047_PermutationsII.Solution().permuteUnique(a));
        System.out.println(new P0047_PermutationsII.Solution1().permuteUnique(a));
    }

    static class Solution {
        List<List<Integer>> ret = new ArrayList<>();
        int                 len;
        int[]               nums;

        public List<List<Integer>> permuteUnique(int[] nums) {
            this.len = nums.length;
            Arrays.sort(nums);
            this.nums = nums;
            dfs(new ArrayList<>(), new boolean[len]);
            return ret;
        }

        /**
         * 深度优先搜索
         * @param path 结果路径
         * @param used 标记该位置的数是否被使用
         */
        void dfs(List<Integer> path, boolean used[]) {
            if (path.size() == len) {
                ret.add(new ArrayList<>(path));
                return;
            }
            for (int i = 0; i < len; i++) {
                // 用过的元素不能再使用了
                if (used[i]) {
                    continue;
                }
                // 剪枝操作
                // !used[i - 1] 是因为 nums[i - 1] 在深度优先遍历的过程中刚刚被撤销选择
                if (i > 0 && nums[i] == nums[i - 1]  && !used[i - 1]) {
                    continue;
                }

                // 将当前值添加到路径中
                used[i] = true;
                path.add(nums[i]);
                // 向下搜索
                dfs(path, used);
                // 回溯
                used[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }

    static class Solution1 {
        public List<List<Integer>> permuteUnique(int[] nums) {
            int len = nums.length;
            List<List<Integer>> res = new ArrayList<>();
            if (len == 0) {
                return res;
            }

            // 排序（升序或者降序都可以），排序是剪枝的前提
            Arrays.sort(nums);
            // 标记该位置的数是否被使用
            boolean[] used = new boolean[len];
            // 使用 Deque 是 Java 官方 Stack 类的建议
            Deque<Integer> path = new ArrayDeque<>(len);
            dfs(nums, len, 0, used, path, res);
            return res;
        }

        private void dfs(int[] nums, int len, int depth, boolean[] used, Deque<Integer> path, List<List<Integer>> res) {
            if (depth == len) {
                res.add(new ArrayList<>(path));
                return;
            }

            for (int i = 0; i < len; ++i) {
                if (used[i]) {
                    continue;
                }

                // 剪枝条件：i > 0 是为了保证 nums[i - 1] 有意义
                // 写 !used[i - 1] 是因为 nums[i - 1] 在深度优先遍历的过程中刚刚被撤销选择
                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                    continue;
                }

                path.addLast(nums[i]);
                used[i] = true;

                dfs(nums, len, depth + 1, used, path, res);
                // 回溯部分的代码，和 dfs 之前的代码是对称的
                used[i] = false;
                path.removeLast();
            }
        }

    }
}
