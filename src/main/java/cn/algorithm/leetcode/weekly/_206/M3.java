package cn.algorithm.leetcode.weekly._206;

import java.util.*;

/**
 * @author hongzhou.wei
 * @date 2020/9/13
 */
public class M3 {
    public static void main(String[] args) {
        int[][] points = {{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}};
        System.out.println(new M3.Solution().minCostConnectPoints(points));
    }


    static class Solution {
        public int minCostConnectPoints(int[][] points) {
            // list[i] i下标表示该点起点，set表示已经和该起点连上的点
            List<Set<Integer>> lines = new ArrayList<Set<Integer>>((int) (points.length / 0.8));
            for (int i = 0; i < points.length; i++) {
                lines.add(new HashSet<>());
            }
            int tolMinCost = 0;
            for (int i = 0; i < points.length; i++) {
                Set<Integer> ps = lines.get(i);
                int minCost = Integer.MAX_VALUE, tempJ = 0;
                for (int j = 0; j < points.length; j++) {
                    if (i != j) {
                        int dis = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                        if(dis < minCost){
                            minCost = dis;
                            tempJ = j;
                        }
                    }
                }
                lines.get(tempJ).add(i);
                if (ps.contains(tempJ)) {
                    continue;
                }
                tolMinCost += minCost;
            }
            return tolMinCost;
        }
    }


    public class Solution0 {
        public int minCostConnectPoints(int[][] points) {
            int n = points.length;
            int inf = (int) 1e9;
            int[] dist = new int[n];
            Arrays.fill(dist, inf);
            dist[0] = 0;
            boolean[] added = new boolean[n];
            for (int i = 0; i < n; i++) {
                int head = -1;
                for (int j = 0; j < n; j++) {
                    if (added[j]) {
                        continue;
                    }
                    if (head == -1 || dist[j] < dist[head]) {
                        head = j;
                    }
                }
                added[head] = true;
                for (int j = 0; j < n; j++) {
                    if (added[j]) {
                        continue;
                    }
                    int cand = dist(points[j], points[head]);
                    if (dist[j] > cand) {
                        dist[j] = cand;
                    }
                }
            }

            return Arrays.stream(dist).sum();
        }

        public int dist(int[] a, int[] b) {
            return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
        }
    }

    class Solution1 {

        public int minCostConnectPoints(int[][] points) {
            HashSet<Integer> visited = new HashSet<>();
            PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);
            queue.add(new int[2]);
            int sum = 0;
            while (visited.size() < points.length) {
                int[] head = queue.remove();
                if (!visited.contains(head[0])) {
                    visited.add(head[0]);
                    sum += head[1];
                    for (int i = 0; i < points.length; i++) {
                        if (!visited.contains(i)) {
                            queue.add(new int[] { i, Math.abs(points[head[0]][0] - points[i][0])
                                    + Math.abs(points[head[0]][1] - points[i][1]) });
                        }
                    }
                }
            }
            return sum;
        }
    }

    /**
     * 每个点都选在其他点中选一个距离最近的相连，但如果已经相互连接过就不再次选对方了
     * 去重（相互已经连接的就不能再连接了，怎么存？怎么表示？）
     * 记录已有的连线
     */
    /*static class Solution {
        public int minCostConnectPoints(int[][] points) {
            // list[i] i下标表示该点起点，set表示已经和该起点连上的点
            List<Set<Integer>> lines = new ArrayList<Set<Integer>>((int) (points.length / 0.8));
            for (int i = 0; i < points.length; i++) {
                lines.add(new HashSet<>());
            }
            int tolMinCost = 0;
            for (int i = 0; i < points.length; i++) {
                Set<Integer> ps = lines.get(i);
                int minCost = Integer.MAX_VALUE, tempJ = 0;
                for (int j = 0; j < points.length; j++) {
                    if (!ps.contains(j) && i != j) {
                        int dis = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                        if(dis < minCost){
                            minCost = dis;
                            tempJ = j;
                        }
                    }
                }
                //
                lines.get(tempJ).add(i);
                tolMinCost += minCost;
            }
            return tolMinCost;
        }
    }*/

    /**
     * dp：
     * 状态：和某点连接后的最小值
     * <p>
     * 已经连上的用个集合保存，这种不能连了，可以作为出发点但不能作为到达点
     */
}
