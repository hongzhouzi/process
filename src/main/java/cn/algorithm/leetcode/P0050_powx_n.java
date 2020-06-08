package cn.algorithm.leetcode;

/**
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
 *
 * @author hongzhou.wei
 * @date 2020/5/11
 */
public class P0050_powx_n {

    public static void main(String[] args) {
        System.out.println(myPow(2.0, 10));
        System.out.println(myPow(2.0, -2));
    }


    static double myPow(double x, int n) {
        // 处理负数次幂
        return n >= 0 ? quickMul(x, n) : 1.0 / quickMul(x, -n);
    }

    /*
    快速幂+递归
    时间复杂度：O(log n)，即为递归的层数。
    空间复杂度：O(log n)，即为递归的层数。递归的函数调用会使用栈空间。
     */
    static double quickMul(double x, long n) {
        // 递归出口（n=0，任意数的 0 次方均为 1）
        if (n == 0) {
            return 1.0;
        }
        // 递归处理
        double y = quickMul(x, n / 2);
        //  偶数和奇数不同的处理方式
        return (n % 2) == 0 ? y * y : y * y * x;
    }

    /*
    快速幂 + 迭代 （二进制思想）
    时间复杂度：O(logn)，即为对 n 进行二进制拆分的时间复杂度。
    空间复杂度：O(1)
     */
    static double quickMul2(double x, int n) {
        double ret = 1.0;
        // 贡献值的初始值为想
        double xContribute = x;
        // 在对n进行二进制拆分时计算答案
        while (n > 0) {
            // 若二进制最低位为0，则需要计入贡献
            if (n % 2 == 0) {
                ret *= xContribute;
            }
            // 平方贡献
            xContribute *= xContribute;
            // 舍弃二进制最低位
            n /= 2;
        }
        return ret;
    }
}
