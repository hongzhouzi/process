package cn.algorithm.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.lang.Number.*;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现了三次。
 * 找出那个只出现了一次的元素。
 * 说明：你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * <p>
 * 示例 1:
 * 输入: [2,2,3,2]；输出: 3
 * 示例 2:
 * 输入: [0,1,0,1,0,1,99]；输出: 99
 * <p>
 * 思路：
 * 1.借助map
 * 第1次遍历数组，用map保存数字的出现次数；
 * 第2次遍历数组，取次数为1的数字。
 * 时间复杂度：O(n)；空间复杂度：O(n)
 * 优：可适用于统计各自次数，通用性强
 * 缺：空间复杂较高
 * <p>
 * 2.借助set
 * 第1次遍历数组，将数组添加到set中，并计算和后*3减去
 * 对数组所有数求和的值除以2即可。（缺点：如果数太大会溢出）
 *
 * @author hongzhou.wei
 * @date 2020/6/9
 */
public class P0137_singleNumberII {

    public static void main(String[] args) {
        int a[] = new int[]{0, 1, 0, 1, 0, 1, 99};
        int b[] = {43, 16, 45, 89, 45, -2147483648, 45, 2147483646, -2147483647, -2147483648, 43, 2147483647, -2147483646, -2147483648, 89, -2147483646, 89, -2147483646, -2147483647, 2147483646, -2147483647, 16, 16, 2147483646, 43};
        P0137_singleNumberII test = new P0137_singleNumberII();
        System.out.println(test.singleNumber2(b));
        System.out.println(test.singleNumber4(b));
    }

    public int singleNumber1(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            // 统计每个数出现的次数
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int i = 0;
        for (int num : nums) {
            // 只取次数为1的
            if (map.get(num) == 1) {
                return num;
            }
        }
        return -1;
    }

    public int singleNumber2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        // 这儿用long，防止int相加后数据溢出
        long setSum = 0, sum = 0;
        for (int num : nums) {
            set.add(num);
            sum +=  num;
        }
        for (Integer i : set) {
            setSum += i;
        }
        return (int) ((3*setSum- sum) / 2);
    }

    /*
    设计电路的大佬设计的，
    x第一次出现后，a = (a ^ x) & ~b的结果为 a = x, b = (b ^ x) & ~a的结果为此时因为a = x了，所以b = 0。
    x第二次出现：a = (a ^ x) & ~b, a = (x ^ x) & ~0, a = 0; b = (b ^ x) & ~a 化简， b = (0 ^ x) & ~0 ,b = x;
x第三次出现：a = (a ^ x) & ~b， a = (0 ^ x) & ~x ,a = 0; b = (b ^ x) & ~a 化简， b = (x ^ x) & ~0 , b = 0;所以出现三次同一个数，a和b最终都变回了0.
     */
    public long singleNumber3(int[] nums) {
        int a = 0, b = 0;
        for (Integer x : nums) {
            b = (b ^ x) & ~a;
            a = (a ^ x) & ~b;
        }
        return b;
    }

    /*
    将每个数想象成32位的二进制，对于每一位的二进制的1和0累加起来必然是3N或者3N+1，
    为3N代表目标值在这一位没贡献，3N+1代表目标值在这一位有贡献(=1)，然后将所有有贡献
    的位|起来就是结果。这样做的好处是如果题目改成K个一样，只需要把代码改成cnt%k，很通用
     */
    public int singleNumber4(int[] nums) {
        int ret = 0;
        // 对32位二进制数依次计算数组中的数，对于应二进制位的1或0累加起来
        for (int i = 0; i < 32; i++) {
            int mask = 1 << i;
            int cnt = 0;
            for (int j = 0; j < nums.length; j++) {
                if ((nums[j] & mask) != 0) {
                    cnt++;
                }
            }
            // 若那个只出现过一次的数对应的二进制位为1，就做将此位的1记录下来
            if (cnt % 3 != 0) {
                ret |= mask;
            }
        }
        return ret;
    }

}
