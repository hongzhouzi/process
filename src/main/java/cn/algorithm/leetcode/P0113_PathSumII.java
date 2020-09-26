package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 113. 路径总和 II
 * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
 * 说明: 叶子节点是指没有子节点的节点。
 * 示例:
 * 给定如下二叉树，以及目标和 sum = 22，
 *
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \    / \
 *         7    2  5   1
 * 返回:
 *
 * [
 *    [5,4,11,2],
 *    [5,8,4,5]
 * ]
 *
 * @author hongzhou.wei
 * @date 2020/9/26
 */
public class P0113_PathSumII {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);

        System.out.println(new P0113_PathSumII.Solution().pathSum(root, 22));
    }

    static class Solution {
        List<List<Integer>> ret = new ArrayList<>();
        int                 sum;

        public List<List<Integer>> pathSum(TreeNode root, int sum) {
            this.sum = sum;
            dfs(root, new ArrayList<>(), 0);
            return ret;
        }

        void dfs(TreeNode root, List<Integer> path, int pathSum) {
            if (root == null) {
                return;
            }
            path.add(root.val);
            if (root.left == null && root.right == null && pathSum + root.val == sum) {
                ret.add(new ArrayList<>(path));
                // 下面两行可注释掉
                path.remove(path.size() - 1);
                return;
            }
            dfs(root.left, path, pathSum + root.val);
            dfs(root.right, path, pathSum + root.val);
            path.remove(path.size() - 1);
        }
    }

    static class Solution1 {
        List<List<Integer>> ret = new ArrayList<>();
        int                 sum;

        public List<List<Integer>> pathSum(TreeNode root, int sum) {
            this.sum = sum;
            dfs(root, new ArrayList<>(), 0);
            return ret;
        }

        void dfs(TreeNode root, List<Integer> path, int pathSum) {
            if (root == null) {
                return;
            }
            // 叶子节点
            if (root.left == null && root.right == null) {
                if (pathSum + root.val == sum) {
                    path.add(root.val);
                    ret.add(new ArrayList<>(path));
                    path.remove(path.size() - 1);
                }
                return;
            }
            path.add(root.val);
            pathSum += root.val;
            dfs(root.left, path, pathSum);
            dfs(root.right, path, pathSum);
            path.remove(path.size() - 1);
            pathSum -= root.val;
        }
    }
}
