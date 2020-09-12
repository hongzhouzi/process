package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 257. 二叉树的所有路径
 * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 *
 * 输入:
 *
 *    1
 *  /   \
 * 2     3
 *  \
 *   5
 *
 * 输出: ["1->2->5", "1->3"]
 *
 * 解释: 所有根节点到叶子节点的路径为: 1->2->5, 1->3
 *
 * @author hongzhou.wei
 * @date 2020/9/4
 */
public class P0257_BinaryTreePaths {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(111);
        root.left = new TreeNode(222);
        root.right = new TreeNode(333);
        root.left.left = new TreeNode(444);
        root.left.right = new TreeNode(555);
//        TreeNode root = new TreeNode();
        System.out.println(new P0257_BinaryTreePaths().binaryTreePaths(root));
        System.out.println(new Solution1().binaryTreePaths(root));
    }

    List<String> ret = new LinkedList<>();

    public List<String> binaryTreePaths(TreeNode root) {
        dfs(root, new ArrayList<>());
        return ret;
    }

    void dfs(TreeNode root, List<Integer> vals) {
        if (Objects.isNull(root)) {
            return;
        }
        // 左右子节点都为空，说明当前节点是叶子节点
        if (Objects.isNull(root.left) && Objects.isNull(root.right)) {
            // 将当前叶子节点添加到结果集
            vals.add(root.val);
            // 处理拼接符
            StringBuilder addSb = new StringBuilder();
            for (int i = 0, len = vals.size(); i < len; i++) {
                addSb.append(vals.get(i));
                if (i == len - 1) {
                    continue;
                }
                addSb.append("->");
            }
            ret.add(addSb.toString());
            // 回溯，将当前值从结果集中移除
            vals.remove(vals.size() - 1);
            return;
        }
        // 将当前值放入结果集
        vals.add(root.val);
        // 深度优先遍历左右节点
        dfs(root.left, vals);
        dfs(root.right, vals);
        // 回溯，将当前值从结果集中移除
        vals.remove(vals.size() - 1);
    }

    static class Solution1{
        List<String> ret = new LinkedList<>();

        public List<String> binaryTreePaths(TreeNode root) {
            dfs(root, new StringBuilder());
            return ret;
        }

        void dfs(TreeNode root, StringBuilder sb){
            if(Objects.isNull(root)){
                return;
            }
            // 处理箭头号
            sb.append(sb.length() == 0 ? "" : "->");
            sb.append(root.val);
            // 到达叶子节点
            if(Objects.isNull(root.left) && Objects.isNull(root.right)){
                ret.add(sb.toString());
                return;
            }
            // 这儿用new的方式，之前的就不会受影响
            dfs(root.left, new StringBuilder(sb));
            dfs(root.right, new StringBuilder(sb));
        }
    }
}


