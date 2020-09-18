package cn.algorithm.leetcode;

/**
 * @author hongzhou.wei
 * @date 2020/9/16
 */
public class P0226_InvertBinaryTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(111);
        root.left = new TreeNode(222);
        root.right = new TreeNode(333);
        root.left.left = new TreeNode(444);
        root.left.right = new TreeNode(555);
        TreeNode c = new Solution1().invertTree(root);
        System.out.println(c);
    }

    static class Solution {
        public TreeNode invertTree(TreeNode root) {
            f(root);
            return root;
        }

        void f(TreeNode root) {
            if (root == null) {
                return;
            }
            TreeNode left = root.left;
            root.left = root.right;
            root.right = left;
            f(root.left);
            f(root.right);
        }
    }

    static class Solution1 {
        public TreeNode invertTree(TreeNode root) {
            if(root == null) return root;
            TreeNode temp = invertTree(root.left);
            root.left = invertTree(root.right);
            root.right = temp;
            return root;
        }
    }
}
