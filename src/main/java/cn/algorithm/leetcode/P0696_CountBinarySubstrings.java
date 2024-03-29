package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 696. 计数二进制子串
 * 给定一个字符串 s，计算具有相同数量0和1的非空(连续)子字符串的数量，并且这些子字符串中的所有0和所有1都是组合在一起的。
 *
 * 重复出现的子串要计算它们出现的次数。
 *
 * 示例 1 :
 *
 * 输入: "00110011"
 * 输出: 6
 * 解释: 有6个子串具有相同数量的连续1和0：“0011”，“01”，“1100”，“10”，“0011” 和 “01”。
 *
 * 请注意，一些重复出现的子串要计算它们出现的次数。
 *
 * 另外，“00110011”不是有效的子串，因为所有的0（和1）没有组合在一起。
 * 示例 2 :
 *
 * 输入: "10101"
 * 输出: 4
 * 解释: 有4个子串：“10”，“01”，“10”，“01”，它们具有相同数量的连续1和0。
 * 注意：
 *
 * s.length 在1到50,000之间。
 * s 只包含“0”或“1”字符。
 *
 * @author hongzhou.wei
 * @date 2020/8/10
 */
public class P0696_CountBinarySubstrings {
	public static void main(String[] args) {
//        String s = "00110011";
//        String s = "10101";
		String s = "00100";
		System.out.println(countBinarySubstrings1(s));
//        System.out.println('1'==('0'^1));
//        System.out.println('1'==('1'^1));
	}

	/**
	 * 【双指针】
	 * 后指针标识连续重复数值的开始位置，前指针标识相异字符。
	 * 若数值连续则后指针不变，前指针向前
	 * 若数值相异则count++，前有连续的则后指针不动，前指针继续向前；若前无连续的，则后指针移到本段相异处
	 *
	 * @param s
	 * @return
	 */
	static public int countBinarySubstrings(String s) {
		int i = 0, j = i + 1, splitPoint = 0, len = s.length(), count = 0;
		while (i < j && j < len) {
			if (s.charAt(i) == (s.charAt(j) ^ 1)) {
				// 相异，记录分割点
				splitPoint = j;
				count++;
			}else{
				// 有分割点的情况下
				if(splitPoint > i){
					// 当前相异连续长度相等，改变后指针
					if (splitPoint - i >= j +1 - splitPoint) {
						i = splitPoint;
					}
					// <= 情况都加记录，不可能出现 > 情况，最多=
					count++;
				}
			}
			j++;
		}
		return count;
	}

	/**
	 * 将字符串s按照0和1连续分组，如 s = 00111011，可以得到这样的数组 counts={2,3,1,2}。
	 * count数组中两个相邻的数一定是代表不同的字符连续数，假设相邻数字为u v对应u个1 v个0 或 u个0 v个1
	 * 它们能组成满足条件子串数目为 min(u, v)，即相邻数字对答案的贡献，最后求出贡献总和即可。
	 * 时间复杂度：O(n)
	 * 空间复杂度：O(n)
	 *
	 * @param s
	 * @return
	 */
	public int countBinarySubstrings0(String s) {
		List<Integer> counts = new ArrayList<>();
		int ptr = 0, n = s.length();
		while (ptr < n) {
			char c = s.charAt(ptr);
			int count = 0;
			while (ptr < n && s.charAt(ptr) == c) {
				++ptr;
				++count;
			}
			counts.add(count);
		}
		int ans = 0;
		for (int i = 1; i < counts.size(); ++i) {
			ans += Math.min(counts.get(i), counts.get(i - 1));
		}
		return ans;
	}

	/**
	 * 【在上面基础上优化】 将存放分组结果的数组换成常数，只记录上次的数
	 *
	 * 时间复杂度：O(n)
	 * 空间复杂度：O(1)
	 *
	 * @param s
	 * @return
	 */
	static public int countBinarySubstrings1(String s) {
		int i = 0, n = s.length(), last = 0, ans = 0;
		while (i < n){
			// 连续字符的开始字符
			char contHead = s.charAt(i);
			// 记录连续字符数
			int count = 0;
			// 统计连续字符
			while (i < n && contHead == s.charAt(i)){
				count++;
				i++;
			}
			// 累加和
			ans += Math.min(count, last);
			// 记录上次连续字符数
			last = count;
		}
		return ans;
	}

}
