package cn.algorithm.leetcode;

import java.util.*;

/**
 * @author hongzhou.wei
 * @date 2020/8/26
 */
public class P0017_LetterCombinationsofaPhoneNumber {
    public static void main(String[] args) {
        String s = "233";
        System.out.println(new P0017_LetterCombinationsofaPhoneNumber().letterCombinations(s));
        System.out.println(new P0017_LetterCombinationsofaPhoneNumber().letterCombinations0(s));
        System.out.println(new P0017_LetterCombinationsofaPhoneNumber().letterCombinations1(s));
    }

    Map<Character, String> dictMap = new HashMap<Character, String>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};

    /**
     * 【递归】
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        if (digits.length() < 1) {
            return new ArrayList<>();
        }
        List<String> ret = new LinkedList<>();
        String dict = dictMap.get(digits.charAt(0));
        for (int i = 0; i < dict.length(); i++) {
            ret.add(dict.charAt(i) + "");
        }
        if (digits.length() == 1) {
            return ret;
        }
        return recursion(digits, 1, ret);
    }

    List<String> recursion(String digits, int idx, List<String> res) {
        if (digits.length() == idx) {
            return res;
        }
        String dict = dictMap.get(digits.charAt(idx));
        List<String> ret = new LinkedList<>();
        for (int i = 0; i < dict.length(); i++) {
            for (String s : res) {
                ret.add(s + dict.charAt(i));
            }
        }
        return recursion(digits, idx + 1, ret);
    }

    /**
     * 【递归回溯优化】把string优化成StringBuilder
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations0(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return res;
        }
        dfs(new StringBuilder(), digits, 0, res);
        return res;
    }

    void dfs(StringBuilder sb, String digits, int n, List<String> res) {
        if (n == digits.length()) {
            res.add(sb.toString());
            return;
        }
        String s = dictMap.get(digits.charAt(n));
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            dfs(sb, digits, n + 1, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 【队列】遍历数字时，依次从队列中取出拼接上
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations1(String digits) {
        LinkedList<String> ret = new LinkedList<>();
        if(digits.length() == 0){
            return ret;
        }
        ret.add("");
        for (int i = 0; i < digits.length(); i++) {
            String dict = dictMap.get(digits.charAt(i));
            while (!ret.isEmpty() && ret.peek().length() == i) {
                String poll = ret.poll();
                for (int j = 0; j < dict.length(); j++) {
                    ret.offer(poll + dict.charAt(j));
                }
            }
        }
        return ret;
    }
}
