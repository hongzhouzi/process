package cn.algorithm.leetcode;

import sun.nio.cs.ext.MacHebrew;

import java.util.*;

/**
 * @author hongzhou.wei
 * @date 2020/10/14
 */
public class P1002_FindCommonCharacters {
    public static void main(String[] args) {
        String [] A = {"bella","label","roller"};
        System.out.println(new P1002_FindCommonCharacters.Solution().commonChars(A));
    }

    static class Solution {
        public List<String> commonChars(String[] A) {
            int[] minfreq = new int[26];
            // 统计每个单词中各个字母的最小出现次数
            Arrays.fill(minfreq, Integer.MAX_VALUE);
            for (String word: A) {
                int[] freq = new int[26];
                for (int i = 0,length = word.length(); i < length; ++i) {
                    char ch = word.charAt(i);
                    ++freq[ch - 'a'];
                }
                for (int i = 0; i < 26; ++i) {
                    minfreq[i] = Math.min(minfreq[i], freq[i]);
                }
            }

            // 按最小出现的次数准备输出结果
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < 26; ++i) {
                for (int j = 0; j < minfreq[i]; ++j) {
                    ans.add(String.valueOf((char) (i + 'a')));
                }
            }
            return ans;
        }
    }
}
