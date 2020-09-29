package cn.algorithm.leetcode;

import java.util.HashMap;

/**
 * 106. 从中序与后序遍历序列构造二叉树
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 * 注意:
 * 你可以假设树中没有重复的元素。
 * 例如，给出
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 * 返回如下的二叉树：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * @author hongzhou.wei
 * @date 2020/9/29
 */
public class P0106 {
    public static void main(String[] args) {
        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};
        TreeNode treeNode = new P0106.Solution().buildTree(inorder, postorder);
        System.out.println(treeNode);
    }

    static class Solution {
        HashMap<Integer, Integer> inorderArrayMap = new HashMap<>();
        int[]                     post;

        public TreeNode buildTree(int[] inorder, int[] postorder) {
            post = postorder;
            for (int i = 0; i < inorder.length; i++) {
                // 将节点值及索引全部记录在哈希表中
                inorderArrayMap.put(inorder[i], i);
            }
            return buildTree(0, inorder.length - 1, 0, post.length - 1);
        }

        public TreeNode buildTree(int inorderStart, int inorderEnd, int postorderStart, int postorderEnd) {
            if (inorderEnd < inorderStart || postorderEnd < postorderStart) {
                return null;
            }
            // 根据后序遍历结果，取得根节点
            int root = post[postorderEnd];
            // 获取对应的索引
            int rootIndexInInorderArray = inorderArrayMap.get(root);

            // 创建该节点
            TreeNode node = new TreeNode(root);
            node.left = buildTree(inorderStart, rootIndexInInorderArray - 1, postorderStart, postorderStart + rootIndexInInorderArray - inorderStart - 1);
            node.right = buildTree(rootIndexInInorderArray + 1, inorderEnd, postorderStart + rootIndexInInorderArray - inorderStart, postorderEnd - 1);
            return node;
        }
    }
}
