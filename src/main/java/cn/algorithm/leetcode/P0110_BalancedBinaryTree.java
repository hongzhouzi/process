package cn.algorithm.leetcode;

/**
 * 110. 平衡二叉树
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 * 本题中，一棵高度平衡二叉树定义为：
 * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
 * 示例 1:
 * 给定二叉树 [3,9,20,null,null,15,7]
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回 true 。
 * 示例 2:
 * 给定二叉树 [1,2,2,3,3,null,null,4,4]
 *
 *        1
 *       / \
 *      2   2
 *     / \
 *    3   3
 *   / \
 *  4   4
 * 返回 false 。
 *
 * @author hongzhou.wei
 * @date 2020/8/17
 */
public class P0110_BalancedBinaryTree {

    public static void main(String[] args) {
       /* TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.left.left = new TreeNode(4);
        root.left.left.right = new TreeNode(4);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(2);*/

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(2);
        System.out.println(new P0110_BalancedBinaryTree().isBalanced(root));
    }


    /**
     * 计算出左边最大高度、右边最大高度，保证两边高度差小于2；同时左右两边也需要满足是平衡的
     *  时间复杂度：O(n^2)，n为树的节点数。
     *  空间复杂度：O(n)，n为树的节点个数，递归调用过程每个节点都需要调用。
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return Math.abs(getHeight(root.left) - getHeight(root.right)) < 2
            && isBalanced(root.left)
            && isBalanced(root.right);
    }

    int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    /**
     * 自底向上，
     * 时间复杂度：O(n)，n为树节点个数
     * 空间复杂度：O(n)，递归开销
     *
     * @param root
     * @return
     */
    public boolean isBalanced1(TreeNode root) {
        return helper(root) >= 0;
    }

    int helper(TreeNode root) {
        if (root == null) {
            return -1;
        }
        int leftHeight = helper(root.left);
        int rightHeight = helper(root.right);
        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

}
