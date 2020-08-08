package cn.algorithm.leetcode;

import java.util.*;

/**
 * 336. 回文对
 * 给定一组 互不相同 的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。
 * 示例 1：
 * 输入：["abcd","dcba","lls","s","sssll"]
 * 输出：[[0,1],[1,0],[3,2],[2,4]]
 * 解释：可拼接成的回文串为 ["dcbaabcd","abcddcba","slls","llssssll"]
 * 示例 2：
 * 输入：["bat","tab","cat"]
 * 输出：[[0,1],[1,0]]
 * 解释：可拼接成的回文串为 ["battab","tabbat"]
 *
 * @author hongzhou.wei
 * @date 2020/8/6
 */
public class P0336_PalindromePairs {
    public static void main(String[] args) {
//        String[] s = {"abcd", "dcba", "lls", "s", "sssll"};
        String[] s = {"bat","tab","cat"};
        System.out.println(palindromePairs(s));

        /*String ss = "ssjsss";
        System.out.println(isPalindrome(ss));*/

        /*List<List<Integer>> ret = new LinkedList<>();
        ret.add(new LinkedList<Integer>() {{
            add(2);
            add(3);
        }});*/
    }

    /**
     * 暴力：双重循环遍历字符串数组，判断组成的字符串是否是回文串，是则记录
     * 时间复杂度：O(n^2 * m) 超时了
     *
     * @param words
     * @return
     */
    static public List<List<Integer>> palindromePairs(String[] words) {
        int length = words.length;
        List<List<Integer>> ret = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i == j) {
                    continue;
                }
                if (isPalindrome(words[i] + words[j])) {
                    List<Integer> list = new LinkedList<>();
                    list.add(i);
                    list.add(j);
                    ret.add(list);
                    ret.add(Arrays.asList(i,j));
                }
            }
        }
        return ret;
    }

    static boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }


    // ================================================================================
    public List<List<Integer>> palindromePairs2(String[] words) {
        //构建trie
        TrieNode root = buildTire(words);
        List<List<Integer>> res = new ArrayList();
        //对于每个单词,在trie中搜索
        for(int i = 0;i < words.length;i++){
            search(words[i],i,root,res);
        }
        return res;
    }

    private void search(String word,int i,TrieNode node,List<List<Integer>> res){
        int k = word.length(),j = 0;
        for(;j < k;j++){
            //循环中在trie中发现的单词是比当前word长度要短的
            char c = word.charAt(j);
            if(node.index != -1&&isPalidrome(word,j,k-1)){
                res.add(Arrays.asList(i,node.index));
            }
            //所有可能被排除，提前退出
            if(node.children[c-'a']==null) return;

            node = node.children[c-'a'];
        }
        //长度等于当前搜索的word的单词
        if(node.index != -1 && node.index != i){
            res.add(Arrays.asList(i,node.index));
        }
        //长度长于当前搜索的word的单词
        for(int rest : node.belowIsPali){
            res.add(Arrays.asList(i,rest));
        }
    }

    private TrieNode buildTire(String[] words){
        TrieNode root = new TrieNode();
        for(int i = 0;i<words.length;i++){
            addWord(root,words[i],i);
        }
        return root;
    }

    private void addWord(TrieNode root,String word,int i){
        for(int j = word.length()-1;j >= 0;j--){
            if(isPalidrome(word,0,j)){
                root.belowIsPali.add(i);
            }
            char c = word.charAt(j);
            if(root.children[c-'a'] == null){
                root.children[c-'a'] = new TrieNode();
            }
            root = root.children[c-'a'];
        }
        root.index = i;
    }

    private boolean isPalidrome(String word,int i,int j){
        if(word.length()<=1){
            return true;
        }
        while(i<j){
            if(word.charAt(i++)!=word.charAt(j--)) return false;
        }
        return true;
    }
    class TrieNode {
        int index;
        List<Integer> belowIsPali;
        TrieNode[] children;

        public TrieNode(){
            index = -1;
            belowIsPali = new ArrayList<Integer>();
            children = new TrieNode[26];
        }
    }



    // ================================================================================

    List<String>         wordsRev = new ArrayList<String>();
    Map<String, Integer> indices  = new HashMap<String, Integer>();

    public List<List<Integer>> palindromePairs1(String[] words) {
        int n = words.length;
        for (String word: words) {
            wordsRev.add(new StringBuffer(word).reverse().toString());
        }
        for (int i = 0; i < n; ++i) {
            indices.put(wordsRev.get(i), i);
        }

        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for (int i = 0; i < n; i++) {
            String word = words[i];
            int m = words[i].length();
            if (m == 0) {
                continue;
            }
            for (int j = 0; j <= m; j++) {
                if (isPalindrome(word, j, m - 1)) {
                    int leftId = findWord(word, 0, j - 1);
                    if (leftId != -1 && leftId != i) {
                        ret.add(Arrays.asList(i, leftId));
                    }
                }
                if (j != 0 && isPalindrome(word, 0, j - 1)) {
                    int rightId = findWord(word, j, m - 1);
                    if (rightId != -1 && rightId != i) {
                        ret.add(Arrays.asList(rightId, i));
                    }
                }
            }
        }
        return ret;
    }

    public boolean isPalindrome(String s, int left, int right) {
        int len = right - left + 1;
        for (int i = 0; i < len / 2; i++) {
            if (s.charAt(left + i) != s.charAt(right - i)) {
                return false;
            }
        }
        return true;
    }

    public int findWord(String s, int left, int right) {
        return indices.getOrDefault(s.substring(left, right + 1), -1);
    }

}
