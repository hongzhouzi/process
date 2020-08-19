package cn.algorithm.leetcode;

/**
 * 647. 回文子串
 * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
 * 示例 1：
 * 输入："abc"
 * 输出：3
 * 解释：三个回文子串: "a", "b", "c"
 * 示例 2：
 * 输入："aaa"
 * 输出：6
 * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 * 提示：
 * 输入的字符串长度不会超过 1000 。
 *
 * @author hongzhou.wei
 * @date 2020/8/19
 */
public class P0647_PalindromicSubstrings {


    public static void main(String[] args) {
//        String s = "aaa";
        String s = "aba";
        System.out.println(countSubstrings(s));
    }

    /**
     * 滑动窗口，依次取 1 到 length 的长度，再从字符串中找出对应长度的字符串，判断是否为回文串
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     *
     * @param s
     * @return
     */
    static public int countSubstrings(String s) {
        int count = 0, len = s.length();
        for (int curLen = 1; curLen <= len; curLen++) {
            for (int i = 0; i <= len - curLen; i++) {
                if (isPalindromic(s.substring(i, i + curLen))) {
                    count++;
                }
            }
        }
        return count;
    }

    static boolean isPalindromic(String s) {
        int len = s.length(), i = 0, j = len - 1;
        while (i <= j) {
            if (s.charAt(i++) != s.charAt(j--)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 将每个字符作为中心点向两边扩展，若扩展后的字符传是回文串就继续向两边扩展。
     * 长度为 n 的字符串会生成 2n-1 组回文中心 [l_i, r_i] 其中 l_i = ⌊ 2i ⌋，r_i = l_i + (i mod 2)
     * 这样我们只要从 0 到 2n - 2 遍历 i，就可以得到所有可能的回文中心
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     *
     * @param s
     * @return
     */
    public int countSubstrings1(String s) {
        int n = s.length(), ans = 0;
        for (int i = 0; i < 2 * n - 1; ++i) {
            int l = i / 2, r = i / 2 + i % 2;
            while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                --l;
                ++r;
                ++ans;
            }
        }
        return ans;
    }


    public int countSubstrings2(String s) {
        int n = s.length();
        StringBuffer t = new StringBuffer("$#");
        for (int i = 0; i < n; ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        n = t.length();
        t.append('!');

        int[] f = new int[n];
        int iMax = 0, rMax = 0, ans = 0;
        for (int i = 1; i < n; ++i) {
            // 初始化 f[i]
            f[i] = i <= rMax ? Math.min(rMax - i + 1, f[2 * iMax - i]) : 1;
            // 中心拓展
            while (t.charAt(i + f[i]) == t.charAt(i - f[i])) {
                ++f[i];
            }
            // 动态维护 iMax 和 rMax
            if (i + f[i] - 1 > rMax) {
                iMax = i;
                rMax = i + f[i] - 1;
            }
            // 统计答案, 当前贡献为 (f[i] - 1) / 2 上取整
            ans += f[i] / 2;
        }

        return ans;
    }

}
