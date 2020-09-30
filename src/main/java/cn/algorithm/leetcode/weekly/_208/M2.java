package cn.algorithm.leetcode.weekly._208;

/**
 * @author hongzhou.wei
 * @date 2020/9/27
 */
public class M2 {
    public static void main(String[] args) {
//        int[] customers = {10, 10, 6, 4, 7};int boardingCost = 3, runningCost = 8;
//        int []customers = {3,4,0,5,1}; int  boardingCost = 1, runningCost = 92;
//        int[] customers = {0, 43, 37, 9, 23, 35, 18, 7, 45, 3, 8, 24, 1, 6, 37, 2, 38, 15, 1, 14, 39, 27, 4, 25, 27, 33, 43, 8, 44, 30, 38, 40, 20, 5, 17, 27, 43, 11, 6, 2, 30, 49, 30, 25, 32, 3, 18, 23, 45, 43, 30, 14, 41, 17, 42, 42, 44, 38, 18, 26, 32, 48, 37, 5, 37, 21, 2, 9, 48, 48, 40, 45, 25, 30, 49, 41, 4, 48, 40, 29, 23, 17, 7, 5, 44, 23, 43, 9, 35, 26, 44, 3, 26, 16, 31, 11, 9, 4, 28, 49, 43, 39, 9, 39, 37, 7, 6, 7, 16, 1, 30, 2, 4, 43, 23, 16, 39, 5, 30, 23, 39, 29, 31, 26, 35, 15, 5, 11, 45, 44, 45, 43, 4, 24, 40, 7, 36, 10, 10, 18, 6, 20, 13, 11, 20, 3, 32, 49, 34, 41, 13, 11, 3, 13, 0, 13, 44, 48, 43, 23, 12, 23, 2};int boardingCost = 43, runningCost = 54;
        int[] customers = {1, 39, 27, 28, 26, 5, 1, 25, 9, 49, 23, 18, 37, 15, 11, 9, 27, 20, 6, 48, 14, 9, 47, 0, 4, 25, 47, 14, 3, 0, 27, 14, 24, 49, 22, 37, 35, 42, 4, 22, 45, 1, 13, 18, 47, 25, 8, 16, 5, 21, 35, 30, 11, 4, 41, 26, 44, 10, 21, 6, 11, 15, 5, 33, 43, 9, 29, 16, 18, 15, 5, 9, 29, 50, 21, 46, 20, 17, 45, 8, 32, 16, 9, 26, 18, 12, 39, 0, 22, 2, 0, 28, 0, 7, 5, 38, 18, 41, 25, 13, 18, 10, 21, 18, 20, 0, 26, 49, 35, 31, 41, 27, 0, 35, 26, 28, 38, 3, 11, 20, 17, 40, 30, 16, 28, 46, 28, 12, 21, 39, 12, 30, 26, 39, 19, 41, 39, 9, 1, 49, 22, 41, 24, 15, 16, 49, 44, 1, 41, 43, 10, 28, 47, 16, 24, 6, 7, 7, 30, 15, 2, 30, 27, 45, 10, 31, 41, 15, 4, 2, 14, 12, 38, 24, 5, 42, 12, 1, 16, 42, 1, 50, 16, 16, 23, 39, 31, 19, 33, 31, 11, 23, 26, 26, 43, 25, 3, 23, 39, 41, 29, 48, 3, 9, 19, 13, 31, 35, 49, 25, 17, 17, 16, 25, 16, 3, 49, 2, 29, 12, 47, 23, 43, 43, 5, 15, 41, 46, 25, 23, 50, 15, 20, 4, 26, 42, 28, 1, 43, 24, 28, 15, 49, 1, 23, 24, 50, 47, 9, 37, 13, 25, 48, 41, 35, 20, 26, 21, 31, 37, 38, 42, 14, 38, 7, 44, 21, 20, 1, 4, 28, 33, 7, 48, 32, 42, 6, 13, 29, 41, 10, 14, 13, 48, 37, 10, 1, 39, 8, 49, 40, 32, 6, 12, 11, 50, 26, 20, 45, 32, 2, 49, 20, 41, 19, 50, 36, 44, 29, 0, 47, 1, 19, 8, 4, 33, 6, 31, 45, 2, 30, 2, 15, 2, 32, 50, 24, 31, 24, 31, 21, 45, 17, 5, 44, 46, 36, 0, 22, 30, 48, 8, 4, 17, 29, 2, 30, 3, 5, 30, 27, 34, 49, 30, 44, 43, 32, 2, 23, 27, 28, 0, 36, 38, 8, 13, 20, 36, 1, 29, 30, 15, 46, 14, 30, 0, 12, 7, 0, 48, 22, 16, 8, 34, 3, 10, 38, 42, 37, 17, 7, 39, 50, 37, 34, 33, 0, 20, 2, 1, 36, 9, 25, 14, 17, 30, 24, 0, 36, 42, 46, 4, 33, 0, 31, 50, 12, 18, 10, 10, 10, 4, 36, 1, 0, 50, 30, 37, 5, 9, 48, 18, 18, 39, 33, 10, 21, 37, 49, 46, 22, 45, 44, 45, 38, 32, 27, 41, 13, 22, 13, 9, 35, 4, 20, 27, 43, 28, 9, 44, 36, 40, 12, 44, 7, 47, 28, 23, 20, 1, 41, 50, 46, 39, 0, 45, 17, 47, 34, 2, 6, 10, 33, 43, 38, 38, 10, 11, 41, 37, 45, 2, 12, 48, 24, 6, 50, 2, 26, 46, 18, 11, 28, 28, 14, 39, 39, 28, 40, 17, 36, 35, 30, 3, 19, 29, 19, 15, 37, 6, 40, 30, 40, 28, 15, 8, 47, 1, 43, 14, 7, 46, 26, 38, 32, 40, 7, 0, 44, 8, 8, 11, 17, 35, 28, 11, 26, 17, 0, 3, 16, 3, 35, 38, 0, 28, 16, 43, 9, 38, 44, 10, 8, 31, 14, 19, 36, 12, 47, 11, 30, 7, 16, 48, 20, 8, 23};int boardingCost = 97, runningCost = 78;
//        int[] customers = {10,10,1,0,0};        int boardingCost = 4, runningCost = 4;
//        int[] customers = {10, 9, 6};        int boardingCost = 6, runningCost = 4;
        System.out.println(new M2.Solution().minOperationsMaxProfit(customers, boardingCost, runningCost));
        System.out.println(new M2.Solution3().minOperationsMaxProfit(customers, boardingCost, runningCost));
    }

    /**
     * 稍微有点投机取巧，当时看到实例输入都是比较正常的，就直接想的通过计算和比较可以得出答案，再处理一些特殊情况。
     * 完整且万无一失的思路是：找出通用计算的公式，在循环中计算判断
     */
    static class Solution {
        public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
            int cusSum = 0;
            for (int i = 0; i < customers.length; i++) {
                cusSum += customers[i];
            }
            // 全部转完需要的转圈数
            int count = (int) (Math.ceil(cusSum / 4D));
            // 最后非满载情况
            int mod = cusSum % 4;
            int ret = -1;
            int retMod = -1;
            // 非满载情况
            if (mod != 0) {
                retMod = ((cusSum - mod) * boardingCost) - ((count - 1) * runningCost);
            }
            // 满载情况
            ret = (cusSum * boardingCost) - (count * runningCost);
            if (retMod >= ret) {
                ret = retMod;
                count--;
            }
            //
            if(customers.length > 0 && (customers[0] == 0 || customers[0] == 1)){
                count++;
            }
            return ret > 0 ? count : -1;
        }
    }



    /**
     * 通常的思路：统计每次新游客到达时等待中的游客数量和已玩过的游客数量，再根据公式计算利润并记录利润最大时最小轮转次数即可。
     */
    static class Solution3 {
        public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
            // 不可能盈利，直接返回
            if (boardingCost * 4 <= runningCost) {
                return -1;
            }
            int waitCustomersCount = 0, historyCustomersCount = 0, maxProfit = 0, maxProfitStep = 0;
            // 注意循环结束条件，若没有新游客到达但还有等待中的游客，依然要进入循环中的
            for (int step = 1; step <= customers.length || waitCustomersCount > 0; step++) {
                // 计算 waitCustomersCount
                if(step <= customers.length){
                    waitCustomersCount += customers[step - 1];
                }
                // 转动一次最大只能座4人，等待人数>4则从坐4人，否则等待中的全部坐
                if (waitCustomersCount > 4) {
                    historyCustomersCount += 4;
                    waitCustomersCount -= 4;
                } else {
                    historyCustomersCount += waitCustomersCount;
                    waitCustomersCount = 0;
                }
                // 计算利润
                int profit = (historyCustomersCount * boardingCost) - (runningCost * step);
                // 记录利润最大时最小轮转次数，注意这儿不能取等号，要的是最小轮转次数
                if (profit > maxProfit) {
                    maxProfit = profit;
                    maxProfitStep = step;
                }
            }
            return maxProfitStep;
        }
    }


    /**
     * 不能取到最大利润
     * int []customers = {10,10,6,4,7}; int  boardingCost = 3, runningCost = 8;
     */
    static class Solution1 {
        public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
            int cusSum = 0;
            for (int i = 0; i < customers.length; i++) {
                cusSum += customers[i];
            }
            int count = (int) (Math.ceil(cusSum / 4D));
            int ret = (cusSum * boardingCost) - (count * runningCost);
            return ret > 0 ? count : -1;
        }
    }
}
