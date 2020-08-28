package cn.algorithm.leetcode;

import java.util.Objects;

/**
 * @author hongzhou.wei
 * @date 2020/8/21
 */
public class P0111_MinimumDepthofBinaryTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
//        root.right.right = new TreeNode(2);
//        root.left = new TreeNode(2);
        System.out.println(minDepth(root));
    }

    static public int minDepth(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        int left = minDepth(root.left);
        int right= minDepth(root.right);
        // 当根节点的左子树或右子树为空时，最小深度是另一边的最小深度
        return Objects.isNull(root.left) || Objects.isNull(root.right)
            ? Math.max(left, right) + 1
            : Math.min(left,right) + 1;
    }
}
