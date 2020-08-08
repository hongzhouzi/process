package cn.algorithm.leetcode;

import cn.examination.pass58.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * 99. 恢复二叉搜索树【困难】
 * 二叉搜索树中的两个节点被错误地交换。
 * 请在不改变其结构的情况下，恢复这棵树。
 * 示例 1:
 * 输入: [1,3,null,null,2]
 *    1
 *   /
 *  3
 *   \
 *    2
 * 输出: [3,1,null,null,2]
 *    3
 *   /
 *  1
 *   \
 *    2
 * 示例 2:
 * 输入: [3,1,4,null,null,2]
 *   3
 *  / \
 * 1   4
 *    /
 *   2
 * 输出: [2,1,4,null,null,3]
 *   2
 *  / \
 * 1   4
 *    /
 *   3
 * 进阶:
 *
 * 使用 O(n) 空间复杂度的解法很容易实现。
 * 你能想出一个只使用常数空间的解决方案吗？
 *
 * @author hongzhou.wei
 * @date 2020/8/8
 */
public class P0099_RecoverBinarySearchTree {

    /**
     * 二叉搜索树【中序遍历】后可以得到一个从小到大的有序序列，
     * 在遍历过程如果序列中的数不是升序，就需要将那两个元素交换位置。
     * 中序遍历树得到值的序列；
     * 线性遍历序列，找到序列中需要交换位置的两个元素
     * （第1个Ai<Ai+1取Ai, 第2个Ai<Ai+1取Ai+1）；
     * 遍历树，将需要交换的值交换。
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     *
     * @param root
     */
    public void recoverTree(TreeNode root) {
        List<Integer> nums = new ArrayList<Integer>();
        inorder(root, nums);
        int[] swapped = findTwoSwapped(nums);
        recover(root, 2, swapped[0], swapped[1]);

    }

    void inorder(TreeNode root, List<Integer> nums) {
        if (root == null) {
            return;
        }
        inorder(root.left, nums);
        nums.add(root.val);
        inorder(root.right, nums);
    }

    int[] findTwoSwapped(List<Integer> nums) {
        int n = nums.size();
        int x = -1, y = -1;
        for (int i = 0; i < n - 1; ++i) {
            // 非常精妙的写法，第1个Ai<Ai+1取Ai, 第2个Ai<Ai+1取Ai+1
            if (nums.get(i + 1) < nums.get(i)) {
                y = nums.get(i + 1);
                if (x == -1) {
                    x = nums.get(i);
                } else {
                    break;
                }
            }
        }
        return new int[]{x, y};
    }

    void recover(TreeNode root, int count, int x, int y) {
        if (root != null) {
            // 交换位置
            if (root.val == x || root.val == y) {
                root.val = root.val == x ? y : x;
                if (--count == 0) {
                    return;
                }
            }
            recover(root.right, count, x, y);
            recover(root.left, count, x, y);
        }
    }
}
