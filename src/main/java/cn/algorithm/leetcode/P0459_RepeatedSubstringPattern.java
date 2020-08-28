package cn.algorithm.leetcode;

/**
 * @author hongzhou.wei
 * @date 2020/8/24
 */
public class P0459_RepeatedSubstringPattern {
    public static void main(String[] args) {
        String s = "abcabc";
        System.out.println(repeatedSubstringPattern1(s));
    }

    /**
     * 【构造双倍字符串】
     *  若字符串是重复的子串组成如abab，构造成双倍后变成abababab，然后在双倍子串中找原字符串，
     *  若不是重复子串构成，则找到的索引处等于原字符串长度，则若能找到则下标必定小于原字符串长度。
     *
     * @param s
     * @return
     */
    static public boolean repeatedSubstringPattern1(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }

    static public boolean repeatedSubstringPattern(String s) {
        for (int i = 1; i <= s.length() / 2; i++) {
            String sub = s.substring(0, i);
            int count = 1, subLen = sub.length();
            for (int j = i; j <= s.length() - subLen; j = j + subLen) {
                String inSub = s.substring(j, j + subLen);
                if (sub.equals(inSub)) {
                    count++;
                } else {
                    break;
                }
            }
            if (count == s.length() / subLen) {
                return true;
            }
        }
        return false;
    }

}
