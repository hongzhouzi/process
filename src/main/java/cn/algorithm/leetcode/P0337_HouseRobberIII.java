package cn.algorithm.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 337. 打家劫舍 III
 * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。
 * 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似
 * 于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
 * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
 * 示例 1:
 * 输入: [3,2,3,null,3,null,1]
 *    3
 *   / \
 *  2   3
 *   \   \
 *   3   1
 * 输出: 7
 * 解释: 小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7.
 * 示例 2:
 * 输入: [3,4,5,1,3,null,1]
 *     3
 *    / \
 *   4   5
 *  / \   \
 * 1   3   1
 * 输出: 9
 * 解释: 小偷一晚能够盗取的最高金额 = 4 + 5 = 9.
 *
 * @author hongzhou.wei
 * @date 2020/8/5
 */
public class P0337_HouseRobberIII {
    public static void main(String[] args) {
        /*TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(3);
        root.right.right = new TreeNode(1);*/

        /*TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(5);
        root.right.right = new TreeNode(1);*/

       /* TreeNode root = new TreeNode(4);
        root.left = new TreeNode(1);
        root.left.left = new TreeNode(2);
        root.left.left.left = new TreeNode(3);*/

        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.left.right = new TreeNode(4);
        root.right = new TreeNode(3);
        System.out.println(new P0337_HouseRobberIII().rob(root));
    }

    /**
     * 根据节点的深度分别累加奇数和偶数深度之和。
     *
     * 这种算法的缺陷在于，若最大值为深度1和深度4的组成就不对了。
     * 改正思路：把每个层次累加之和计算出，最后每个层次间不相邻的进行组合。
     *
     * 以上这种缺陷在于，下面这种结构就不行了。
     *    2
     *   / \
     *  1   3
     *   \
     *    4
     *
     * @param root
     * @return
     */
    List<Integer> level = new LinkedList<>();
    public int rob(TreeNode root) {
       /* Queue<TreeNode> queue = new LinkedList<>();
        // 把根节点入队列
        queue.offer(root);
        while (!queue.isEmpty()){
            //1、把当前节点移除队列，输出内容
            TreeNode cur = queue.poll();
            //2、若存在左孩子，就将其加入队列
            if(cur.left != null){
                queue.offer(cur.left);
            }
            //3、若存在右孩子，就将其加入队列
            if(cur.right != null){
                queue.offer(cur.right);
            }
        }*/
        dfs(root, 1);
        // 不同层次间不相邻的进行组合
        int dp[] = new int[level.size()];
        int max = 0;
        for (int i = 0; i < level.size(); i++) {
            dp[i] = Math.max(i >= 2 ? dp[i - 2] : 0, i >= 3 ? dp[i - 3] : 0) + level.get(i);
            max = dp[i] > max ? dp[i] : max;
        }
        return max;
    }
    void dfs(TreeNode node, int depth) {
        if (node == null) {
            return;
        }
        // 每个同一深度的值相加
        if (level.size() >= depth) {
            level.set(depth-1, level.get(depth-1) + node.val);
        }else {
            level.add(node.val);
        }
        dfs(node.left, depth + 1);
        dfs(node.right, depth + 1);
    }


    /**
     * 暴力递归
     * 首先明确相邻节点不能偷，即选择了爷，就不能选择儿，但可以选择孙。
     *
     *
     * @param root
     * @return
     */
    public int rob1(TreeNode root) {
        if(root == null){
            return 0;
        }
        // 计算爷孙组合
        int money = root.val;
        if(root.left != null){
            money += rob1(root.left.left) + rob1(root.left.right);
        }
        if(root.right != null){
            money += rob1(root.right.left) + rob1(root.right.right);
        }
        // 返回爷孙组合与儿儿组合最大的那个
        return Math.max(money, rob1(root.left) + rob1(root.right));
    }

    /**
     * 在递归过程加个缓存，减少计算量
     *
     * @param root
     * @return
     */
    public int rob2(TreeNode root) {
        Map<TreeNode,Integer> cache = new HashMap<>();
        return robRecursion(root,cache);
    }

    private int robRecursion(TreeNode root, Map<TreeNode,Integer> cache) {
        if (root == null) {
            return 0;
        }
        if (cache.containsKey(root)) {
            return cache.get(root);
        }

        // 计算爷孙组合
        int money = root.val;
        if (root.left != null) {
            money += robRecursion(root.left.left,cache) + robRecursion(root.left.right,cache);
        }
        if (root.right != null) {
            money += robRecursion(root.right.left,cache) + robRecursion(root.right.right,cache);
        }
        // 返回爷孙组合与儿儿组合最大的那个
        int result = Math.max(money, robRecursion(root.left, cache) + robRecursion(root.right, cache));
        cache.put(root, result);
        return result;
    }


    /**
     * 换个思路优化【树中动态规划】
     * 我们使用一个大小为 2 的数组来表示 int[] res = new int[2] 0 代表不偷，1 代表偷
     * 任何一个节点能偷到的最大钱的状态可以定义为：
     * 当前节点选择不偷：当前节点能偷到的最大钱数 = 左孩子能偷到的钱 + 右孩子能偷到的钱
     * 当前节点选择偷：当前节点能偷到的最大钱数 = 左孩子选择自己不偷时能得到的钱 + 右孩子选择不偷时能得到的钱 + 当前节点的钱数
     *
     * @param root
     * @return
     */
    public int rob3(TreeNode root) {
        int[] result = robInternal(root);
        return Math.max(result[0], result[1]);
    }
    public int[] robInternal(TreeNode root) {
        int[] result = new int[2];
        if(root == null){
            return result;
        }
        int[] left = robInternal(root.left);
        int[] right = robInternal(root.right);
        result[0] = Math.max(left[0], left[1]) + Math.max(right[0],right[1]);
        result[1] = left[0] + right[0] + root.val;
        return result;
    }
}
