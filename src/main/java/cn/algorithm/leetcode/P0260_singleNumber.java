package cn.algorithm.leetcode;

import java.util.*;

/**
 * 给定一个整数数组 nums，其中恰好有两个元素只出现一次，
 * 其余所有元素均出现两次。 找出只出现一次的那两个元素。
 * 输入: [1,2,1,3,2,5]
 * 输出: [3,5] 或 [5,3]
 * <p>
 * 思路：
 * 1. 第1次遍历数组，用map保存数字的出现次数；
 * 第2次遍历数组，取次数为1的数字。
 * 时间复杂度：O(n)；空间复杂度：O(n)
 * 优：可适用于统计各自次数，通用性强
 * 缺：空间复杂较高
 * <p>
 * 2. 遍历数组，若当前数在set中就将其从set中移除，不存在就添加到set中
 * 最后剩下的2个数就是只出现过一次的数。
 * 时间复杂度：O(n)；空间复杂度：O(n)
 * 优：可适用于统计出现偶数次的数组中找奇数次的数
 * 缺：空间复杂较高
 * <p>
 * 3. 先对所有数进行异或运算，得到的值result等价于那两个只出现一次
 * 的数相互异或的值，接下来就想办法把这两个数分开。
 * 两个数不一样，那么它们的二进制数也一定不一样，两个二进制数不一样，
 * 那么肯定有某些位的值不一样，根据异或的性质知result中二进制位为1
 * 的位就是那两个只出现一次的数对应相异的二进制位，比如第n位的值一
 * 个数为0另一个数为1，那么异或结果中第n位肯定为1。
 * 然后我们尝试从第n位的数入手将整个数组的数分为两类，一类为第n位为0的数，
 * 另一类为第n位数为1的数，把整个数组内的数分成两类后再分别对这两类数
 * 进行异或运算就得到的两个结果就是那两个只出现一次的数。
 * 时间复杂度：O(n)；空间复杂度：O(1)
 * 优：空间复杂达到了常数阶
 * 缺：通用性比较低，太难想到
 * <p>
 * 3. 第1次遍历数组，将所有数字放在set中并把它们相加，把所有元素相加
 *
 * @author hongzhou.wei
 * @date 2020/5/22
 */
public class P0260_singleNumber {

    public static void main(String[] args) {
        int a[] = new int[]{1, 2, 1, 3, 2, 5};
        int[] ret = new P0260_singleNumber().singleNumber(a);
        int[] ret1 = new P0260_singleNumber().singleNumber1(a);
        int[] ret2 = new P0260_singleNumber().singleNumber2(a);
        System.out.println(Arrays.toString(ret));
//        System.out.println(Arrays.toString(ret1));
//        System.out.println(Arrays.toString(ret2));
    }

    public int[] singleNumber(int[] nums) {
        // 若nums=[1,2,1,3,2,5];
        int bitmask = 0;
        // 1.对所有数进行异或运算，得到的值等价于2个只出现一次的数相异或的值
        // 得到的结果是:3(0011) ^ 5(0101) = 6(0110)
        for (int i : nums) {
            bitmask ^= i;
        }
        // 2.保留2个只出现一次的二进制数的不同位中最右边的位，即0110中最右边的1
        // 得到的结果是: 6(0110) & -6(1010) = (0010)
        // rightmost 1-bit diff between x and y
//        int diff = bitmask & -bitmask;
        int diff = Integer.highestOneBit(bitmask);
        int x = 0;
        // bitmask which will contain only x
        for (int i : nums) {
            // 3.分类处理：计算该位不为0的那类异或的值，
            // 该位为1的那类最后直接与bitmask异或就OK了
            if ((i & diff) != 0) {
                x ^= i;
            }
        }
        return new int[]{x, bitmask ^ x};
    }

    public int[] singleNumber1(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            // 统计每个数出现的次数
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int[] ret = new int[2];
        int i = 0;
        for (int num : nums) {
            // 只取次数为1的
            if (map.get(num) == 1) {
                ret[i++] = num;
            }
        }
        return ret;
    }

    public int[] singleNumber2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                set.remove(num);
            } else {
                set.add(num);
            }
        }
        return set.stream()
                .mapToInt(Number::intValue)
                .toArray();
    }
}
