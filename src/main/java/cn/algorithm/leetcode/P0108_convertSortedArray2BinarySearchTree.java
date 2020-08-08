package cn.algorithm.leetcode;

/**
 * 108. 将有序数组转换为二叉搜索树
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 * 示例:
 * 给定有序数组: [-10,-3,0,5,9],
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 *
 * @author hongzhou.wei
 * @date 2020/7/3
 */
public class P0108_convertSortedArray2BinarySearchTree {
    public static void main(String[] args) {

    }

    /**
     * 二叉搜索树中中序遍历得到的是升序序列，给定的升序序列则可得出二叉树为中序遍历的结果
     * 在取根节点时，取中间位置偏左或偏右均可。
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return recursion(nums, 0, nums.length - 1);
    }

    TreeNode recursion(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        // 选择中间位置左边的作为根节点
        int mid = (left + right) / 2;
        // 选择任意一个中间位置数字作为根节点
        //int mid = (left + right + new Random().nextInt(2)) / 2;
        // 总是选择中间位置右边的数字作为根节点
        // int mid = (left + right + 1) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = recursion(nums, left, mid - 1);
        node.right = recursion(nums, mid + 1, right);
        return node;
    }



    public TreeNode sortedArrayToBST1(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    public TreeNode helper(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        // 总是选择中间位置左边的数字作为根节点
        int mid = (left + right) / 2;

        TreeNode root = new TreeNode(nums[mid]);
        root.left = helper(nums, left, mid - 1);
        root.right = helper(nums, mid + 1, right);
        return root;
    }

}


