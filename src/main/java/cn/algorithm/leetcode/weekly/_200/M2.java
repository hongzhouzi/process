package cn.algorithm.leetcode.weekly._200;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 5476. 找出数组游戏的赢家
 * 给你一个由 不同 整数组成的整数数组 arr 和一个整数 k 。
 *
 * 每回合游戏都在数组的前两个元素（即 arr[0] 和 arr[1] ）之间进行。比较 arr[0] 与 arr[1] 的大小，较大的整数将会取得这一回合的胜利并保留在位置 0 ，较小的整数移至数组的末尾。当一个整数赢得 k 个连续回合时，游戏结束，该整数就是比赛的 赢家 。
 *
 * 返回赢得比赛的整数。
 *
 * 题目数据 保证 游戏存在赢家。
 *
 *
 *
 * 示例 1：
 *
 * 输入：arr = [2,1,3,5,4,6,7], k = 2
 * 输出：5
 * 解释：一起看一下本场游戏每回合的情况：
 *
 * 因此将进行 4 回合比赛，其中 5 是赢家，因为它连胜 2 回合。
 * 示例 2：
 *
 * 输入：arr = [3,2,1], k = 10
 * 输出：3
 * 解释：3 将会在前 10 个回合中连续获胜。
 * 示例 3：
 *
 * 输入：arr = [1,9,8,2,3,7,6,4,5], k = 7
 * 输出：9
 * 示例 4：
 *
 * 输入：arr = [1,11,22,33,44,55,66,77,88,99], k = 1000000000
 * 输出：99
 *
 *
 * 提示：
 *
 * 2 <= arr.length <= 10^5
 * 1 <= arr[i] <= 10^6
 * arr 所含的整数 各不相同 。
 * 1 <= k <= 10^9
 */
public class M2 {
    public static void main(String[] args) {
//        int arr[] = new int[]{2, 1, 3, 5, 4, 6, 7}, k = 2;
        int arr[] = new int[]{1,11,22,33,44,55,66,77,88,99}, k = 1000000000;
        System.out.println(getWinner(arr, k));
    }

    static public int getWinner(int[] arr, int k) {
        int len = arr.length;
        boolean flag = false;
        Queue<Integer> q = new LinkedList<>();
        int max = arr[0];
        for (int i = 1; i < len; i++) {
            max = Math.max(max, arr[i]);
            q.offer(arr[i]);
        }
        int first = arr[0];
        int num = 0, count = 0;
        while (!flag) {
            Integer pop = q.poll();
            if (first < pop) {
                int temp = first;
                first = pop;
                q.offer(temp);
                if (num == pop) {
                    count++;
                } else {
                    num = pop;
                    count = 1;
                }
                if (count >= k) {
                    flag = true;
                }
            } else {
                q.offer(pop);
                if (num == first) {
                    count++;
                } else {
                    num = first;
                    count = 1;
                }
                if (count >= k) {
                    flag = true;
                }
            }
            if (num == max) {
                flag = true;
            }
        }
        return num;
    }


    public int getWinner1(int[] arr, int k) {
        int n = arr.length;
        k = Math.min(k, n);
        int A = arr[0];
        int rep = 0;
        for(int i = 1;;i++){
            // 取模方式代替将
            int B = arr[i%n];
            if(A >= B){
                rep++;
            }else{
                A = B;
                rep = 1;
            }
            if(rep == k){
                return A;
            }
        }
    }
}
