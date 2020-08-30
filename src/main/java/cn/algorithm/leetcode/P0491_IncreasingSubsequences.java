package cn.algorithm.leetcode;

import java.util.*;

/**
 * 491. 递增子序列
 * 给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是2。
 * 示例:
 * 输入: [4, 6, 7, 7]
 * 输出: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
 * 说明:
 * 给定数组的长度不会超过15。
 * 数组中的整数范围是 [-100,100]。
 * 给定数组中可能包含重复数字，相等的数字应该被视为递增的一种情况。
 *
 * @author hongzhou.wei
 * @date 2020/8/25
 */
public class P0491_IncreasingSubsequences {
    public static void main(String[] args) {
//        int[] nums = {4, 6, 7, 7, 4, 4};
//        int[] nums = {1, 3, 5, 7,7};
        int[] nums = {4,3,2,1};
        System.out.println(findSubsequences(nums));
        System.out.println(new P0491_IncreasingSubsequences().findSubsequences1(nums));
    }

    /**
     * 【recursion】
     * 从前往后深度优先搜索，搜索过程对数字进行去重，若搜索的当前数满足递增就加入结果集并继续向后搜索
     */
    List<List<Integer>> ret = new LinkedList<>();
    public List<List<Integer>> findSubsequences1(int[] nums) {
        dfs(nums, -1, new ArrayList<>());
        return ret;
    }

    void dfs(int[] nums, int idx, List<Integer> cur) {
        // 当前递增长度序列长度大于0，就加入结果集
        if (cur.size() > 1) {
            ret.add(new ArrayList<>(cur));
        }
        // 在 [idx+1,  len -1] 范围内搜索下一个值，并借助set去重
        Set<Integer> set = new HashSet<>();
        for (int i = idx + 1; i < nums.length; i++) {
            if (set.contains(nums[i])) {
                continue;
            }
            set.add(nums[i]);
            // 出现递增序列则添加到结果集，并向下继续搜索
            if (idx == -1 || nums[i] >= nums[idx]) {
                cur.add(nums[i]);
                dfs(nums, i, cur);
                cur.remove(cur.size() - 1);
            }
        }
    }


    //=======================================
    List<Integer> temp = new ArrayList<Integer>();
    List<List<Integer>> ans = new ArrayList<List<Integer>>();

    public List<List<Integer>> findSubsequences2(int[] nums) {
        dfs(0, Integer.MIN_VALUE, nums);
        return ans;
    }

    public void dfs(int cur, int last, int[] nums) {
        if (cur == nums.length) {
            if (temp.size() >= 2) {
                ans.add(new ArrayList<Integer>(temp));
            }
            return;
        }
        if (nums[cur] >= last) {
            temp.add(nums[cur]);
            dfs(cur + 1, nums[cur], nums);
            temp.remove(temp.size() - 1);
        }
        if (nums[cur] != last) {
            dfs(cur + 1, last, nums);
        }
    }


    /**
     * 【bfs】
     * 排序后遍历一次数组组合出两个
     *
     * @param nums
     * @return
     */
    static public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> ret = new LinkedList<>();
        int len = nums.length, lastIndex = 0;
        // 记录顺序列表List<Integer>中最后索引
        Deque<Integer> queue = new LinkedList<>();
        Set<List<Integer>> set = new HashSet<>();
        // 组合两位数
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                List<Integer> cur = new LinkedList<>();
                cur.add(nums[i]);
                cur.add(nums[j]);
                // 取的两位数相邻 || 之前有的组合不取
                if (nums[i] <= nums[j] && !set.contains(cur)) {
                    set.add(cur);
                    ret.add(cur);
                    queue.offer(j + 1);
                }
            }
        }
        // 在前面组合数的基础上往后一位一位的尝试
        while (!queue.isEmpty()) {
            int pollIndex = queue.poll();
            int i = pollIndex;
            while (i < len) {
                List<Integer> cur = new LinkedList<>(ret.get(lastIndex));
                cur.add(nums[i]);
                if (nums[pollIndex - 1] <= nums[i] && !set.contains(cur)) {
                    set.add(cur);
                    ret.add(cur);
                    queue.offer(i + 1);
                }
                i++;
            }
            lastIndex++;
        }
        return ret;
    }
}
