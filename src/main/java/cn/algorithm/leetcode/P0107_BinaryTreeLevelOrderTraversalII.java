package cn.algorithm.leetcode;

import java.util.*;

/**
 * 107. 二叉树的层次遍历 II
 * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 返回其自底向上的层次遍历为：
 * [
 * [15,7],
 * [9,20],
 * [3]
 * ]
 *
 * @author hongzhou.wei
 * @date 2020/9/6
 */
public class P0107_BinaryTreeLevelOrderTraversalII {


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.right = new TreeNode(5);
        System.out.println(new P0107_BinaryTreeLevelOrderTraversalII().levelOrderBottom(root));
        System.out.println(new P0107_BinaryTreeLevelOrderTraversalII().levelOrderBottom1(root));
    }

    /**
     * 用两个queue存放待遍历的数据，一个queue存放父节点，一个queue存放子节点，父节点队列空了时将子节点的queue放在父节点
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> ret = new LinkedList<>();
        if (Objects.isNull(root)) {
            return ret;
        }
        LinkedList<TreeNode> queueFather = new LinkedList<>();
        LinkedList<TreeNode> queueSon = new LinkedList<>();
        List<Integer> temp = new LinkedList<>();
        queueFather.offer(root);
        while (!queueFather.isEmpty() || !queueSon.isEmpty()) {
            // 父节点队列空了时数据放入结果集，并将子节点的queue放在父节点
            if (queueFather.isEmpty()) {
                ret.addFirst(temp);
                temp = new LinkedList<>();
                while (!queueSon.isEmpty()) {
                    queueFather.offer(queueSon.poll());
                }
            }
            //1、把当前节点移除队列，输出内容
            TreeNode cur = queueFather.poll();
            temp.add(cur.val);
            //2、若存在左孩子，就将其加入队列
            if (Objects.nonNull(cur.left)) {
                queueSon.offer(cur.left);
            }
            //3、若存在右孩子，就将其加入队列
            if (Objects.nonNull(cur.right)) {
                queueSon.offer(cur.right);
            }
        }
        ret.addFirst(temp);
        return ret;
    }


    public List<List<Integer>> levelOrderBottom0(TreeNode root) {
        List<List<Integer>> levelOrder = new LinkedList<>();
        if (root == null) {
            return levelOrder;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int size = queue.size();
            // 将当前层次的遍历完
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                TreeNode left = node.left, right = node.right;
                if (left != null) {
                    queue.offer(left);
                }
                if (right != null) {
                    queue.offer(right);
                }
            }
            // 头插到结果集中
            levelOrder.add(0, level);
        }
        return levelOrder;
    }


    /**
     * 【dfs】遍历时将对应层数据添加到相应的list中
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom1(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        dfs(root, ret, 0);
        return ret;
    }

    void dfs(TreeNode root, List<List<Integer>> ret, int level) {
        if (Objects.isNull(root)) {
            return;
        }
        // 头插法，添加到头部
        if (ret.size() == level) {
            ret.add(0, new ArrayList<>());
        }
        // 在对应层次将值添加到结果集
        ret.get(ret.size() - 1 - level).add(root.val);
        dfs(root.left, ret, level + 1);
        dfs(root.right, ret, level + 1);
    }
}
