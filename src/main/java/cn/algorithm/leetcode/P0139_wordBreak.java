package cn.algorithm.leetcode;

import cn.examination.pass58.Solution;

import java.util.*;

/**
 * 给定一个非空字符串 weekly 和一个包含非空单词列表的字典 wordDict，
 * 判定s是否可以被空格拆分为一个或多个在字典中出现的单词。
 * 说明：
 * 拆分时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 * <p>
 * 示例 1：
 * 输入: weekly = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
 * <p>
 * 示例 2：
 * 输入: weekly = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
 *      注意你可以重复使用字典中的单词。
 * <p>
 * 示例 3：
 * 输入: weekly = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 *
 * @author hongzhou.wei
 * @date 2020/6/25
 */
public class P0139_wordBreak {
    public static void main(String[] args) {
        String s = "abcd";
        List<String> wordDict = new ArrayList<String>(Arrays.asList("a","abc","b","cd"));
        System.out.println(wordBreak1(s, wordDict));
    }

    /**
     * 依次遍历字符串中的字符，取子串，判断子串字符在字典集合中
     *
     * @param s
     * @param wordDict
     * @return
     */
    static public boolean wordBreak(String s, List<String> wordDict) {
        if (wordDict.size() == 0) {
            return false;
        }
        Set<String> wordDictSet = new HashSet<>(wordDict);
        for (int i = 0, beginIndex = 0; i < s.length(); i++) {
            if (wordDictSet.contains(s.substring(beginIndex, i + 1))) {
                beginIndex = i + 1;
                i++;
            } else {
                if (i == s.length() - 1) {
                    return false;
                }
            }
        }
        return true;
    }


    static public boolean wordBreak1(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
