package cn.algorithm.leetcode;

/**
 * @author hongzhou.wei
 * @date 2020/9/30
 */
public class P0701_InsertintoaBinarySearchTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(7);
        TreeNode treeNode = new P0701_InsertintoaBinarySearchTree.Solution1().insertIntoBST(root, 5);
        System.out.println(treeNode);
    }


    static class Solution1 {
        public TreeNode insertIntoBST(TreeNode root, int val) {
            if (root == null) {
                return new TreeNode(val);
            }
            if( val < root.val ){
                root.left = insertIntoBST(root.left, val);
            }else {
                root.right = insertIntoBST(root.right, val);
            }
            return root;
        }
    }

    static class Solution {
        public TreeNode insertIntoBST(TreeNode root, int val) {
            if (root == null) {
                return new TreeNode(val);
            }
            insert(root, val);
            return root;
        }

        void insert(TreeNode node, int val) {
            if (node == null) {
                return;
            }
            if (val <= node.val) {
                // 叶子节点
                if (node.left == null) {
                    node.left = new TreeNode(val);
                    return;
                }
                insert(node.left, val);
            } else {
                // 叶子节点
                if (node.right == null) {
                    node.right = new TreeNode(val);
                    return;
                }
                insert(node.right, val);
            }
        }
    }
}
