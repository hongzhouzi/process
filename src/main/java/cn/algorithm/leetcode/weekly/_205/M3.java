package cn.algorithm.leetcode.weekly._205;

/**
 * 5509. 避免重复字母的最小删除成本
 * 给你一个字符串 s 和一个整数数组 cost ，其中 cost[i] 是从 s 中删除字符 i 的代价。
 * 返回使字符串任意相邻两个字母不相同的最小删除成本。
 * 请注意，删除一个字符后，删除其他字符的成本不会改变。
 * 示例 1：
 * 输入：s = "abaac", cost = [1,2,3,4,5]
 * 输出：3
 * 解释：删除字母 "a" 的成本为 3，然后得到 "abac"（字符串中相邻两个字母不相同）。
 * 示例 2：
 * 输入：s = "abc", cost = [1,2,3]
 * 输出：0
 * 解释：无需删除任何字母，因为字符串中不存在相邻两个字母相同的情况。
 * 示例 3：
 * 输入：s = "aabaa", cost = [1,2,3,4,1]
 * 输出：2
 * 解释：删除第一个和最后一个字母，得到字符串 ("aba") 。
 * 提示：
 * s.length == cost.length
 * 1 <= s.length, cost.length <= 10^5
 * 1 <= cost[i] <= 10^4
 * s 中只含有小写英文字母
 *
 * @author hongzhou.wei
 * @date 2020/9/6
 */
public class M3 {
    public static void main(String[] args) {
//        String s = "aaabbbabbbb";
//        int[] cost = {3, 5, 10, 7, 5, 3, 6, 5, 4, 8, 1};
//        String s = "bbbaaa"; int [] cost = {4,9,3,8,8,9};
        String s = "abaac"; int [] cost = {1,2,3,4,5};
//        String s = "aabaa"; int [] cost = {1,2,3,4,1};
        System.out.println(minCost(s, cost));
        System.out.println(minCost1(s, cost));
    }

    /**
     * 把相同字母中最大的代价的找出来，用总的代价减去最大的代价，注意如果有单独一个元素相邻的也需要处理好。
     *
     * @param s
     * @param cost
     * @return
     */
    static public int minCost(String s, int[] cost) {
        char[] chars = s.toCharArray();
        int sumCost = 0;
        for (int i = 0; i < cost.length; i++) {
            sumCost += cost[i];
        }
        int maxValue = 0;
        char pre = (char) 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != pre) {
                sumCost -= maxValue;
                maxValue = 0;
                pre = chars[i];
            }
            maxValue = Math.max(maxValue, cost[i]);
        }
        return sumCost - maxValue;
    }


   static public int minCost1(String S, int[] cost) {
        char[] s = S.toCharArray();

        int all = 0;
        for (int v : cost) all += v;

        char pre = (char) 0;
        int max = 0;

        int p = 0;
        for (char c : s) {
            if (c != pre) {
                all -= max;
                max = 0;
                pre = c;
            }
            max = Math.max(max, cost[p]);
            p++;
        }
        all -= max;
        return all;
    }
}
