package cn.algorithm.leetcode;

/**
 * 43. 字符串相乘
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 * 示例 1:
 * 输入: num1 = "2", num2 = "3"
 * 输出: "6"
 * 示例 2:
 * 输入: num1 = "123", num2 = "456"
 * 输出: "56088"
 * 说明：
 * num1 和 num2 的长度小于110。
 * num1 和 num2 只包含数字 0-9。
 * num1 和 num2 均不以零开头，除非是数字 0 本身。
 * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
 *
 * @author hongzhou.wei
 * @date 2020/8/13
 */
public class P0043_MultiplyStrings {

    public static void main(String[] args) {
        String num1 = "1234", num2 = "654321";
        System.out.println(1234 * 654321);
        System.out.println(multiply(num1, num2));
        System.out.println(multiply1(num1, num2));

        System.out.println(addStrings("12", "31", 3));
        System.out.println(addStrings("12", "31", 0));

    }

    /**
     * 大数相乘-先逐位相乘再相加思路，相加时用大数相加偏移量
     *
     * @param num1
     * @param num2
     * @return
     */
    static public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }
        int len1 = num1.length(), len2 = num2.length();
        StringBuilder sum = new StringBuilder();
        for (int i = len1 - 1; i >= 0; i--) {
            StringBuilder sb = new StringBuilder("0");
            for (int j = len2 - 1; j >= 0; j--) {
                sb = addStrings(sb + "", ((num2.charAt(j) - '0') * (num1.charAt(i) - '0')) + "", len2 - j - 1);
            }
            sum = addStrings(sum + "", sb + "", len1 - i - 1);
        }
        return sum.toString();
    }


    /**
     * 数值数组存储
     *
     * @param num1
     * @param num2
     * @return
     */
    static public String multiply1(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }
        int len1 = num1.length(), len2 = num2.length();
        int[] ans = new int[len1 + len2];
        // 先逐位相乘，将值放在指定位置，同位置上的数累加
        for (int i = len1 - 1; i >= 0; i--) {
            int x = num1.charAt(i) - '0';
            for (int j = len2 - 1; j >= 0; j--) {
                int y = num2.charAt(j) - '0';
                ans[i + j + 1] += x * y;
            }
        }
        // 处理进位
        for (int i = len1 + len2 - 1; i > 0; i--) {
            ans[i - 1] += ans[i] / 10;
            ans[i] %= 10;
        }
        // 处理前导
        int index = ans[0] == 0 ? 1 : 0;
        StringBuffer sb = new StringBuffer();
        while (index < ans.length) {
            sb.append(ans[index++]);
        }
        return sb.toString();
    }


    static String zero(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("0");
        }
        return sb.toString();
    }

    /**
     * 字符串大数相加
     *
     * @param num1
     * @param num2
     * @return
     */
    static public StringBuilder addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int a = carry + (i >= 0 ? num1.charAt(i) - '0' : 0) + (j >= 0 ? num2.charAt(j) - '0' : 0);
            sb.append(a % 10);
            carry = a / 10;
        }
        if (carry > 0) {
            sb.append(carry);
        }
        return sb.reverse();
    }

    /**
     * 字符串大数相加，并指定相加的偏移量
     *
     * @param num1
     * @param num2
     * @param offset num2的偏移量
     * @return
     */
    static public StringBuilder addStrings(String num1, String num2, int offset) {
        if (offset < 0) {
            throw new RuntimeException("偏移量不支持负数");
        }
        StringBuilder sb = new StringBuilder();
        int len1 = num1.length();
        int len2 = num2.length();
        int carry = 0;
        // 在j初始化时需要把偏移量处理好
        for (int i = len1 - 1, j = len2 - 1 + offset; i >= 0 || j >= 0; i--, j--) {
            int a = carry + (i >= 0 ? num1.charAt(i) - '0' : 0) + (j >= 0 && j < len2 ? num2.charAt(j) - '0' : 0);
            sb.append(a % 10);
            carry = a / 10;
        }
        if (carry > 0) {
            sb.append(carry);
        }
        return sb.reverse();
    }
}