package cn.algorithm.leetcode.weekly._212;

/**
 * @author hongzhou.wei
 * @date 2020/10/25
 */
public class M1 {
    public static void main(String[] args) {
//        int [] releaseTimes = {12,23,36,46,62} ;String keysPressed = "spuda";
        int [] releaseTimes = {9,29,49,50} ;String keysPressed = "cbcd";
        System.out.println(new M1.Solution().slowestKey(releaseTimes, keysPressed));
    }

   static class Solution {
        public char slowestKey(int[] releaseTimes, String keysPressed) {
            int max= releaseTimes[0], index = 0;
            for (int i = 1; i < releaseTimes.length; i++) {
                int val = releaseTimes[i] - releaseTimes[i-1];
                if(val > max || (val == max && keysPressed.charAt(i) > keysPressed.charAt(index))){
                    index = i;
                    max = val;
                }
            }
            return keysPressed.charAt(index);
        }
    }
}
