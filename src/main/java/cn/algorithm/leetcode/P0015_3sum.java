package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，
 * 使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * 示例：
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 *
 * @author hongzhou.wei
 * @date 2020/6/12
 */
public class P0015_3sum {
    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        List<List<Integer>> ret = threeSum3(nums);
        for (List<Integer> list : ret) {
            System.out.println(list.toString());
        }
    }

    // 第一次写的
    static public List<List<Integer>> threeSum(int[] nums) {
        // 升序排列
        Arrays.sort(nums);
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || nums[i] > nums[i - 1]) {
                int left = i + 1, right = nums.length - 1;
                while (left < right) {
                    int sum = nums[left] + nums[right] + nums[i];
                    if (sum > 0) {
                        // 需要加小的数，移动左边指针
                        left++;
                    } else if (sum < 0) {
                        // 需要加大的数，移动右边指针
                        right--;
                    } else {
                        // sum=0时，需要找数组中是否有0
                        // 数组中是否有0，可以在遍历数组过程中顺便检查
                        // 这个就先暂存着，如果检查到有0就把这个加上，无0就丢弃
                        List<Integer> list = new ArrayList<>(3);
                        list.add(nums[i]);
                        list.add(nums[left]);
                        list.add(nums[right]);
                        ret.add(list);
                        // 探索下个符合条件的
                        left++;
                        right--;
                        while (left < right && nums[left] == nums[left - 1]) {
                            left++;
                        }
                        while (right > left && nums[right] == nums[right + 1]) {
                            right--;
                        }
                    }
                }
            }
        }
        return ret;
    }

    // 参考
    static public List<List<Integer>> threeSum1(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ls = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过可能重复的答案
            if (i == 0 || nums[i] != nums[i - 1]) {
                int l = i + 1, r = nums.length - 1, sum = 0 - nums[i];
                while (l < r) {
                    if (nums[l] + nums[r] == sum) {
                        ls.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        // 进一步搜索是否有符合要求的值
                        // 跳过重复值
                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        // 跳过重复值
                        while (l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                        l++;
                        r--;
                    } else if (nums[l] + nums[r] < sum) {
                        // 跳过重复值
                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        l++;
                    } else {
                        // 跳过重复值
                        while (l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                        r--;
                    }
                }
            }
        }
        return ls;
    }

    /**
     * 双指针法：
     * 1.先对数组排序（从小到大）
     * 2.遍历排序后的数组，准备两个指针一个从左边i+1开始，一个从右边开始
     * 3.计算双指针指向位置的值+遍历的当前的值，判断总和与0的大小
     * 比0大就需要减小总和，需要移动右指针；比0小就需要增大总和，需要移动左边指针
     * 等于0时，就记录当前这种组合方式，再移动左右指针继续向中间搜索是否有符合的值
     * 4.另外需要考虑跳过重复的值，移动双指针时当前值=下个值就需要跳过，循环遍历时
     * 遍历当前值如果和上个值一样，就需要跳到下个值，这儿不能用当前值和下个值比
     *
     * @param nums
     * @return
     */
    static public List<List<Integer>> threeSum3(int[] nums) {
        List<List<Integer>> ret = new LinkedList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            // 跳过可能重复的值
            // 不能用下面这种方式，要之前出现过的才跳过，不然会少些组合值
//            if (i != nums.length - 1 && nums[i] == nums[i + 1]) {
//                continue;
//            }
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum > 0) {
                    // 右指针移动时，跳过重复值
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    r--;
                } else if (sum < 0) {
                    // 左指针移动时，跳过重复值
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    l++;
                } else {
                    ret.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    // 继续搜索是否有符合要求的值
                    // 跳过重复值
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    // 继续搜索下个值
                    l++;
                    r--;
                }
            }
        }
        return ret;
    }
}
