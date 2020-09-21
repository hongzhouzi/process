package cn.algorithm.leetcode;

/**
 * 538. 把二叉搜索树转换为累加树
 * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，
 * 使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
 * 例如：
 * 输入: 原始二叉搜索树:
 *               5
 *             /   \
 *            2     13
 *
 * 输出: 转换为累加树:
 *              18
 *             /   \
 *           20     13
 *
 * @author hongzhou.wei
 * @date 2020/9/21
 */
public class P0538_ConvertBST2GreaterTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(111);
        root.left = new TreeNode(222);
        root.right = new TreeNode(333);
        root.left.left = new TreeNode(444);
        root.left.right = new TreeNode(555);
        TreeNode treeNode = new Solution().convertBST(root);
        System.out.println(treeNode);
    }



    /**
     * 反序-中序遍历
     */
    static class Solution {
        int sum = 0;
        public TreeNode convertBST(TreeNode root) {
            if(root != null){
                convertBST(root.right);
                sum += root.val;
                root.val = sum;
                convertBST(root.left);
            }
            return  root;
        }


    }
}
