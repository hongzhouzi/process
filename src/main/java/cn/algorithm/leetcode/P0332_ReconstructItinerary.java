package cn.algorithm.leetcode;

import java.util.*;

/**
 * 332. 重新安排行程
 * 给定一个机票的字符串二维数组 {from, to}，子数组中的两个成员分别表示飞机出发和降落的机场地点，对该行程进行重新规划排序。
 * 所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，所以该行程必须从 JFK 开始。
 * 说明:
 * 如果存在多种有效的行程，你可以按字符自然排序返回最小的行程组合。例如，行程 {"JFK", "LGA"} 与 {"JFK", "LGB"} 相比就更小，排序更靠前
 * 所有的机场都用三个大写字母表示（机场代码）。
 * 假定所有机票至少存在一种合理的行程。
 * 示例 1:
 * 输入: {{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"}, {"LHR", "SFO"}}
 * 输出: {"JFK", "MUC", "LHR", "SFO", "SJC"}
 * 示例 2:
 * 输入: {{"JFK","SFO"},{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}}
 * 输出: {"JFK","ATL","JFK","SFO","ATL","SFO"}
 * 解释: 另一种有效的行程是 {"JFK","SFO","ATL","JFK","ATL","SFO"}。但是它自然排序更大更靠后。
 *
 * @author hongzhou.wei
 * @date 2020/8/27
 */
public class P0332_ReconstructItinerary {

    public static void main(String[] args) {
        String[][] a = {{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"}, {"LHR", "SFO"}};
        List<List<String>> tickets = new LinkedList<>();
        for (int i = 0; i < a.length; i++) {
            tickets.add(new ArrayList<>(Arrays.asList(a[i])));
        }
        System.out.println(new P0332_ReconstructItinerary().findItinerary(tickets));
    }

    /**
     * 【dfs】
     *
     * @param tickets
     * @return
     */
    LinkedList<String>                 ret        = new LinkedList<>();
    Map<String, PriorityQueue<String>> ticketsMap = new HashMap<>();

    public List<String> findItinerary(List<List<String>> tickets) {
        for (List<String> ticket : tickets) {
            String src = ticket.get(0);
            String tar = ticket.get(1);
            if (!ticketsMap.containsKey(src)) {
                ticketsMap.put(src, new PriorityQueue<>());
            }
            ticketsMap.get(src).add(tar);
            /*if (ticketsMap.containsKey(src)) {
                ticketsMap.get(src).add(tar);
            } else {
                // 使用优先队列，添加进去时会排序
                ticketsMap.put(src, new PriorityQueue<String>() {{
                    add(tar);
                }});
            }*/
        }
        dfs("JFK");
        return ret;
    }

    void dfs(String src) {
        PriorityQueue<String> list = ticketsMap.get(src);
        while (list != null && list.size() > 0) {
            dfs(list.poll());
        }
        // 头插法添加，递归时从下往上添加先添加的要在最后面
        ret.addFirst(src);
    }
}
