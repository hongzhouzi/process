package cn.algorithm.leetcode;

/**
 * 给你两个二进制字符串，返回它们的和（用二进制表示）。
 * 输入为 非空 字符串且只包含数字 1 和 0。
 * 示例1:
 * 输入: a = "11", b = "1"
 * 输出: "100"
 * 示例?2:
 * 输入: a = "1010", b = "1011"
 * 输出: "10101"
 * 提示：
 * 每个字符串仅由字符 '0' 或 '1' 组成。
 * 1 <= a.length, b.length <= 10^4
 * 字符串如果不是 "0" ，就都不含前导零。
 * <p>
 * 思路：将字符串中每个字符从后往前计算依次相加，相加过程中要加上进位，最后将字符反转一下就OK了
 * 优化的解法在最后面
 *
 * @author hongzhou.wei
 * @date 2020/6/23
 */
public class P0067_addBinary {
    public static void main(String[] args) {
        String a = "11", b = "100110";
        System.out.println(addBinary2(a, b));
        System.out.println(addBinary3(a, b));
    }

    static public String addBinary(String a, String b) {
        String l = a.length() > b.length() ? a : b;
        String s = a.length() > b.length() ? b : a;
        StringBuilder res = new StringBuilder();
        byte f = 0;
        for (int i = l.length() - 1, j = s.length() - 1; i >= 0; i--, j--) {
            byte sy = (byte) (j >= 0 ? Byte.parseByte(String.valueOf(s.charAt(j))) : 0);
            if ((Byte.parseByte(String.valueOf(l.charAt(i))) + sy + f) > 1) {
                res.append((Byte.parseByte(String.valueOf(l.charAt(i))) + sy + f) & 1);
                f = 1;
            } else {
                res.append((Byte.parseByte(String.valueOf(l.charAt(i))) + sy + f) & 1);
                f = 0;
            }
        }
        if (f > 0) {
            res.append(f);
        }
        return res.reverse().toString();
    }


    static public String addBinary2(String a, String b) {
        StringBuilder res = new StringBuilder();
        byte f = 0;
        for (int ai = a.length() - 1, bi = b.length() - 1; ai >= 0 || bi >= 0; ai--, bi--) {
            byte aa = (byte) (ai >= 0 ? a.charAt(ai) - '0' : 0);
            byte bb = (byte) (bi >= 0 ? b.charAt(bi) - '0' : 0);
            // 计算当前位的值
            res.append((aa + bb + f) & 1);
            // 处理进位
            f = (byte) ((aa + bb + f) > 1 ? 1 : 0);
        }
        // 处理最后的进位
        if (f > 0) {
            res.append(f);
        }
        return res.reverse().toString();
    }

    static public String addBinary3(String a, String b) {
        StringBuilder res = new StringBuilder();
        byte f = 0;
        for (int ai = a.length() - 1, bi = b.length() - 1; ai >= 0 || bi >= 0; ai--, bi--) {
            byte v = (byte) ((ai >= 0 ? a.charAt(ai) - '0' : 0) + (bi >= 0 ? b.charAt(bi) - '0' : 0) + f);
            // 计算当前位的值
            res.append(v & 1);
            // 处理进位，进位后最高进1，和2按位与即可
            f = (byte) ((v & 2) >> 1);
        }
        // 处理最后的进位
        if (f > 0) {
            res.append(f);
        }
        return res.reverse().toString();
    }

    /**
     * 思路：将字符串中每个字符从后往前计算依次相加，相加过程中要加上进位，最后将字符反转一下就OK了
     *
     * @param a
     * @param b
     * @return
     */
    static public String addBinary4(String a, String b) {
        StringBuilder res = new StringBuilder();
        byte carry = 0;
        for (int ai = a.length() - 1, bi = b.length() - 1; ai >= 0 || bi >= 0; ai--, bi--) {
            // 计算值的过程：从后往前取字符，返回按索引取到的值，如果索引为负数就返回0，最后加上进位
            byte v = (byte) ((ai >= 0 ? a.charAt(ai) - '0' : 0) + (bi >= 0 ? b.charAt(bi) - '0' : 0) + carry);
            // 计算当前位的值
            res.append(v & 1);
            // 处理进位
            carry = (byte) (v > 1 ? 1 : 0);
            // carry = (byte) ((v & 2 )>>1); // 使用位运算，因为只要有进位，那么第二位上肯定为1，这样将值和2按位与再右移一位即可
        }
        // 处理最后的进位
        if (carry > 0) {
            res.append(carry);
        }
        return res.reverse().toString();
    }
}
