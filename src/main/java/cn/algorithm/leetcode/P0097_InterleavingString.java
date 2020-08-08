package cn.algorithm.leetcode;

/**
 * 97. Interleaving String
 * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
 * Example 1:
 * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 * Output: true
 * Example 2:
 * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 * Output: false
 *
 * @author hongzhou.wei
 * @date 2020/7/18
 */
public class P0097_InterleavingString {
    public static void main(String[] args) {
        String s1 = "aabcc" , s2 = "dbbca" , s3 = "aadbbcbcac" ;
//        String s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc";
//        String s1 = "", s2 = "", s3 = "a";
//        String s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac";
        System.out.println(isInterleave1(s1, s2, s3));
    }

    /**
     * 双指针：（错误）遍历s3，将当前字符在s1和s2中从前往后依次找，如果刚好都可以在s1和s2
     * 中找到，就返回true，反之返回false
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    static public boolean isInterleave(String s1, String s2, String s3) {
        int i1 = 0, i2 = 0;
        for (int i = 0; i < s3.length(); ) {
            // 从s1字符串中往后找
            while (i < s3.length() && i1 < s1.length() && s3.charAt(i) == s1.charAt(i1)) {
                i1++;
                i++;
            }
            // 从s2字符串中往后找
            while (i < s3.length() && i2 < s2.length() && s3.charAt(i) == s2.charAt(i2)) {
                i2++;
                i++;
            }
            // 若下个不能在s1中找到，就返回false
            if (i1 < s1.length() && s3.charAt(i) != s1.charAt(i1)) {
                return false;
            }
            // 找完了
            if (i1 == s1.length() && i2 == s2.length() && i == s3.length()) {
                break;
            }
            if (i1 >= s1.length()) {
                return false;
            }
        }
        return true;
    }


    static public boolean isInterleave1(String s1, String s2, String s3) {
        int l1 = s1.length(), l2 = s2.length(), l3 = s3.length();
        if (l1 + l2 != l3) {
            return false;
        }
        boolean dp[][] = new boolean[l1 + 1][l2 + 1];
        // 边界条件
        dp[0][0] = true;

        for (int i1 = 0; i1 <= l1; i1++) {
            for (int i2 = 0; i2 <= l2; i2++) {
                int p = i1 + i2 - 1;
                if (i1 > 0) {
                    dp[i1][i2] =dp[i1][i2] ||  (dp[i1 - 1][i2] && s1.charAt(i1 - 1) == s3.charAt(p)) ;
                }
                if (i2 > 0) {
                    dp[i1][i2] = dp[i1][i2] || (dp[i1][i2 - 1] && s2.charAt(i2 - 1) == s3.charAt(p));
                }
            }
        }
        return dp[l1][l2];
    }
}
