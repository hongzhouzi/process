package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @author hongzhou.wei
 * @date 2020/9/14
 */
public class P0094_BinaryTreeInorderTraversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(111);
        root.left = new TreeNode(222);
        root.right = new TreeNode(333);
        root.left.left = new TreeNode(444);
        root.left.right = new TreeNode(555);

        System.out.println(inorderTraversal(root));
        System.out.println(preorderTraversal(root));
        System.out.println(postorderTraversal(root));
    }
    // ============================中序===============================

    /**
     * 【迭代遍中序遍历二叉树】
     *
     * @param root
     * @return
     */
    static public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> ret = new ArrayList<>();
        TreeNode curNode = root;
        while (curNode != null || !stack.isEmpty()) {
            // 迭代访问左孩子
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            curNode = stack.pop();
            // 在迭代下个右孩子前，输出值
            ret.add(curNode.val);
            // 访问右孩子
            curNode = curNode.right;
        }
        return ret;
    }

    static public List<Integer> inorderTraversal1(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> ret = new ArrayList<>();
        TreeNode curNode = root;
        while (curNode != null || !stack.isEmpty()) {
            if (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            } else {
                curNode = stack.pop();
                ret.add(curNode.val);
                curNode = curNode.right;
            }
        }
        return ret;
    }

    // ============================先序===============================

    static public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> ret = new ArrayList<>();
        TreeNode curNode = root;
        while (curNode != null || !stack.isEmpty()) {
            // 输出、迭代访问左孩子
            while (curNode != null) {
                stack.push(curNode);
                // 在迭代下个左孩子前，输出值
                ret.add(curNode.val);
                curNode = curNode.left;
            }
            // 访问右孩子
            curNode = stack.pop();
            curNode = curNode.right;
        }
        return ret;
    }


    // ============================后序===============================
    /*
    后序遍历处理思路和先序中序不一样，需要有逆向思维。后序输出结果（左子 右子 根）---> 栈存（根 右子 左子）---> 出栈结果即得
     */

    static public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        // 后序遍历用
        Stack<TreeNode> output = new Stack<>();
        List<Integer> ret = new ArrayList<>();
        TreeNode curNode = root;
        while (curNode != null || !stack.isEmpty()) {
            while (curNode != null) {
                stack.push(curNode);
                // 添加到输出集合
                output.push(curNode);
                // 迭代访问右孩子
                curNode = curNode.right;
            }
            // 访问左孩子
            curNode = stack.pop();
            curNode = curNode.left;
        }
        // 后序遍历需要
        while (!output.isEmpty()) {
            ret.add(output.pop().val);
        }
        return ret;
    }

    static public List<Integer> postorderTraversal1(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        //后序遍历用
        Stack<TreeNode> output = new Stack<>();
        List<Integer> ret = new ArrayList<>();
        TreeNode curNode = root;
        while (curNode != null || !stack.isEmpty()) {
            if (curNode != null) {
                stack.push(curNode);
                // 添加到输出集合
                output.push(curNode);
                curNode = curNode.right;
            } else {
                curNode = stack.pop();
                curNode = curNode.left;
            }
        }
        //后序遍历需要
        while (!output.isEmpty()) {
            ret.add(output.pop().val);
        }
        return ret;
    }
}
