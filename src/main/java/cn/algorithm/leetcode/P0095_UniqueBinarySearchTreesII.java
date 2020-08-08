package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 95. 不同的二叉搜索树 II
 * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。
 * 示例：
 * 输入：3
 * 输出：
 * [
 *   [1,null,3,2],
 *   [3,2,null,1],
 *   [3,1,null,null,2],
 *   [2,1,3],
 *   [1,null,2,null,3]
 * ]
 * 解释：
 * 以上的输出对应以下 5 种不同结构的二叉搜索树：
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 *
 * @author hongzhou.wei
 * @date 2020/7/21
 */
public class P0095_UniqueBinarySearchTreesII {

    public static void main(String[] args) {
        System.out.println(generateTrees1(5));
    }

    /**
     * 依次使每个数成为根节点，再找左子树和右子树
     *
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }
        return getChildTree(1, n);
    }

    List<TreeNode> getChildTree(int start, int end) {
        List<TreeNode> ans = new ArrayList<>();
        // 没有数字，加入null
        if (start > end) {
            ans.add(null);
            return ans;
        }
        // 只有一个数字，将当前作为一棵树加入结果集中
        if (start == end) {
            TreeNode root = new TreeNode(start);
            ans.add(root);
            return ans;
        }

        // 让每个数都成为根节点，分别找他们的左右子树
        for (int i = start; i <= end; i++) {
            // 获取左右子树
            List<TreeNode> leftChildTree = getChildTree(start, i - 1);
            List<TreeNode> rightChildTree = getChildTree(i + 1, end);
            // 组合左右子树，并添加到结果集中
            for (TreeNode leftNode : leftChildTree) {
                for (TreeNode rightNode : rightChildTree) {
                    TreeNode root = new TreeNode(i);
                    root.left = leftNode;
                    root.right = rightNode;
                    ans.add(root);
                }
            }
        }
        return ans;
    }


    /**
     * 【动态规划】
     * 在上面解题思路之上做优化，上面计算过程会存在重复计算。比如[1,2]组成的子树和[1 2] ， [2 3] ， [3 4] ... [99 100]
     * 组成的子树结构可能是一样的只是数字不一样而已，但我们在计算子树时这种情况是重复计算的。对[1 2]计算，然后加上数字的偏差
     * 复制树就可实现那种效果。
     * 总体思想就是求长度为 2 的所有情况，求长度为 3 的所有情况直到 n。而求长度为 len 的所有情况，
     * 我们只需要求出一个代表 [ 1 2 ... len ] 的所有情况，其他的话加上一个偏差，加上当前根节点即可。
     * <p>
     * 状态： 有n个数时，这些数所有可能组成的BST数
     * 状态转移方程：dp[n] = dp[left] * dp[right]所有组合；left = 遍历的当前数作为根节点-1，right = 树长度-遍历当前数作为根节点
     * 其中dp[left]是以及计算过的，可以直接取到，dp[right]是需要计算的，而计算时可以用树的复制+偏差的方式来计算
     *
     * @param n
     * @return
     */
    static public List<TreeNode> generateTrees1(int n) {
        // dp中保存有n个数时，这些数所有可能组成的BST树
        ArrayList<TreeNode>[] dp = new ArrayList[n + 1];
        dp[0] = new ArrayList<TreeNode>();
        if (n == 0) {
            return dp[0];
        }
        dp[0].add(null);
        // 长度为 1 到 n
        for (int len = 1; len <= n; len++) {
            dp[len] = new ArrayList<TreeNode>();
            // 将不同的数字作为根节点，只需要考虑到 len
            for (int i = 1; i <= len; i++) {
                // 左子树的长度
                int left = i - 1;
                // 右子树的长度
                int right = len - i;
                // 合并左右子树，并添加到结果集中。左右子树根据左右子树对应的长度直接在dp中取即可
                for (TreeNode leftTree : dp[left]) {
                    for (TreeNode rightTree : dp[right]) {
                        TreeNode treeRoot = new TreeNode(i);
                        // 此时左子树已经计算过了，可以直接取；右子树需要计算
                        treeRoot.left = leftTree;
                        // 克隆右子树并且加上偏差
                        treeRoot.right = clone(rightTree, i);
                        dp[len].add(treeRoot);
                    }
                }
            }
        }
        return dp[n];
    }

    /**
     * 树的复制并且加上偏差
     *
     * @param n      树的节点
     * @param offset 偏差
     * @return
     */
    static private TreeNode clone(TreeNode n, int offset) {
        if (n == null) {
            return null;
        }
        TreeNode node = new TreeNode(n.val + offset);
        node.left = clone(n.left, offset);
        node.right = clone(n.right, offset);
        return node;
    }

}
