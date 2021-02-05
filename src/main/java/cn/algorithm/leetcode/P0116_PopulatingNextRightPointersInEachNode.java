package cn.algorithm.leetcode;

import javafx.scene.transform.Rotate;

/**
 * 116. 填充每个节点的下一个右侧节点指针
 * 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
 * struct Node {
 *   int val;
 *   Node *left;
 *   Node *right;
 *   Node *next;
 * }
 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
 * 初始状态下，所有 next 指针都被设置为 NULL。
 *
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public class P0116_PopulatingNextRightPointersInEachNode {


    static    class Solution {
        Node last = null, nextStart = null;
        public Node connect(Node root) {
            if(root == null){
                return null;
            }
            Node start = root;
            while (start != null){
                // 每层遍历将标识清空
                last = nextStart = null;
                // 通过next像遍历链表一样遍历
                for(Node p = start; p!=null ; p = p.next){
                    if(p.left != null){
                        handle(p.left);
                    }
                    if(p.right !=null){
                        handle(p.right);
                    }
                }
                // 准备遍历下层
                start = nextStart;
            }
            return root;
        }
        void handle(Node p){
            // 上个节点不为空，当前节点就接在后面
            if(last != null){
                last.next = p;
            }
            // 记录下层开始的节点
            if(nextStart == null){
                nextStart = p;
            }
            last = p;
        }
    }

    static class Solution1 {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            Node leftMost = root;
            while (leftMost.left != null) {
                Node head = leftMost;
                while (head != null) {
                    head.left.next = head.right;
                    if (head.next != null) {
                        head.right.next = head.next.left;
                    }
                    head = head.next;
                }
                leftMost = leftMost.left;
            }
            return root;
        }
    }
    static class Node {
        public int  val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
}
