package cn.algorithm.leetcode;

/**
 * 968. 监控二叉树
 * 给定一个二叉树，我们在树的节点上安装摄像头。
 * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
 * 计算监控树的所有节点所需的最小摄像头数量。
 * 示例 1：
 * 输入：[0,0,null,0,0]
 * 输出：1
 * 解释：如图所示，一台摄像头足以监控所有节点。
 * 示例 2：
 * 输入：[0,0,null,0,null,0,null,null,0]
 * 输出：2
 * 解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
 * 提示：
 * 给定树的节点数的范围是 [1, 1000]。
 * 每个节点的值都是 0。
 *
 * @author hongzhou.wei
 * @date 2020/9/22
 */
public class P0968_BinaryTreeCameras {

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.left.left = new TreeNode(2);
        treeNode.left.left.left = new TreeNode(2);
        treeNode.left.left.left.left= new TreeNode(2);
        treeNode.right= new TreeNode(2);
        System.out.println(new Solution().minCameraCover(treeNode));
    }

    static class Solution {
        private int ans = 0;

        public int minCameraCover(TreeNode root) {
            if (root == null) return 0;
            if (dfs(root) == 2) ans++;
            return ans;
        }

        // 1：该节点不可观  0：该节点可观，但没有安装监视器  2：该节点安装了监视器
        private int dfs(TreeNode node) {
            if (node == null)
                return 1;
            // 递归查找左右子节点状态
            int left = dfs(node.left), right = dfs(node.right);
            // 左右子节点告诉当前节点需要monitor，就在当前节点安装，结果集就累加并告诉父节点可观但不用monitor
            if (left == 2 || right == 2) {
                ans++;
                return 0;
            }
            // 左右子节点有monitor，告诉父节点不可观
            else if (left == 0 || right == 0){
                return 1;
            }
            // 当前的左右子节点不可观（没有子节点了），告诉父节点需要monitor
            else {
                return 2;
            }
        }
    }
}
