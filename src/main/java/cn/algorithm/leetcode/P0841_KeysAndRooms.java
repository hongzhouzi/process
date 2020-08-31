package cn.algorithm.leetcode;

import java.util.*;

/**
 * 841. 钥匙和房间
 * 有 N 个房间，开始时你位于 0 号房间。每个房间有不同的号码：0，1，2，...，N-1，并且房间里可能有一些钥匙能使你进入下一个房间。
 * 在形式上，对于每个房间 i 都有一个钥匙列表 rooms{i}，每个钥匙 rooms{i}{j} 由 {0,1，...，N-1} 中的一个整数表示，
 * 其中 N = rooms.length。 钥匙 rooms{i}{j} = v 可以打开编号为 v 的房间。
 * 最初，除 0 号房间外的其余所有房间都被锁住。
 * 你可以自由地在房间之间来回走动。
 * 如果能进入每个房间返回 true，否则返回 false。
 * 示例 1：
 * 输入: {{1},{2},{3},{}}
 * 输出: true
 * 解释:
 * 我们从 0 号房间开始，拿到钥匙 1。
 * 之后我们去 1 号房间，拿到钥匙 2。
 * 然后我们去 2 号房间，拿到钥匙 3。
 * 最后我们去了 3 号房间。
 * 由于我们能够进入每个房间，我们返回 true。
 * 示例 2：
 * 输入：{{1,3},{3,0,1},{2},{0}}
 * 输出：false
 * 解释：我们不能进入 2 号房间。
 *
 * @author hongzhou.wei
 * @date 2020/8/31
 */
public class P0841_KeysAndRooms {
    public static void main(String[] args) {
//        int a[][] = {{2, 3}, {}, {2}, {1, 3, 1}};
//        int a[][] = {{1, 3}, {3, 0, 1}, {2}, {0}};
        int a[][] = {{1},{2},{3},{}};
        List<List<Integer>> rooms = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < a[i].length; j++) {
                list.add(a[i][j]);
            }
            rooms.add(list);
        }
        System.out.println(canVisitAllRooms(rooms));
        ;
    }

    /**
     * 【dfs】搜索每一条路径，只要有一条路径可以走完所有的房间就返回true，否则返回false
     * 出现问题，访问过的房间还可能存在拿到其他钥匙再访问其他其他房间
     *
     * @param rooms
     * @return
     */
    /*static public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int[] visitedRooms = new int[rooms.size()];
        visitedRooms[0] = 1;
        return dfs(rooms, 0, visitedRooms);
    }

    static boolean dfs(List<List<Integer>> rooms, int curRoom, int[] visitedRooms) {
        if ((Arrays.stream(visitedRooms).sum() == visitedRooms.length)) {
            return true;
        }
        boolean ret = false;
        List<Integer> nextRooms = rooms.get(curRoom);
        for (int i = 0; i < nextRooms.size(); i++) {
            int next = nextRooms.get(i);
            if (visitedRooms[next] == 1) {
                continue;
            }
            visitedRooms[next] = 1;
            ret = dfs(rooms, next, visitedRooms);
            visitedRooms[next] = 0;
        }
        return ret;
    }*/

    /**
     * 耗时
     * @param rooms
     * @return
     */
    /*static public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int[] visitedRooms = new int[rooms.size()];
        List<Set<Integer>> viskey = new LinkedList<>();
        for (int i = 0; i < rooms.size(); i++) {
            viskey.add(new HashSet<>());
        }
        visitedRooms[0] = 1;
        return dfs(rooms, viskey, 0, visitedRooms);
    }

    static boolean dfs(List<List<Integer>> rooms, List<Set<Integer>> viskey, int curRoom, int[] visitedRooms) {
        if ((Arrays.stream(visitedRooms).sum() == visitedRooms.length)) {
            return true;
        }
        boolean ret = false;
        List<Integer> nextRooms = rooms.get(curRoom);
        for (int i = 0; i < nextRooms.size(); i++) {
            int next = nextRooms.get(i);
            if (viskey.get(curRoom).contains(next)) {
                continue;
            } else {
                viskey.get(curRoom).add(next);
            }
            visitedRooms[next] = 1;
            ret = dfs(rooms, viskey, next, visitedRooms);
        }
        return ret;
    }*/


    /**
     * 优化，将访问过的钥匙标记为-1
     *
     * @param rooms
     * @return
     */
   /* static public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int[] visitedRooms = new int[rooms.size()];
        visitedRooms[0] = 1;
        return dfs(rooms, 0, visitedRooms);
    }

    static boolean dfs(List<List<Integer>> rooms, int curRoom, int[] visitedRooms) {
        // 每个房间都访问过就返回true
        int sum = 0, len = visitedRooms.length;
        for (int i = 0; i < len; i++) {
            sum += visitedRooms[i];
        }
        if(sum == len){
            return true;
        }
        boolean ret = false;
        List<Integer> nextRooms = rooms.get(curRoom);
        for (int i = 0; i < nextRooms.size(); i++) {
            int next = nextRooms.get(i);
            if (next == -1) {
                continue;
            }
            visitedRooms[next] = 1;
            nextRooms.set(i,-1);
            ret = dfs(rooms, next, visitedRooms);
        }
        return ret;
    }*/


    /**
     * 优化：将每次的房间是否已访问完的判断提到递归外面
     *
     * @param rooms
     * @return
     */
    static public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int[] visitedRooms = new int[rooms.size()];
        visitedRooms[0] = 1;
        // 每个房间都访问过就返回true
        return dfs(rooms, 0, visitedRooms) == rooms.size();
    }

    static int dfs(List<List<Integer>> rooms, int curRoom, int[] visitedRooms) {
        List<Integer> nextRooms = rooms.get(curRoom);
        int ret = 1;
        for (int i = 0; i < nextRooms.size(); i++) {
            int next = nextRooms.get(i);
            // 访问过的房间就不再访问了
            if (visitedRooms[next] == 1) {
                continue;
            }
            // 将访问的房间标记为已访问
            visitedRooms[next] = 1;
            ret += dfs(rooms, next, visitedRooms);
        }
        return ret;
    }

    /**
     * 【bfs】
     *
     * @param rooms
     * @return
     */
    public boolean canVisitAllRooms1(List<List<Integer>> rooms) {
        int n = rooms.size(), num = 0;
        boolean[] vis = new boolean[n];
        Queue<Integer> que = new LinkedList<Integer>();
        vis[0] = true;
        que.offer(0);
        while (!que.isEmpty()) {
            int x = que.poll();
            num++;
            for (int it : rooms.get(x)) {
                if (!vis[it]) {
                    vis[it] = true;
                    que.offer(it);
                }
            }
        }
        return num == n;
    }


}


