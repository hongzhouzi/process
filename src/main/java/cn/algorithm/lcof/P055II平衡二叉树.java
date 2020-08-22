package cn.algorithm.lcof;

import java.util.Objects;

/**
 * @author hongzhou.wei
 * @date 2020/8/22
 */
public class P055II平衡二叉树 {
    public boolean isBalanced(TreeNode root) {
        if(Objects.isNull(root)){
            return true;
        }
        return Math.abs(height(root.right) - height(root.left)) > 2
            && isBalanced(root.left)
            && isBalanced(root.right);
    }

    int height(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        return Math.max(height(root.left), height(root.right)) + 1;
    }

    public boolean isBalanced1(TreeNode root) {
      return helper(root) != -1;
    }
    int helper(TreeNode root){
        if(Objects.isNull(root)){
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        return Math.abs(left - right) > 1 || left == -1 || right == -1
            ? -1
            : Math.max(left,right) + 1;
    }
}
