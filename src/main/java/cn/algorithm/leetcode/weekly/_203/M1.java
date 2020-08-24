package cn.algorithm.leetcode.weekly._203;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class M1 {
    public static void main(String[] args) {
        int n = 3;
        int[] rounds = {3, 2, 1, 2, 1, 3, 2, 1, 2, 1, 3, 2, 3, 1};

        System.out.println(mostVisited0(n, rounds));
    }

    /**
     * 对问题进行简化的思维需要锻炼，
     * 这个问题只需要关注起点和终点的情况，从起点遍历到终点，再对结果升序排序即可。
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * 反思：
     * 1.开始看到这个问题想得复杂度了，本来只需要关心起点和终点就够了，
     * 结果把中间部分数据也考虑了进去，就把问题复杂度化了。
     * 2.在环形中取某段数据时，用取模的方式不熟练。（[1,length]范围）
     * 取跨段数据时可以用[1, 终点]+[起点, 环周长]的方式取，不一定取模。
     *
     * @param n
     * @param rounds
     * @return
     */
    static public List<Integer> mostVisited(int n, int[] rounds) {
        int head = rounds[0], tail = rounds[rounds.length - 1];
        List<Integer> ret = new LinkedList<>();
        if(head<=tail){
            while (head <= tail) {
                ret.add(head++);
            }
        }else {
            // [1, 终点]+[起点, n]
            while (1 <= tail) {
                ret.add(tail--);
            }
            while (head <= n) {
                ret.add(head++);
            }
        }
        ret.sort(Integer::compareTo);
        return ret;
    }

    /**
     * 解决环状不用取模的方式：开始节点数==n+1时让头结点数置为1
     *
     * @param n
     * @param rounds
     * @return
     */
    static public List<Integer> mostVisited0(int n, int[] rounds) {
        int head = rounds[0], tail = rounds[rounds.length - 1];
        List<Integer> ret = new LinkedList<>();
        while (true){
            ret.add(head);
            if(head == tail){
                break;
            }
            head++;
            if(head == n+1){
                head = 1;
            }
        }
        ret.sort(Integer::compareTo);
        return ret;
    }


        static public List<Integer> mostVisited2(int n, int[] rounds) {
        int head = rounds[0];
        int tail = rounds[rounds.length - 1];
        List<Integer> ret = new LinkedList<>();
        if (head == tail) {
            ret.add(head);
        } else if (tail < head) {
            while (true) {
                if (head % (n + 1) == tail) {
                    break;
                }
                if (head % (n + 1) == 0) {
                    head++;
                    ret.add(head % (n + 1));
                    continue;
                }
                ret.add(head % (n + 1));
                head++;
            }
        } else {
            Arrays.sort(rounds);
            int orderTail = rounds[rounds.length - 1];
            if (orderTail > tail) {
                while (head <= tail) {
                    ret.add(head++);
                }
            } else if (orderTail < tail) {
                while (true) {
                    if (orderTail % n == tail) {
                        break;
                    }
                    if (orderTail % n == 0) {
                        orderTail++;
                    }
                    ret.add(orderTail % n);
                }

            } else {
                while (head <= orderTail) {
                    ret.add(head++);
                }
            }
        }
        ret.sort(Integer::compareTo);
        return ret;
    }

    static public List<Integer> mostVisited1(int n, int[] rounds) {
        if (n <= 0) {
            return new LinkedList<>();
        }

        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < rounds.length - 1; i++) {
            if (i == 0) {
                for (int x = rounds[i]; x <= rounds[i + 1]; x++) {
                    list.add(x);
                }
            } else {
                int x = rounds[i] + 1;
                while (true) {

                    if (x % (n + 1) == 0) {
                        list.add(x % (n + 1) + 1);

                        continue;
                    }
                    list.add(x % (n + 1));
                    x = (x % (n + 1)) + 1;
                    if (x == rounds[i + 1]) {
                        break;
                    }

                }
            }
        }
        Map<Integer, Long> collect = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<Integer> ret = new LinkedList<>();
        int max = collect.get(1).intValue();
        for (int i = 1; i <= n; i++) {
            if (collect.get(i) == max) {
                ret.add(i);
            }
        }
        return ret;

    }
}
