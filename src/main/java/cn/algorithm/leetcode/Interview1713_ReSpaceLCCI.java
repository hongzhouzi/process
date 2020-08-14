package cn.algorithm.leetcode;

import java.util.*;

/**
 * 哦，不！你不小心把一个长篇文章中的空格、标点都删掉了，并且大写也弄成了小写。像句子"I reset the computer. It still didn’t boot!"已
 * 经变成了"iresetthecomputeritstilldidntboot"。在处理标点符号和大小写之前，你得先把它断成词语。当然了，你有一本厚厚的
 * 词典dictionary，不过，有些词没在词典里。假设文章用sentence表示，设计一个算法，把文章断开，要求未识别的字符最少，返回未识别的字符数。
 * 注意：本题相对原题稍作改动，只需返回未识别的字符数
 * 示例：
 * 输入：
 * dictionary = ["looked","just","like","her","brother"]
 * sentence = "jesslookedjustliketimherbrother"
 * 输出： 7
 * 解释： 断句后为"jess looked just like tim her brother"，共7个未识别字符。
 *
 * @author hongzhou.wei
 * @date 2020/7/9
 */
public class Interview1713_ReSpaceLCCI {
    // TODO:
    public static void main(String[] args) {
        String dictionary[] = {"looked", "just", "like", "her", "brother"};
        String sentence = "jesslookedjustliketimherbrother";
        System.out.println(respaceDp(dictionary,sentence));
    }

    /**
     * 【动态规划】
     * 状态：当前未匹配最少字符数
     * 转移方程：
     * 第 i + 1 个字符未匹配，则 dp[i + 1] = dp[i] + 1，即不匹配数加 1;
     * 遍历前 i 个字符，若以其中某一个下标 idx 为开头、以第 i + 1 个字符
     * 为结尾的字符串正好在词典里，则 dp[i] = min(dp[i], dp[idx]) 更新 dp[i]。
     *
     * @param dictionary
     * @param sentence
     * @return
     */
    static public int respaceDp(String[] dictionary, String sentence) {
        Set<String> dictSet = new HashSet<>(Arrays.asList(dictionary));
        int len = sentence.length();
        int dp[] = new int[len + 1];
        // 需要取到=len，匹配时遍历的len之前的字符
        for (int i = 1; i <= len; i++) {
            dp[i] = dp[i - 1] + 1;
            for (int idx = 0; idx < i; idx++) {
                // 当前子串在字典中，就更新当前dp
                if (dictSet.contains(sentence.substring(idx, i))) {
                    dp[i] = Math.min(dp[i], dp[idx]);
                }
            }
        }
        return dp[len];
    }
}