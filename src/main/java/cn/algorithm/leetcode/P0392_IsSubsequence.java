package cn.algorithm.leetcode;

/**
 * 392. 判断子序列
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。你可以认为 s 和 t 中仅包含英文小写字母。字符串 t 可能会
 * 很长（长度 ~= 500,000），而 s 是个短字符串（长度 <=100）。字符串的一个子序列是原始字符串删除一些（也可
 * 以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 * 示例 1:
 * s = "abc", t = "ahbgdc"
 * 返回 true.
 * 示例 2:
 * s = "axc", t = "ahbgdc"
 * 返回 false.
 *
 * 后续挑战 :
 * 如果有大量输入的 S，称作S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
 *
 * @author hongzhou.wei
 * @date 2020/7/27
 */
public class P0392_IsSubsequence {
    public static void main(String[] args) {
//        String s = "abc", t = "ahbgdc";
//        String s = "", t = "ahbgdc";
//        String s = "abc", t = "";
//        String s = "b", t = "c";
//        String s = "axc", t = "ahbgdc";
        String s = "b", t = "b";
        System.out.println(isSubsequence(s, t));
    }

    /**
     * 【双指针】
     *
     * @param s
     * @param t
     * @return
     */
    static public boolean isSubsequence(String s, String t) {
        // 各种边界情况要考虑到位
        int i = 0, j = 0;
        while (j < t.length() && i < s.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == s.length();
    }

    /**
     * 【动态规划】当有大量输入s时，也能从容应对
     * 双指针算法有大量的时间在找下个匹配字符，可以优化。预处理对t中每一个位置，从该位置往后每个字符第一次出现的位置
     *
     * @param s
     * @param t
     * @return
     */
    static public boolean isSubsequence0(String s, String t) {
        int n = s.length(), m = t.length();

        int[][] f = new int[m + 1][26];
        for (int i = 0; i < 26; i++) {
            f[m][i] = m;
        }

        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                if (t.charAt(i) == j + 'a')
                    f[i][j] = i;
                else
                    f[i][j] = f[i + 1][j];
            }
        }
        int add = 0;
        for (int i = 0; i < n; i++) {
            if (f[add][s.charAt(i) - 'a'] == m) {
                return false;
            }
            add = f[add][s.charAt(i) - 'a'] + 1;
        }
        return true;
    }

    /**
     * 使用字符串的indexOf函数查找
     *
     * @param s
     * @param t
     * @return
     */
    static public boolean isSubsequence1(String s, String t) {
        int i = -1;
        for (char c : s.toCharArray()) {
            i = t.indexOf(c, i + 1);
            if (i == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将字符串转成char[] ，只使用一个索引，最后判断时根据t的长度判断
     *
     * @param s
     * @param t
     * @return
     */
    static public boolean isSubsequence2(String s, String t) {
        int i = 0;
        for (char c : s.toCharArray()) {
            if (i < t.length() && c != t.charAt(i)) {
                i++;
            }
            i++;
        }
        // 根据t的长度来判断，t中找完了都没找到，就返回false
        return i <= t.length();
    }
}
