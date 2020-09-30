package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 501. 二叉搜索树中的众数
 * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
 * 假定 BST 有如下定义：
 * 结点左子树中所含结点的值小于等于当前结点的值
 * 结点右子树中所含结点的值大于等于当前结点的值
 * 左子树和右子树都是二叉搜索树
 * 例如：
 * 给定 BST [1,null,2,2],
 *
 *    1
 *     \
 *      2
 *     /
 *    2
 * 返回[2].
 * 提示：如果众数超过1个，不需考虑输出顺序
 * 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）
 *
 * @author hongzhou.wei
 * @date 2020/9/24
 */
public class P0501_FindModeinBinarySearchTree {

    public static void main(String[] args) {

    }
    static class Solution {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        List<Integer> res = new ArrayList<>();
        public int[] findMode(TreeNode root) {
            dfs(root);
            int [] ret = new int[res.size()];
            for (int i = 0; i < res.size(); i++) {
                ret[i] = res.get(i);
            }
            return ret;
        }
        void dfs(TreeNode root){
            if(root == null){
                return;
            }
            int key = root.val;
            int val = map.getOrDefault(key,0) + 1;
            map.put(key, val);
            if(val >= max){
                if(val > max){
                    res.clear();
                }
                res.add(key);
                max = val;
            }
            dfs(root.left);
            dfs(root.right);
        }
    }
}
