package cn.algorithm.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 20. 有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * 示例 1:
 * 输入: "()"
 * 输出: true
 * 示例 2:
 * 输入: "()[]{}"
 * 输出: true
 * 示例 3:
 * 输入: "(]"
 * 输出: false
 * 示例 4:
 * 输入: "([)]"
 * 输出: false
 * 示例 5:
 * 输入: "{[]}"
 * 输出: true
 *
 * @author hongzhou.wei
 * @date 2020/8/14
 */
public class P0020_ValidParentheses {
    public static void main(String[] args) {
        String s = "([)]";
//        String s = "()[]{}";
//        String s = "()";
//        String s = "{[]}";
        System.out.println(isValid(s));
        System.out.println(isValid1(s));
        System.out.println(isValid2(s));
    }

    /**
     * map存配对字符+栈
     *
     * @param s
     * @return
     */
    static public boolean isValid(String s) {
        int len = s.length();
        if (len == 0) {
            return true;
        }
        // 优化，奇数长度肯定不能
        if ((len % 2) == 1 ){
            return false;
        }
        Map<Character, Character> characterMap = new HashMap<Character, Character>(8) {{
            put('(', ')');
            put('{', '}');
            put('[', ']');
        }};
        LinkedList<Character> stack = new LinkedList<>();
        stack.push(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            if (!stack.isEmpty() && characterMap.getOrDefault(stack.peek(), '0') == s.charAt(i)) {
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }

    /**
     * 栈+多个条件判断符号
     *
     * @param s
     * @return
     */
   static public boolean isValid1(String s) {
        LinkedList<Character> stack = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (c == '[') {
                stack.push(']');
            } else if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (stack.isEmpty() || c != stack.pop()) {
                return false;
            }
        }
        return stack.isEmpty();
    }
    static public boolean isValid2(String s) {
       int len = s.length();
       if((len & 1)==1){
           return false;
       }
       char [] stack = new char[len];
       int index = 0;
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            switch (cur){
                case '(':
                    stack[index++] = ')';
                    break;
                case '[':
                    stack[index++] = ']';
                    break;
                case '{':
                    stack[index++] = '}';
                    break;
                default:
                    if (index > 0 && cur != stack[--index]) {
                        return false;
                    }
            }
        }
        return index == 0;
    }

}
