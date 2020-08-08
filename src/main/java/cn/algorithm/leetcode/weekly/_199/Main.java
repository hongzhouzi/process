package cn.algorithm.leetcode.weekly._199;

import java.util.Arrays;

/**
 * @author hongzhou.wei
 * @date 2020/7/26
 */
public class Main {

    public static void main(String[] args) {
        String s ="codeleet";
        int a[] = new int[]{4,5,6,7,0,2,1,3};
        System.out.println(restoreString(s,a));
    }
    static public String restoreString(String s, int[] indices) {
//        StringBuilder sb = new StringBuilder();
////        for (int i = 0; i < indices.length; i++) {
////            sb.append(s.charAt(indices[i]));
////        }
////        return sb.toString();
        char a[] = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            a[indices[i]] = s.charAt(i);
        }
        return new String(a);

    }
}
