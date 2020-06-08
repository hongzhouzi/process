package cn.algorithm.leetcode;

/**
 * @author: whz
 * @date: 2019/8/19
 **/
public class LongestPalindrome {
    public static void main(String[] args) {
        String s ="abccba";
        String s1 ="acbaca";
        System.out.println(isPalindrome(s));
        System.out.println(isPalindrome(s1));
//        System.out.println(longestPalindrome(s));
        System.out.println(longestPalindrome(s1));
    }
    static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            //从i下标处往两边扩展，直到扩展至不为回文就停，返回的扩展的长度
            int len1 = expandAroundCenter(s, i, i);//奇
            int len2 = expandAroundCenter(s, i, i + 1);//偶
            int len = Math.max(len1, len2);//取较长的
            if (len > end - start) {//比之前的最长记录还长就更新
                //根据回文长度和开始扩展的位置计算回文起始位置
                start = i - (len - 1) / 2;
                end = i + len / 2;
//                System.out.println("len1:"+len1+ " len2:"+len2);
//                System.out.println(" i:"+i+" len:"+len+" start:"+start+" end:"+end);
            }
        }
        return s.substring(start, end + 1);
    }

    static private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }
    /*static String longestPalindrome(String s) {
        char sChar[] = s.toCharArray();
        for(int i=sChar.length-1; i>0; i--){

        }

        return null;
    }*/
    static boolean isPalindrome(String s){
        char str[] = s.toCharArray();
        for(int i=0; i<=str.length/2;i++){
            if(str[i] != str[str.length-1-i]){
                return false;
            }
        }
        return true;
    }
}
