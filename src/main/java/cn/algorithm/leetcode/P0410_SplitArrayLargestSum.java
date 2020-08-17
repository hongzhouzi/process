package cn.algorithm.leetcode;

import cn.examination.pass58.Solution;

import java.util.Arrays;

/**
 * 410. 分割数组的最大值
 * 给定一个非负整数数组和一个整数 m，你需要将这个数组分成 m 个非空的连续子数组。
 * 设计一个算法使得这 m 个子数组各自和的最大值最小。
 * 注意:
 * 数组长度 n 满足以下条件:
 * 1 ≤ n ≤ 1000
 * 1 ≤ m ≤ min(50, n)
 * 示例:
 * 输入:
 * nums = [7,2,5,10,8]
 * m = 2
 * 输出:
 * 18
 * 解释:
 * 一共有四种方法将nums分割为2个子数组。
 * 其中最好的方式是将其分为[7,2,5] 和 [10,8]，
 * 因为此时这两个子数组各自的和的最大值为18，在所有情况中最小。
 *
 * @author hongzhou.wei
 * @date 2020/7/25
 */
public class P0410_SplitArrayLargestSum {
    public static void main(String[] args) {
        /*int []nums = new int[]{7,2,5,10,8};
        int m = 2;*/
        int []nums = new int[]{1,2147483646};
        int m = 1;
        System.out.println(splitArray1(nums,m));
        System.out.println(splitArray2(nums,m));
    }

    /**
     *
     * 尽量让拆分后各个子数列之和保持相差不大，因为整个数组之和是一定的，希望拆分后
     * 的子数组最大的和最小，就只能让各个子数列之和保持相差不大。
     * 求出数组的总和/拆分次数，得到平均拆分数，挑选子数组时，大小参考平均拆分数
     * 达到平均拆分数附近就作为拆分点，拆分后记录好拆分点，作为初步方案
     * 然后验证初步方案是否为最优效果，不为最小则调整拆分点。
     * 写着写着感觉不太对
     *
     * @param nums
     * @param m
     * @return
     */
    public int splitArray(int[] nums, int m) {
        // 记录拆分点、对应拆分点子数组和
        int record[][] = new int[m  ][2];
        // 参考拆分和
        int pivotSum = Arrays.stream(nums).sum() / m;
        int tempSum = 0;
        // 初次拆分
        for (int i = 0, im = 0; i < nums.length && im < m; i++) {
            // 若加上当前数，越靠近参考的拆分和则加上当前数
            if (Math.abs(pivotSum - tempSum) < Math.abs(pivotSum - tempSum - nums[i])) {
                tempSum += nums[i];
            }
            // 否则将当前数作为拆分点
            else {
                record[im][0] = i;
                record[im++][1] = tempSum;
                tempSum = nums[i];
            }
        }

        return 0;
    }


    /**
     * 二分搜索
     * 返回的值在数组中最大的值和数组和之间，我们可以对这个区间的值x进行验证
     * 若这个是符合条件，那么它必须存在一种分割方案，满足其最大分割子数组和不超过x
     * 策略如下：
     * 贪心地模拟分割的过程，从前到后遍历数组，用 sum 表示当前分割子数组的和，
     * cnt 表示已经分割出的子数组的数量（包括当前子数组），那么每当 sum 加上当前值
     * 超过了x，我们就把当前取的值作为新的一段分割子数组的开头，并将cnt加1。
     * 遍历结束后验证是否cnt 不超过 m。
     *
     *
     * @param nums
     * @param m
     * @return
     */
    static public int splitArray1(int[] nums, int m) {
        // 找二分搜索的左右边界，左为数组中最大值，右为数组和
        int left = 0, right = 0;
        for (int i = 0; i < nums.length; i++) {
            right += nums[i];
            if (left < nums[i]) {
                left = nums[i];
            }
        }
        // 二分搜索
        while (left < right) {
//            int mid = (right + left) / 2;
            // 使用这种写法，上面那种写法可能会溢出而导致出错
            int mid = (right - left) / 2 + left;
            // 验证该值是否存在分割方案，存在则尝试缩小右边界范围
            if (check(nums, mid, m)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 验证是否存在分割方案，使分割子数组和不超过x
     *
     * @param nums
     * @param x
     * @param m
     * @return
     */
    static public boolean check(int[] nums, int x, int m) {
        int sum = 0;
        int cnt = 1;
        for (int i = 0; i < nums.length; i++) {
            if (sum + nums[i] > x) {
                cnt++;
                sum = nums[i];
            } else {
                sum += nums[i];
            }
        }
        return cnt <= m;
    }

    static public int splitArray2(int[] nums, int m) {
        // 初始化二分搜索边界
        int left = 0, right = 0, len = nums.length;
        for (int i = 0; i < len; i++) {
            if(left < nums[i]){
                left = nums[i];
            }
            right += nums[i];
        }
        // 开始二分搜索+验证
        /*while (right > left){
            int mid = (right - left) / 2 + left;
            // 最小化最大值：验证符合要求后，验证有没有更小的符合要求的值，则缩小大值范围，right = mid -1
            if (check2(nums, mid, m)) {
                // 不能写成 mid - 1 防止当前大值是范围内的最小值，mid - 1 得到的值就小了一个数
                right = mid ;
            } else {
                // 说明mid是验证了不能通过的，则范围缩小到mid+1
                left = mid + 1;
            }
        }

        return left;*/
        // 两个数相邻时就结束条件
        while (right - left > 1){
            int mid = (right - left) / 2 + left;
            // 最小化最大值：验证符合要求后，验证有没有更小的符合要求的值，则缩小大值范围，right = mid -1
            if (check2(nums, mid, m)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        //right是验证通过了的，而left是验证未通过，两个碰面的就说明范围缩小到left和right，而right验证通过，left验证未通过，故返回right
        return right;
    }

    static boolean check2(int[] nums,int mid, int m){
        // count需要初始化为1而不是0，因为统计的是分割后的个数，分割1次会分割成2份，分割2次回分割成3份
        int count = 1, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if(sum + nums[i] > mid){
                sum = nums[i];
                count++;
            }else {
                sum += nums[i];
            }
        }
        return count <= m;
    }
}
