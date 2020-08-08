package cn.algorithm.leetcode.weekly._199;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongzhou.wei
 * @date 2020/7/26
 */
public class P5474_NumberofGoodLeafNodesPairs {
    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.left.right = new TreeNode(4);
        treeNode.right = new TreeNode(3);
        /*TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.left.left = new TreeNode(4);
        treeNode.left.right = new TreeNode(5);
        treeNode.right = new TreeNode(3);
        treeNode.right.left = new TreeNode(6);
        treeNode.right.right = new TreeNode(7);*/

        System.out.println(new P5474_NumberofGoodLeafNodesPairs().countPairs1(treeNode, 3));
        System.out.println(new P5474_NumberofGoodLeafNodesPairs().countPairs5(treeNode, 3));
    }

    /*public int countPairs(TreeNode root, int distance) {
        return 0;
    }*/

    // ========================参考=====================================
    private int count = 0;

    public int countPairs1(TreeNode root, int distance) {
        count = 0;
        countPairs(new ArrayList<>(), root, 0, distance);
        return count;
    }

    private void countPairs(ArrayList<Integer> list, TreeNode root, int depth, int distance) {
        if (root == null) {
            return;
        }
        // 叶子节点
        else if (root.left == null && root.right == null) {
            list.add(depth);
        } else {
            // 当前节点左右子节点的深度
            ArrayList<Integer> left = new ArrayList<>(), right = new ArrayList<>();
            countPairs(left, root.left, depth + 1, distance);
            countPairs(right, root.right, depth + 1, distance);
            list.addAll(left);
            list.addAll(right);
            for (int i : left) {
                for (int j : right) {
                    // 当前节点的子节点之间的距离
                    if (i + j - 2 * depth <= distance) {
                        count++;
                    }
                }
            }
        }
    }

    int num = 0;
    int distance = 0;

    /**
     * 采用深度优先遍历，计算出每个子叶节点的深度，再根据它们最近公共祖先的深度计算出
     * 叶子节点直接的最短路径长度
     *
     * @param root
     * @param distance
     * @return
     */
    public int countPairs5(TreeNode root, int distance) {
        this.distance = distance;
        dfs(root, new ArrayList<>(), 0);
        return num;
    }

    void dfs(TreeNode root, List<Integer> depthRecords, int curDepth) {
        if (root == null) {
            return;
        }
        // 遍历到叶子节点，记录下该叶子节点的深度
        if (root.left == null && root.right == null) {
            depthRecords.add(curDepth);
        }
        List<Integer> leftChildrenDep = new ArrayList<>(), rightChildrenDep = new ArrayList<>();
        // 遍历左右子节点，得到左右叶子节点的深度
        this.dfs(root.left, leftChildrenDep, curDepth + 1);
        this.dfs(root.right, rightChildrenDep, curDepth + 1);
        depthRecords.addAll(leftChildrenDep);
        depthRecords.addAll(rightChildrenDep);
        // 遍历当前左右子节点的深度，结合当前节点的深度判断是否符合条件
        for (Integer leftDep : leftChildrenDep) {
            for (Integer rightDep : rightChildrenDep) {
                if (leftDep + rightDep - curDepth * 2 <= distance) {
                    num++;
                }
            }
        }
    }


    // ===========================================
    /*int ans;
    int[] dfs(TreeNode root, int distance) {
        if (root == null) {
            return null;
        }
        int dis[] = new int[10];
        int left[] = dfs(root.left, distance);
        int right[] = dfs(root.right, distance);
        if (left != null && right != null) {
            for (int i = 0; i < 9; i++) {
                if(left[i] == 0) {
                    continue;
                }
                for (int k = 0; k < 9; k++) {
                    int len = i + 1 + k + 1;
                    if (len > distance) {
                        break;
                    }
                    // if(left[i] > 0 && right[k] > 0) {
                    //     System.out.println("pos: "+ i+" "+ k+" "+ root.val+" "+ left[i]+" "+ right[k]);
                    // }
                    ans += left[i] * right[k];
                }
            }
        }
        if(left == null && right == null) {
            dis[0] = 1;
        }
        for (int i = 1; i < 10; i++) {
            if (left != null) {
                dis[i] += left[i - 1];
            }
            if (right != null) {
                dis[i] += right[i - 1];
            }
        }
        return dis;
    }

    public int countPairs2(TreeNode root, int distance) {
        ans = 0;
        dfs(root, distance);
        return ans;
    }*/

    // =========================================================
   /* public int countPairs3(TreeNode root, int distance) {
        dfs(root, distance);
        return ans;
    }
    int ans = 0;
    public void handle(TreeNode root, int[] state, int d){
        int[] ret = dfs(root, d);
        for(int i = 0; i <= d; i++){
            for(int j = 0; j <= d; j++){
                if(i + j + 1 <= d){
                    ans += ret[i] * state[j];
                }
            }
        }
        for(int i = 0; i < d; i++){
            state[i + 1] += ret[i];
        }

    }
    public int[] dfs(TreeNode root, int d){
        int[] state = new int[d + 1];
        // 遍历到叶子节点，返回状态
        if(root.left == null && root.right == null){
            state[0] = 1;
            return state;
        }
        if(root.left != null){
            handle(root.left, state, d);
        }
        if(root.right != null){
            handle(root.right, state, d);
        }
        return state;
    }*/
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}