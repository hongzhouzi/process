package cn.algorithm.leetcode;

/**
 * 404. Sum of Left Leaves
 * Find the sum of all left leaves in a given binary tree.
 *
 * Example:
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
 *
 * @author hongzhou.wei
 * @date 2020/9/23
 */
public class P0404_SumofLeftLeaves {

    public static void main(String[] args) {

    }
    static class Solution {
        int sum = 0;
        public int sumOfLeftLeaves(TreeNode root) {
            dfs(root);
            return sum;
        }
        void dfs(TreeNode node){
            if(node == null){
                return;
            }
            if(node.left !=null && node.left.left == null && node.left.right == null){
                sum += node.left.val;
            }
            dfs(node.left);
            dfs(node.right);
        }
    }
}
