package cn.algorithm.leetcode;

/**
 * 104. 二叉树的最大深度
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * 说明: 叶子节点是指没有子节点的节点。
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3 。
 *
 * @author hongzhou.wei
 * @date 2020/7/28
 */
public class P0104_MaximumDepthOfBinaryTree {



    public int maxDepth1(TreeNode root) {
        if(root == null){
            return 0;
        }
        return Math.max(maxDepth1(root.left),maxDepth1(root.right)) + 1;
    }

    public int maxDepth(TreeNode root) {
        return dfs(root);
    }
    int dfs(TreeNode node){
        if(node == null){
            return 0;
        }
        return Math.max(dfs(node.left), dfs(node.right))+1;
    }
}