package cn.algorithm.leetcode;

/**
 * 给你一个未排序的整数数组，请你找出其中没有出现的最小的正整数。
 * 示例 1:
 * 输入: [1,2,0]；输出: 3
 * 示例 2:
 * 输入: [3,4,-1,1]；输出: 2
 * 示例 3:
 * 输入: [7,8,9,11,12]；输出: 1
 * 提示：
 * 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的额外空间。
 *
 * @author hongzhou.wei
 * @date 2020/6/27
 */
public class P0041_firstMissingPositive {
    public static void main(String[] args) {
        int a[] = new int[]{3, 4, -1, 1};
        System.out.println(firstMissingPositive(a));
    }

    /**
     * 思路：在数组nums中数组长度为N，缺失的最小正整数一定在[1,N+1]中
     * 遍历数组过程中将下标为0的放置1，下标为1的放置2以此类推，一次遍历交换他们的位置,
     * 若没有对应位置的就跳过
     * 放置好后，第二次检查是否在正确的位置，对应位置上没有对应的值就说明那儿是缺失的值
     *
     * @param nums
     * @return
     */
    static public int firstMissingPositive(int[] nums) {
        for (int i = 0, len = nums.length; i < len; i++) {
            // 这儿只用if判断不行，需要在交换后另一个值没有在指定位置时继续交换的写法
            /*if (nums[i] != i + 1 && (nums[i] <= len  && nums[i]>0)) {
                // 交互i位置和num[i]-1位置的数
                int temp = nums[i] -1;
                nums[i] = nums[temp];
                nums[temp] = temp;
//                swap(nums[i],nums[nums[i]]);
            }*/
            // 下面这个while循环和nums[nums[i] - 1] != nums[i]是精髓
            // 可以让交互后的另一个值没在指定位置的情况继续交换
            while (nums[i] > 0 && nums[i] <= len && nums[nums[i] - 1] != nums[i]) {
                // 交换i位置和num[i]-1位置的数
                // 这样写会导致num[i]的值改变之后下标越界，nums[i]换了后num[i]-1也就变了
//                int temp = nums[i];
//                nums[i] = nums[nums[i] - 1];
//                nums[nums[i] - 1] = temp;
                // 正确写法：交互值时涉及到下标也需要取值的，需要把交互的下标暂存起来
                int index2 = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[index2];
                nums[index2] = temp;
                // 或者写个交换函数，传参
//                swap(nums,i,nums[i]-1);
            }
        }
        for (int i = 0, len = nums.length; i < len; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return nums.length + 1;
    }

    /**
     * LeetCode参考答案
     *
     * @param nums
     * @return
     */
    static public int firstMissingPositive1(int[] nums) {
        int len = nums.length;

        for (int i = 0; i < len; i++) {
            while (nums[i] > 0 && nums[i] <= len && nums[nums[i] - 1] != nums[i]) {
                // 满足在指定范围内、并且没有放在正确的位置上，才交换
                // 例如：数值 3 应该放在索引 2 的位置上
                swap(nums, nums[i] - 1, i);
            }
        }
        // [1, -1, 3, 4]
        for (int i = 0; i < len; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        // 都正确则返回数组长度 + 1
        return len + 1;
    }

    static private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

}
