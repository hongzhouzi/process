package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class P0117_PopulatingNextRightPointersinEachNodeII {
    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.right = new Node(7);

        Node n = new P0117_PopulatingNextRightPointersinEachNodeII.Solution1().connect(root);
        System.out.println(n.val);
    }

    /**
     * 类似于广度优先遍历，用广度优先遍历过程中就能够知道同一深度下的节点，然后把当前节点指向下一个节点
     */
    static class Solution {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            Deque<Node> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                // 这儿的size就是当前同一深度的节点多少，在下面for循环遍历时就根据这个size取前size个就OK
                int size = queue.size();
                LinkedList<Node> stack = new LinkedList<>();
                // 循环该层次的node节点
                for (int i = 0; i < size; i++) {
                    Node poll = queue.poll();
                    stack.push(poll);
                    if (poll.left != null) {
                        queue.offer(poll.left);
                    }
                    if (poll.right != null) {
                        queue.offer(poll.right);
                    }
                }
                // 倒着指向
                Node nextNode = stack.pop();
                nextNode.next = null;
                while (!stack.isEmpty()) {
                    Node node = stack.pop();
                    node.next = nextNode;
                    nextNode = node;
                }
            }
            return root;
        }
    }

    /**
     * 对空间进行优化
     */
    static class Solution1 {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            Deque<Node> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                Node prevNode = null;
                // 循环该层次的node节点
                for (int i = 0; i < size; i++) {
                    Node poll = queue.poll();
                    // 让前置节点指向当前节点，并将前置节点位置后移
                    if (i != 0) {
                        prevNode.next = poll;
                    }
                    prevNode = poll;

                    // 将左右子节点加入队列
                    if (poll.left != null) {
                        queue.offer(poll.left);
                    }
                    if (poll.right != null) {
                        queue.offer(poll.right);
                    }
                }
                // 遍历结束将最后节点指向null
                prevNode.next = null;
            }
            return root;
        }
    }

    static class Solution1_1 {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            Deque<Node> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                // 循环该层次的node节点
                for (int i = 0; i < size; i++) {
                    Node poll = queue.poll();
                    // 让前置节点指向当前节点，并将前置节点位置后移
                    if (i < size -1) {
                        poll.next = queue.peek();
                    }
                    // 将左右子节点加入队列
                    if (poll.left != null) {
                        queue.offer(poll.left);
                    }
                    if (poll.right != null) {
                        queue.offer(poll.right);
                    }
                }
            }
            return root;
        }
    }

    static class Solution2 {
        ArrayList<Node> pres = new ArrayList<>();

        public Node connect(Node root) {
            if (root == null) return root;
            dfs(root, 0);
            return root;
        }

        private void dfs(Node root, int levelNum) {
            if (pres.size() == levelNum)
                pres.add(root);
            else {
                pres.get(levelNum).next = root;
                pres.remove(levelNum);
                pres.add(levelNum, root);
            }

            if (root.left != null)
                dfs(root.left, levelNum + 1);
            if (root.right != null)
                dfs(root.right, levelNum + 1);
        }
    }


    static class Solution3 {
        public Node connect(Node root) {
            if (root == null) return null;
            if (root.left != null) {
                if (root.right != null) {
                    root.left.next = root.right;
                } else {
                    root.left.next = getNext(root.next);
                }
            }
            if(root.right != null) {
                root.right.next = getNext(root.next);
            }
            connect(root.right);
            connect(root.left);
            return root;
        }
        private Node getNext(Node root) {
            while (root != null) {
                if (root.left != null) return root.left;
                if (root.right != null) return root.right;
                root = root.next;
            }
            return null;
        }
    }
    static class Solution4 {
        /**
         * 分别为标识上个节点、下层遍历的开始节点
         */
        Node last = null, nextStart = null;

        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            Node start = root;
            while (start != null) {
                // 每层遍历前将标识清空
                last = null;
                nextStart = null;
                // 通过next像遍历链表的方式遍历
                for (Node p = start; p != null; p = p.next) {
                    if (p.left != null) {
                        handle(p.left);
                    }
                    if (p.right != null) {
                        handle(p.right);
                    }
                }
                // 当前层遍历完了，准备遍历下层
                start = nextStart;
            }
            return root;
        }

        public void handle(Node p) {
            // 上个节点不为空，当前节点就接在后面
            if (last != null) {
                last.next = p;
            }
            // 记录下层开始的节点
            if (nextStart == null) {
                nextStart = p;
            }
            // 标识上个节点的指针后移
            last = p;
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

    ;


}
