package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 * 说明：解集不能包含重复的子集。
 * 示例:
 * 输入: nums = [1,2,3]
 * 输出:
 * [
 * [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 * <p>
 * 思路：
 * 1.遍历数组，遇到一个数就把所有子集加上该数组成新的子集，遍历完毕即是所有子集
 *
 * @author hongzhou.wei
 * @date 2020/6/10
 */
public class P0078_subsets {

    public static void main(String[] args) {
        int a[] = new int[]{1, 2, 3};
        List<List<Integer>> ret = subsets1(a);
        for (List<Integer> list : ret) {
            for (Integer integer : list) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }

    // 参考
    static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        // 添加空子集
        results.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            List<List<Integer>> plusNumbers = new ArrayList<>();
            // 添加当前元素产生的子集
            for (List<Integer> result : results) {
                List<Integer> newNumber = new ArrayList<>(result);
                newNumber.add(nums[i]);
                plusNumbers.add(newNumber);
            }
            results.addAll(plusNumbers);
        }
        return results;
    }

    /**
     * 递归思想：遍历数组，最开始假设子集为空，然后遇到一个数
     * 就把所有子集加上该数组成新的子集，遍历完毕即是所有子集
     * 时间复杂度：O(N * 2^N)
     * 空间复杂度：O(N * 2^N)
     * 对于给定的任意元素，它在子集中有两种情况，存在或者不存在（对应二进制中的 0 和 1）。
     * 因此，N个数字共有 2^N个子集。
     *
     * @param nums
     * @return
     */
    static List<List<Integer>> subsets1(int[] nums) {
        List<List<Integer>> results = new LinkedList<>();
        // 添加空的子集
        results.add(new LinkedList<>());
        // 遍历数组，更新子集
        for (int num : nums) {
            List<List<Integer>> newSubsets = new LinkedList<>();
            // 遍历已有子集，添加增加当前数新增的子集
            for (List<Integer> cur : results) {
                List<Integer> curSubset = new LinkedList<>(cur);
                curSubset.add(num);
                newSubsets.add(curSubset);
                // 前3行代码可简写成如下
                /*newSubsets.add(new LinkedList<Integer>(cur) {{
                    add(num);
                }});*/
            }
            results.addAll(newSubsets);
        }
        return results;
    }

}
