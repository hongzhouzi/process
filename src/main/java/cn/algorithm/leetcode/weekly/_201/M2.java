package cn.algorithm.leetcode.weekly._201;

import java.util.Arrays;

/**
 * 5484. 找出第 N 个二进制字符串中的第 K 位 显示英文描述
 * 题目难度Medium
 * 给你两个正整数 n 和 k，二进制字符串  Sn 的形成规则如下：
 *
 * S1 = "0"
 * 当 i > 1 时，Si = Si-1 + "1" + reverse(invert(Si-1))
 * 其中 + 表示串联操作，reverse(x) 返回反转 x 后得到的字符串，而 invert(x) 则会翻转 x 中的每一位（0 变为 1，而 1 变为 0）
 *
 * 例如，符合上述描述的序列的前 4 个字符串依次是：
 *
 * S1 = "0"
 * S2 = "011"
 * S3 = "0111001"
 * S4 = "011100110110001"
 * 请你返回  Sn 的 第 k 位字符 ，题目数据保证 k 一定在 Sn 长度范围以内。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 3, k = 1
 * 输出："0"
 * 解释：S3 为 "0111001"，其第 1 位为 "0" 。
 * 示例 2：
 *
 * 输入：n = 4, k = 11
 * 输出："1"
 * 解释：S4 为 "011100110110001"，其第 11 位为 "1" 。
 * 示例 3：
 *
 * 输入：n = 1, k = 1
 * 输出："0"
 * 示例 4：
 *
 * 输入：n = 2, k = 3
 * 输出："1"
 *
 *
 * 提示：
 *
 * 1 <= n <= 20
 * 1 <= k <= 2n - 1
 *
 * @author hongzhou.wei
 * @date 2020/8/9
 */
public class M2 {
    public static void main(String[] args) {
//        int n = 3, k = 1;
//        int n = 4, k = 11;
        int n = 2, k = 3;
        System.out.println(findKthBit(n,k));
    }

    static public char findKthBit(int n, int k) {
        String [] dp = new String[n+1];
        dp[1] = "0";
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1]+"1" + invertRe(dp[i-1]);
        }
        return dp[n].charAt(k-1);
    }

   static String invertRe(String s){
       /*char[] c = s.toCharArray();
       for (int i = 0; i < c.length; i++) {
           if(c[i] == '0'){
               c[i] = '1';
           }else {
               c[i] = '0';
           }
       }
       return new StringBuilder(Arrays.toString(c)).reverse().toString();
*/
       StringBuilder stringBuilder = new StringBuilder(s);
       for (int i = 0; i < stringBuilder.length(); i++) {
           if(stringBuilder.charAt(i) == '0'){
               stringBuilder.setCharAt(i,'1');
           }else {
               stringBuilder.setCharAt(i,'0');
           }
       }
       return stringBuilder.reverse().toString();
    }

    // 字符串处理的相关方法需要熟悉哈all


    public char findKthBit1(int n, int k) {
        String s = "0";
        while (n>1) {
            --n;
            s = s+"1"+sinv(s);
        }
        return s.charAt(k-1);
    }

    public String sinv(String s) {
        StringBuilder a = new StringBuilder();
        for (int i = s.length()-1; i>=0; --i) {
            char c = s.charAt(i);
            c^=1;
            a.append(c);
        }
        return a.toString();
    }
}
