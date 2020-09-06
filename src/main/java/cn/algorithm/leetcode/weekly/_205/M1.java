package cn.algorithm.leetcode.weekly._205;

/**
 * @author hongzhou.wei
 * @date 2020/9/6
 */
public class M1 {
    public static void main(String[] args) {
//        String s  = s = "?zs";
//        String s  ="ubv?w";
//        String s  ="j?qg??b";
        String s = "??yw?ipkj?";

        System.out.println(modifyString(s));
    }

    static public String modifyString(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '?') {
                chars[i] = 'a';
                while (true) {
                    if (chars[i] != (i + 1 >= chars.length ? '@' : chars[i + 1])
                        && chars[i] != (i - 1 < 0 ? '@' : chars[i - 1])) {
                        break;
                    } else {
                        chars[i] = (char) (chars[i] + 1);
                    }
                }
            }
        }
        return new String(chars);
    }


    static public String modifyString1(String S) {
        char[] s = S.toCharArray();
        for(int i = 0;i < s.length;i++){
            if(s[i] == '?'){
                for(int j = 0;j < 26;j++){
                    char x = (char)('a'+j);
                    if(i-1 >= 0 && x == s[i-1])continue;
                    if(i+1 < s.length && x == s[i+1])continue;
                    s[i] = x;
                    break;
                }
            }
        }
        return new String(s);
    }
}
