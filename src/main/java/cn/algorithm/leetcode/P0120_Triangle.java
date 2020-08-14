package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 120. 三角形最小路径和
 * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
 * 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
 * 例如，给定三角形：
 * [
 *      [2],
 *     [3,4],
 *    [6,5,7],
 *   [4,1,8,3]
 * ]
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 * 说明：
 * 如果你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题，那么你的算法会很加分。
 *
 * @author hongzhou.wei
 * @date 2020/7/14
 */
public class P0120_Triangle {
	public static void main(String[] args) {
		List<List<Integer>> d = new ArrayList<>();
		d.add(new ArrayList<Integer>() {{
			add(2);
		}});
		d.add(new ArrayList<Integer>() {{
			add(3);
			add(4);
		}});
		d.add(new ArrayList<Integer>() {{
			add(6);
			add(5);
			add(7);
		}});
		d.add(new ArrayList<Integer>() {{
			add(4);
			add(1);
			add(8);
			add(3);
		}});
		System.out.println(minimumTotal(d));
	}

	/**
	 * 【动态规划】自顶向上遍历，存储最小值的作为状态，计算上一层中累加和最小的。
	 *  dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
	 *
	 * @param triangle
	 * @return
	 */
	static public int minimumTotal(List<List<Integer>> triangle) {
		int dp[] = new int[triangle.size()+1];
		for (int i = triangle.size() - 1; i>=0 ;i--) {
			for (int j = 0; j < triangle.get(i).size(); j++) {
				dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
			}
		}
		return dp[0];
	}

	/**
	 * 【递归】
	 * @param triangle
	 * @return
	 */
	static public int minimumTotal1(List<List<Integer>> triangle) {
		return dfs(triangle,0,0);
	}
	static int dfs(List<List<Integer>> triangle, int i, int j){
		if(i == triangle.size()){
			return 0;
		}
		return Math.min(dfs(triangle,i+1,j), dfs(triangle,i+1,j+1)) + triangle.get(i).get(j);
	}


}