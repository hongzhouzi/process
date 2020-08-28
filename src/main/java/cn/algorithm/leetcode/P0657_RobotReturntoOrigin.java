package cn.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hongzhou.wei
 * @date 2020/8/28
 */
public class P0657_RobotReturntoOrigin {
    public static void main(String[] args) {
        String s = "LL";
        System.out.println(judgeCircle(s));
        System.out.println(judgeCircle1(s));
    }

    /**
     * 【运行时间最短】
     *
     * @param moves
     * @return
     */
    static public boolean judgeCircle2(String moves) {
        int[] letters = new int[26 + 'A'];
        for (char c : moves.toCharArray()) {
            letters[c]++;
        }
        return letters['U'] == letters['D'] && letters['L'] == letters['R'];
    }


    static public boolean judgeCircle1(String moves) {
        int vertical = 0, level = 0;
        for (int i = 0; i < moves.length(); i++) {
            switch (moves.charAt(i)) {
                case 'U':
                    vertical++;
                    break;
                case 'D':
                    vertical--;
                    break;
                case 'L':
                    level++;
                    break;
                case 'R':
                    level--;
                    break;
                default:
            }
        }
        return vertical == 0 && level == 0;
    }



    static public boolean judgeCircle(String moves) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < moves.length(); i++) {
            switch (moves.charAt(i)) {
                case 'U':
                    map.put('U', map.getOrDefault('U', 0) + 1);
                    break;
                case 'D':
                    map.put('D', map.getOrDefault('D', 0) + 1);
                    break;
                case 'L':
                    map.put('L', map.getOrDefault('L', 0) + 1);
                    break;
                case 'R':
                    map.put('R', map.getOrDefault('R', 0) + 1);
                    break;
                default:
            }
        }
        return map.getOrDefault('U', 0).equals(map.getOrDefault('D', 0))
            && map.getOrDefault('L', 0).equals(map.getOrDefault('R', 0));
    }


}
