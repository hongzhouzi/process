package cn.algorithm.base.bit;

import java.util.HashMap;

/**
 * 判断是否是2的次方
 *
 * @author: whz
 * @date: 2019/8/9
 **/
public class Is2Power {
    public static void main(String[] args) {
        System.out.println(isPower1(1024));
        System.out.println(isPower1(1025));
        System.out.println(isPower2(1024));
        System.out.println(isPower2(1026));

        System.out.println("8中1个数：" + countOne(8));
        System.out.println("16中1个数:" + countOne(16));

        System.out.println("8中1个数：" + countOne2(8));
        System.out.println("15中1个数:" + countOne2(15));

        HashMap<String, String> a = new HashMap<>(10);
        System.out.println(a.size());


        Thread t = new Thread() {
            @Override
            public void run() {
                f();
            }
        };
        t.start();
        System.out.println("zhao");
    }

    static void f() {
        System.out.println("xik");
    }

    /**
     * 用1做移位操作，若移位后的数与给的数相等，就说明是2的某次方
     *
     * @param n
     * @return
     */
    static boolean isPower1(long n) {
        if (n < 1)
            return false;
        long i = 1;
        while (i <= n) {
            if (i == n)
                return true;
            i <<= 1; //左移
        }
        return false;
    }

    /**
     * n = 0100  那么 (n-1) = 0011
     * n与n-1做与运算，若是2的某次方，那么运算结果为0
     *
     * @param n
     * @return
     */
    static boolean isPower2(int n) {
        if (n < 1)
            return false;
        int m = n & (n - 1);
        return m == 0;
    }

    static int countOne(int n) {
        int count = 0;
        while (n > 0) {
            if ((n & 1) == 1) //判断最后一位是否为1
                count++;
            n >>= 1;     //移位操作
        }
        return count;
    }

    /**
     * 每次进行一次 n & (n-1)结果都会少一位，而且是最后一位
     *
     * @param n
     * @return
     */
    static int countOne2(int n) {
        int count = 0;
        while (n > 0) {
            if (n != 0) {//判断最后一位是否为1
                n = n & (n - 1);//对n或运算（类似于自减）
                count++;
            }
        }
        return count;
    }

}
