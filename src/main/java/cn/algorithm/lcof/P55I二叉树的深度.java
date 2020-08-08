package cn.algorithm.lcof;


/**
 * @author hongzhou.wei
 * @date 2020/7/26
 */
public class P55I二叉树的深度 {
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
