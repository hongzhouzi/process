package cn.algorithm.leetcode;

/**
 * 114. 二叉树展开为链表
 * 给定一个二叉树，原地将它展开为一个单链表。
 * <p>
 * 例如，给定二叉树
 * <p>
 * 1
 * / \
 * 2   5
 * / \   \
 * 3   4   6
 * 将其展开为：
 * <p>
 * 1
 * \
 * 2
 * \
 * 3
 * \
 * 4
 * \
 * 5
 * \
 * 6
 */
public class P0114_FlattenBinaryTree2LinkedList {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.left = new TreeNode(5);
        root.left.left = new TreeNode(6);
//        new P0114_FlattenBinaryTree2LinkedList().flatten(root);
        new P0114_FlattenBinaryTree2LinkedList().flatten1(root);
        System.out.println(root);
    }

    /**
     * 先把左子树临时存起来，转换右子树，再转换左子树
     *
     * @param root
     */
    public void flatten(TreeNode root) {
        dfs(root);
    }

    // 这种方式左孩子会丢失
    private TreeNode dfs(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode right = dfs(root.right);
        TreeNode left = dfs(root.left);
        root.left = right;
        root.left.left = left;
        return root;
    }

    private TreeNode pre = null;
    public void flatten1(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten1(root.right);
        flatten1(root.left);
        root.right = pre;
        root.left= null;
        pre = root;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
