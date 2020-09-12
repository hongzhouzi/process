package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 637. 二叉树的层平均值
 * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组。
 * 示例 1：
 * 输入：
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 输出：[3, 14.5, 11]
 * 解释：
 * 第 0 层的平均值是 3 ,  第1层是 14.5 , 第2层是 11 。因此返回 [3, 14.5, 11] 。
 *
 * @author hongzhou.wei
 * @date 2020/9/12
 */
public class P0637_AverageofLevelsinBinaryTree {

    public static void main(String[] args) {
        /*TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.right = new TreeNode(5);*/
        TreeNode root = new TreeNode(2147483647);
        root.left = new TreeNode(2147483647);
        root.right = new TreeNode(2147483647);
        System.out.println(averageOfLevels(root));
    }

    static public List<Double> averageOfLevels(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(root, res, 0);
        List<Double> ret = new LinkedList<>();
        for (List<Integer> integers : res) {
            long sum = 0;
            for (Integer i : integers) {
                sum += i;
            }
            ret.add((double) sum / integers.size());
        }
        return ret;
    }

    static void dfs(TreeNode root, List<List<Integer>> ret, int level) {
        if (Objects.isNull(root)) {
            return;
        }
        if (ret.size() == level) {
            ret.add(new ArrayList<>());
        }
        // 在对应层次将值添加到结果集
        ret.get(level).add(root.val);
        dfs(root.left, ret, level + 1);
        dfs(root.right, ret, level + 1);
    }

    // ================================BFS====================================

    public List<Double> averageOfLevels1(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        list.add(root);
        List<Double> ret = new ArrayList<>();
        bfs(ret, list);
        return ret;
    }

    /**
     * 广度优先遍历
     *
     * @param ret     返回值
     * @param parents 父节点集合
     */
    void bfs(List<Double> ret, List<TreeNode> parents) {
        if (Objects.isNull(parents) || parents.isEmpty()) {
            return;
        }

        List<TreeNode> temp = new LinkedList<>();
        double sum = 0;
        int count = 0;
        // 遍历父节点集合，并计算该层值的平均值
        for (TreeNode node : parents) {
            if (Objects.nonNull(node.left)) {
                temp.add(node.left);
            }
            if (Objects.nonNull(node.right)) {
                temp.add(node.right);
            }
            sum += node.val;
            count++;
        }
        ret.add(sum / count);
        // 继续往下层搜索
        bfs(ret, temp);
    }
}
