package cn.algorithm.leetcode;

import java.util.*;

/**
 * 347. 前 K 个高频元素
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 * 示例 1:
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2:
 * 输入: nums = [1], k = 1
 * 输出: [1]
 * 提示：
 * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
 * 你可以按任意顺序返回答案。
 *
 * @author hongzhou.wei
 * @date 2020/9/7
 */
public class P0347_TopKFrequentElements {

    public static void main(String[] args) {
        int[] nums = {5, 2, 5, 3, 5, 3, 1, 1, 3};
        int k = 2;
//        int[] nums = {1, 1, 1, 2, 2,  3};        int k = 2;

        System.out.println(Arrays.toString(new Solution().topKFrequent(nums, k)));
        System.out.println(Arrays.toString(new Solution1().topKFrequent(nums, k)));
    }


    /**
     * 在map中统计出现的数量，再根据出现量排序
     * 时间复杂度：O(n + n*logn )
     */
    static class Solution1 {
        public int[] topKFrequent(int[] nums, int k) {
            // map统计各个数出现的频率
            Map<Integer, Integer> countMap = new TreeMap<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                countMap.put(nums[i], countMap.getOrDefault(nums[i], 0) + 1);
            }
            // 根据map中频率排序
            List<Map.Entry<Integer, Integer>> list = new ArrayList<>(countMap.entrySet());
            list.sort((o1, o2) -> o2.getValue() - o1.getValue());
            // 存结果集中
            int[] ret = new int[k];
            for (int i = 0; i < k; i++) {
                ret[i] = list.get(i).getKey();
            }
            return ret;
        }
    }

    /**
     * 【优先队列】
     * 时间复杂度：O(n logn)
     * 空间复杂度：O(n) 最坏情况每个值都不同，都需要存储在map中
     */
    static class Solution {

        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> countMap = new TreeMap<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                countMap.put(nums[i], countMap.getOrDefault(nums[i], 0) + 1);
            }
            // 保存频率最高的 k 个元素  存key，比较过程使用value，满k个时value大的key替换value小的key
            // 优先队列中倒序排（最小堆），频次最低的在上面，当满 k 个时就让堆顶的key对应次数和当前key出现次数比较，小则移除
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(countMap::get));
            /*PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return countMap.get(a) - countMap.get(b);
                }
            });*/
            for (Integer num : countMap.keySet()) {
                if (priorityQueue.size() < k) {
                    priorityQueue.add(num);
                } else if (countMap.get(priorityQueue.peek()) < countMap.get(num)) {
                    priorityQueue.poll();
                    priorityQueue.add(num);
                }
            }

            int[] ret = new int[k];
            for (int j = 0; j < k; j++) {
                ret[j] = priorityQueue.poll();
            }
            return ret;
        }


        /*public int[] topKFrequent(int[] nums, int k) {
            PriorityQueue<Map<Integer, Integer>> priorityQueue = new PriorityQueue<>();
            Map<Integer, Integer> countMap = new HashMap<>();
            for (int i = nums.length - 1; i > 0; i--) {
                countMap.getOrDefault(nums[i], 0);
                Integer cur = countMap.get(nums[i]);
                if (Objects.isNull(cur)) {
                    int finalI = i;
                    priorityQueue.add(new HashMap<Integer, Integer>() {{
                        put(nums[finalI], 1);
                    }});
                } else {
//                    priorityQueue.
                }

            }
            int[] ret = new int[k];
            for (int i = 0; i < k; i++) {
                ret[i] = (int) (priorityQueue.poll().values().toArray())[0];
            }
            return ret;
        }*/
    }

    /**
     * 桶排序
     */
    static class Solution2 {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> countMap = new TreeMap<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                countMap.put(nums[i], countMap.getOrDefault(nums[i], 0) + 1);
            }

            // 桶排序，将频率作为数组下标，对于出现频率不同的数字集合，存入对应的数组下标
            List<Integer>[] list = new List[nums.length+1];
            for(int key : countMap.keySet()){
                // 获取出现的次数作为下标
                int i = countMap.get(key);
                if(list[i] == null){
                    list[i] = new ArrayList();
                }
                list[i].add(key);
            }

            List<Integer> res = new ArrayList();
            // 倒序遍历数组获取出现顺序从大到小的排列
            for(int i = list.length - 1;i >= 0 && res.size() < k;i--){
                if(list[i] == null) {
                    continue;
                }
                res.addAll(list[i]);
            }
            int[] ret = new int[res.size()];
            for (int i = 0; i < res.size(); i++) {
                ret[i] = res.get(i);
            }
            return ret;
        }
    }

}
