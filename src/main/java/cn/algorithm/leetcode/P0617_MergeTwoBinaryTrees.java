package cn.algorithm.leetcode;

/**
 * @author hongzhou.wei
 * @date 2020/9/23
 */
public class P0617_MergeTwoBinaryTrees {
    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(1);
        t1.left = new TreeNode(3);
        t1.left.left = new TreeNode(5);
        t1.right = new TreeNode(2);

        TreeNode t2 = new TreeNode(2);
        t2.left = new TreeNode(1);
        t2.left.right = new TreeNode(4);
        t2.right = new TreeNode(3);
        t2.right.right = new TreeNode(7);

        TreeNode treeNode = new Solution1().mergeTrees(t1, t2);
        System.out.println(treeNode);


    }

    /**
     * 正确解法：依次将合并的左子树和右子树加在当前节点上
     */
    static class Solution1 {
        public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
            if (t1 != null && t2 != null) {
                t1.val += t2.val;
                t1.left = mergeTrees(t1.left, t2.left);
                t1.right = mergeTrees(t1.right, t2.right);
            }
            return t1 != null ? t1 : t2;
        }
    }

    /**
     * 开始的错误思路和解法，原因是没有改变树的结构，在操作左右子树时没有给left和right赋值而是直接给root赋值
     */
    static class Solution {
        public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
            TreeNode root = t1;
            dfs(root, t2);
            return root;
        }

        void dfs(TreeNode root, TreeNode t2) {
            if (t2 == null) {
                return;
            }
            // 这种写法只能更改值，不能改变root的结构（左右节点没改到）
            if (root == null) {
                root = t2;
                return;
            }
            root.val += t2.val;
            dfs(root.left, t2.left);
            dfs(root.right, t2.right);
        }
    }
}
