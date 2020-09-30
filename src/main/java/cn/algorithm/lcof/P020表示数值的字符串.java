package cn.algorithm.lcof;

/**
 * 剑指 Offer 20. 表示数值的字符串
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100"、"5e2"、"-123"、"3.1416"、"-1E-16"、
 * "0123"都表示数值，但"12e"、"1a3.14"、"1.2.3"、"+-5"及"12e+5.4"都不是。
 *
 * @author hongzhou.wei
 * @date 2020/9/2
 */
public class P020表示数值的字符串 {

    public static void main(String[] args) {
        String s = ".e1";
        String s1 = "5e2";
        String s2 = "-123";
        /*String s = "3.1416";
        String s1 = "-1E-16";
        String s2 = "0123";*/
        /*String s = "12e";
        String s1 = "1a3.14";
        String s2 = "1.2.3";*/
        /*String s = "+-5";
        String s1 = "12e+5.4";
        String s2 = "1.2.3";*/
        System.out.println(isNumber(s));
        System.out.println(isNumber(s1));
        System.out.println(isNumber(s2));
    }

    /**
     * 科学计数法：e后面的数表示多少次方，为负就是负多少次方
     * e后面不能有小数，可以有正负号，后面必须有数字；
     * 小数点后不能再有小数点，不能出现在开头和结尾；
     * 不能出现多个正负号，除了有e的情况
     *
     * @param s
     * @return
     */
    static public boolean isNumber(String s) {
        // s为空对象或 s长度为0(空字符串)时, 不能表示数值
        if (s == null || s.length() == 0) {
            return false;
        }
        // 标记是否遇到数位、小数点、‘e’或'E'
        boolean isNum = false, isDot = false, iseOrE = false;
        // 删除字符串头尾的空格，转为字符数组，方便遍历判断每个字符
        char[] str = s.trim().toCharArray();
        for (int i = 0; i < str.length; i++) {
            // 判断当前字符是否为 0~9 的数位
            if (str[i] >= '0' && str[i] <= '9') {
                isNum = true;
            }
            // 遇到小数点
            else if (str[i] == '.') {
                // 小数点之前可以没有整数，但是不能重复出现小数点、或出现‘e’、'E'
                if (isDot || iseOrE) {
                    return false;
                }
                // 标记已经遇到小数点
                isDot = true;
            }
            // 遇到‘e’或'E'
            else if (str[i] == 'e' || str[i] == 'E') {
                // ‘e’或'E'前面必须有整数，且前面不能重复出现‘e’或'E'
                if (!isNum || iseOrE) {
                    return false;
                }
                // 标记已经遇到‘e’或'E'
                iseOrE = true;
                // 重置isNum，因为‘e’或'E'之后也必须接上整数，防止出现 123e或者123e+的非法情况
                isNum = false;
            } else if (str[i] == '-' || str[i] == '+') {
                // 正负号只可能出现在第一个位置，或者出现在‘e’或'E'的后面一个位置
                if (i != 0 && str[i - 1] != 'e' && str[i - 1] != 'E') {
                    return false;
                }
            }
            // 其它情况均为不合法字符
            else {
                return false;
            }
        }
        return isNum;
    }
}
