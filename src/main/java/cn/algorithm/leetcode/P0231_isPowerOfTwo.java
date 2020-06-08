package cn.algorithm.leetcode;

/**
 * 给定一个整数，编写一个函数来判断它是否是 2 的幂次方。
 *
 * @author hongzhou.wei
 * @date 2020/5/21
 */
public class P0231_isPowerOfTwo {
    public static void main(String[] args) {

    }

    public boolean isPowerOfTwo1(int n) {
        return (n > 0) && (n & n - 1) == 0;
    }

    // 负数的原码=补码=正数按位取反+1
    public boolean isPowerOfTwo2(int n) {
        return (n > 0) && (n & -n) == 0;
    }

    // 1<<31得到2的最大正数次幂，模上n能整除则说明n是2的幂
    public boolean isPowerOfTwo3(int n) {
        return (n > 0) && (1<<31) % n == 0;
    }
}
