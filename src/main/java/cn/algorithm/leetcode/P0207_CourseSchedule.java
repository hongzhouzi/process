package cn.algorithm.leetcode;

import java.util.*;

/**
 * 207. 课程表
 * 你这个学期必须选修 numCourse 门课程，记为 0 到 numCourse-1 。
 * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们：[0,1]
 * 给定课程总量以及它们的先决条件，请你判断是否可能完成所有课程的学习？
 * 示例 1:
 * 输入: 2, [[1,0]]
 * 输出: true
 * 解释: 总共有 2 门课程。学习课程 1 之前，你需要完成课程 0。所以这是可能的。
 * 示例 2:
 * 输入: 2, [[1,0],[0,1]]
 * 输出: false
 * 解释: 总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0；并且学习课程 0 之前，你还应先完成课程 1。这是不可能的。
 * 提示：
 * 输入的先决条件是由 边缘列表 表示的图形，而不是 邻接矩阵 。详情请参见图的表示法。
 * 你可以假定输入的先决条件中没有重复的边。
 * 1 <= numCourses <= 10^5
 *
 * @author hongzhou.wei
 * @date 2020/8/4
 */
public class P0207_CourseSchedule {
    public static void main(String[] args) {

    }

    /*
    给定一个包含 n 个节点的有向图 G，我们给出它的节点编号的一种排列，如果满足：对于图 G 中的任意一条有向边 (u, v)，
    u 在排列中都出现在 v 的前面。那么称该排列是图 GG 的「拓扑排序」
    如果图 G 中存在环（即图 G 不是「有向无环图」），那么图 G 不存在拓扑排序。
    如果图 G 是有向无环图，那么它的拓扑排序可能不止一种。
    举一个最极端的例子，如果图 G 值包含 n 个节点却没有任何边，那么任意一种编号的排列都可以作为拓扑排序。
     */


    List<List<Integer>> edges;
    int[]               visited;
    boolean             valid = true;

    /**
     * 【深度优先搜索】
     * 对于一个节点 u，如果它的所有相邻节点都已经搜索完成，那么在搜索回溯到 u 的时候，u 本身也会变成一个已经搜索完成的节点。
     * 这里的「相邻节点」指的是从 u 出发通过一条有向边可以到达的所有节点。
     * 假设我们当前搜索到了节点 u，如果它的所有相邻节点都已经搜索完成，那么这些节点都已经在栈中了，此时我们就可以把 u 入栈。
     * 可以发现，如果我们从栈顶往栈底的顺序看，由于 u 处于栈顶的位置，那么 u 出现在所有 u 的相邻节点的前面。
     * 因此对于 u 这个节点而言，它是满足拓扑排序的要求的。
     * <p>
     * 这样以来，我们对图进行一遍深度优先搜索。当每个节点进行回溯的时候，我们把该节点放入栈中。最终从栈顶到栈底的序列就是一种拓扑排序。
     * <p>
     * 时间复杂度: O(n+m)，其中 n 为课程数，m 为先修课程的要求数
     * 空间复杂度: O(n+m)
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            edges.add(new ArrayList<>());
        }
        // 记录节点访问状态
        visited = new int[numCourses];
        // edges：[先决条件1[……], 先决条件2[……]] 即：[当前节点1[相邻节点1……], 当前节点2[相邻节点1……]]
        for (int[] info : prerequisites) {
            edges.get(info[1]).add(info[0]);
        }
        // 遍历每个节点，valid 标识能否满足拓扑排序
        for (int i = 0; i < numCourses && valid; ++i) {
            // 当前点没有被访问，则遍历访问
            if (visited[i] == 0) {
                dfs(i);
            }
        }
        return valid;
    }

    void dfs(int u) {
        // 将当前节点标记为访问中
        visited[u] = 1;
        // 遍历当前节点的所有相邻节点
        for (int v : edges.get(u)) {
            // 相邻节点没有访问则访问
            if (visited[v] == 0) {
                dfs(v);
                // 已经不能满足拓扑排序，就直接返回了
                if (!valid) {
                    return;
                }
            } else if (visited[v] == 1) {
                // 访问当前节点的相邻节点时出现了相邻节点也在访问中，则不存在拓扑排序，直接返回
                valid = false;
                return;
            }
        }
        // 将当前节点标记为访问过
        visited[u] = 2;
    }

    // ===========================================================================================



    /**
     * 【广度优先】
     * 考虑拓扑排序最前面的节点，该节点不会有任何的入边，也就是没有任何先修课程的要求。当我们把这个节点加入答案之后
     * 我们可以移除它的所有出边，代表它的相邻节点少了一门先修课程的要求。如果某个相邻节点变成了‘没有任何入边的节点’
     * 代表着这门课程可以学习了，按这样的流程，不断将没有入边的节点加入答案，直到答案包含了所有的节点（得到拓扑排序）
     * 或不存在没有入边的节点（不存在拓扑排序）。
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinishBfs(int numCourses, int[][] prerequisites) {
        // 初始化边、边的入度
        List<List<Integer>>  edgesBfs = new ArrayList<>();
        int[]               indeg = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            edgesBfs.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            edgesBfs.get(prerequisites[i][1]).add(prerequisites[i][0]);
            ++indeg[prerequisites[i][0]];
        }

        // 准备队列，将入度为0的边加入队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                queue.offer(i);
            }
        }

        // 处理访问的元素，并得到拓扑排序
//        List<Integer> sort = new LinkedList<>();
        int visited = 0;
        while (!queue.isEmpty()) {
            // 将元素出队，并将访问过的元素数量+1
            int u = queue.poll();
//            sort.add(u);
            visited++;
            // 遍历相邻节点所有边
            for (Integer v : edgesBfs.get(u)) {
                // 该节点的入度减1
                --indeg[v];
                // 若当前节点的入度为0，则将其放入队列中
                if (indeg[v] == 0) {
                    queue.offer(v);
                }
            }
        }
//        System.out.println(Arrays.toString(sort.toArray()));
        return visited == numCourses;
    }
}
