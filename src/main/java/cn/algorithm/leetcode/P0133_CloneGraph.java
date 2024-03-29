package cn.algorithm.leetcode;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 133. 克隆图
 * 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
 * 图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。
 * class Node {
 *     public int val;
 *     public List<Node> neighbors;
 * }
 * 测试用例格式：
 * 简单起见，每个节点的值都和它的索引相同。例如，第一个节点值为 1（val = 1），第二个节点值为 2（val = 2），以此类推。该图在测试用例中使用邻接列表表示。
 * 邻接列表 是用于表示有限图的无序列表的集合。每个列表都描述了图中节点的邻居集。
 * 给定节点将始终是图中的第一个节点（值为 1）。你必须将 给定节点的拷贝 作为对克隆图的引用返回。
 *
 * @author hongzhou.wei
 * @date 2020/8/12
 */
public class P0133_CloneGraph {

    public static void main(String[] args) {
        Node node = new Node();
        node.neighbors.add(new Node(2));
        node.neighbors.add(new Node(4));

        Node node1 = new Node();
        node1.neighbors.add(new Node(1));
        node1.neighbors.add(new Node(3));

        Node node2 = new Node();
        node2.neighbors.add(new Node(2));
        node2.neighbors.add(new Node(4));

        Node node3 = new Node();
        node3.neighbors.add(new Node(1));
        node3.neighbors.add(new Node(3));

        Node root = new Node();
        root.neighbors.add(node);
        root.neighbors.add(node1);
        root.neighbors.add(node2);
        root.neighbors.add(node3);
        Node ret = new P0133_CloneGraph().cloneGraph1(root);
        System.out.println(ret);

    }

    /**
     * 【dfs】
     * 当成处理树的思路在处理，这是不对的，对于图必须有标识是否已遍历的的容器，否则会找不到出口
     *
     * @param node
     * @return
     */
    public Node cloneGraphError(Node node) {
        Node root = new Node();
        Map<Node, Node> map = new HashMap<>();
        dfsError(node, root);
        return root;
    }

    void dfsError(Node source, Node target) {
        if (source.neighbors == null || source.neighbors.size() == 0) {
            return;
        }
        target.val = source.val;
        for (Node neighbor : source.neighbors) {
            Node clone = new Node();
            clone.val = neighbor.val;
            target.neighbors.add(clone);
            // 递归
            dfsError(neighbor, clone);
        }
    }


    /**
     * 【dfs】用哈希表存放已遍历的节点
     *
     * @param node
     * @return
     */
    public Node cloneGraph1(Node node) {
        Map<Node, Node> lookup = new HashMap<>();
        return dfs1(node, lookup);
    }

    private Node dfs1(Node node, Map<Node, Node> nodeMap) {
        if (node == null) {
            return null;
        }
        // 若已经遍历过该节点就直接返回
        if (nodeMap.containsKey(node)) {
            return nodeMap.get(node);
        }
        Node clone = new Node();
        clone.val = node.val;
        // 标识该节点已遍历过
        nodeMap.put(node, clone);
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(dfs1(neighbor, nodeMap));
        }
        return clone;
    }

    public Node cloneGraph1_1(Node node) {
        Map<Integer, Node> lookup = new HashMap<>();
        return dfs(node, lookup);
    }
    Node dfs(Node node, Map<Integer,Node> nodeMap){
        if(node == null){
            return null;
        }
        if (nodeMap.containsKey(node.val)){
            return nodeMap.get(node.val);
        }
        Node clone = new Node(node.val, new ArrayList<>());
        nodeMap.put(node.val, clone);
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(dfs(neighbor,nodeMap));
        }
        return clone;
    }


    /**
     * 【bfs】
     *
     * @param node
     * @return
     */
    public Node cloneGraph2(Node node) {
        if (node == null) {
            return null;
        }
        Node clone = new Node(node.val, new ArrayList<>());
        Map<Integer, Node> nodeMap = new HashMap<>();
        nodeMap.put(node.val, clone);
        Deque<Node> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            for (Node neighbor : curNode.neighbors) {
                if (!nodeMap.containsKey(neighbor.val)) {
                    nodeMap.put(neighbor.val, new Node(neighbor.val, new ArrayList<>()));
                    queue.offer(neighbor);
                }
                nodeMap.get(curNode.val).neighbors.add(nodeMap.get(neighbor.val));
            }
        }
        return clone;
    }
}
class Node {
    public int        val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
