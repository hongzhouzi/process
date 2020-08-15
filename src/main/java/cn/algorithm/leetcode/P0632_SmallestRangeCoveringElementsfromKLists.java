package cn.algorithm.leetcode;

import java.util.*;

/**
 * 632. 最小区间
 * 你有 k 个升序排列的整数数组。找到一个最小区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
 * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
 * 示例 1:
 * 输入:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
 * 输出: [20,24]
 * 解释:
 * 列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
 * 列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
 * 列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
 * 注意:
 * 给定的列表可能包含重复元素，所以在这里升序表示 >= 。
 * 1 <= k <= 3500
 * -10^5 <= 元素的值 <= 10^5
 * 对于使用Java的用户，请注意传入类型已修改为List<List<Integer>>。重置代码模板后可以看到这项改动。
 *
 * @author hongzhou.wei
 * @date 2020/8/1
 */
public class P0632_SmallestRangeCoveringElementsfromKLists {
    public static void main(String[] args) {
        List<List<Integer>> data = new LinkedList<>();
        data.add(new LinkedList<>(Arrays.asList(4,10,15,24,26)));
        data.add(new LinkedList<>(Arrays.asList(0,9,12,20)));
        data.add(new LinkedList<>(Arrays.asList(5,18,22,30)));
        System.out.println(Arrays.toString(smallestRange(data)));
    }

    /**
     * 将该问题转换为从k个列表中各取一个数，使这些数的最大值和最小值的差最小。
     * 由于k个列表都是升序排列的，因此在每个列表中维护一个指针，通过指针得到列表中的元素，指针右移之后的元素一定大于等于之前的元素
     * 使用最小堆维护k个指针指向元素的最小值，同时维护堆中元素的最大值。初始时k指针都指向下标0，最大值为所有下标0元素中的最大值，
     * 每次从堆中取出最小值，根据最大和最小值计算当前区间，若当前区间小于最小区间（初始化为元素的边界范围）则更新最小区间，
     * 然后将对应列表的指针右移，并更新堆中元素、最小区间、堆中最大值。。。
     * 退出条件：某一个列表指针超出该列表索引，则说明该列表已遍历完，堆中不会再有该列表中的值，因此退出循环。
     *
     * @param nums
     * @return
     */
    static public int[] smallestRange(List<List<Integer>> nums) {
        int rangeLeft = 0, rangeRight = Integer.MAX_VALUE;
        int minRange = rangeRight - rangeLeft;
        int max = Integer.MIN_VALUE;
        int size = nums.size();
        // 存放索引的指针
        int next[] = new int[size];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> nums.get(o).get(next[o])));
        /*PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return nums.get(o1).get(next[o1]) - nums.get(o2).get(next[o2]);
            }
        });*/
        // 初始化最小堆中元素和堆中最大值
        for (int i = 0; i < size; i++) {
            priorityQueue.offer(i);
            max = Math.max(max, nums.get(i).get(0));
        }
        while (true) {
            int minIndex = priorityQueue.poll();
            int curRange = max - nums.get(minIndex).get(next[minIndex]);
            // 当前范围小于最小范围，更新最小范围
            if (curRange < minRange) {
                minRange = curRange;
                rangeLeft = nums.get(minIndex).get(next[minIndex]);
                rangeRight = max;
            }
            // 指针右移
            next[minIndex]++;
            if (next[minIndex] == nums.get(minIndex).size()) {
                break;
            }
            priorityQueue.offer(minIndex);
            max = Math.max(max, nums.get(minIndex).get(next[minIndex]));
        }
        return new int[]{rangeLeft, rangeRight};
    }
}
