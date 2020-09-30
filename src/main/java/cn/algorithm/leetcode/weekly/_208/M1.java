package cn.algorithm.leetcode.weekly._208;

/**
 * @author hongzhou.wei
 * @date 2020/9/27
 */
public class M1 {
    public static void main(String[] args) {
//        String[] logs = {"./","wz4/","../","mj2/","../","../","ik0/","il7/"};
        String[] logs = {"d1/", "../", "../", "../"};
//        String[] logs = {"d1/","d2/","./","d3/","../","d31/"};
        System.out.println(new M1.Solution().minOperations(logs));
    }

    // 最开始没有把倒退到<0的情况考虑清楚导致了错误提交，另外最开始还想复杂了，想用map来存字符串的各种情况和对应计数的值
    // 思考的时候尽量往简单的地方想，局部细节的地方懒加载，写到哪个地方再思考具体的地方

    static class Solution {
        final String BACK = "../";
        final String CUR  = "./";

        public int minOperations(String[] logs) {
            int count = 0;
            for (int i = 0; i < logs.length; i++) {
                if (BACK.equals(logs[i])) {
                    count--;
                } else if (!CUR.equals(logs[i])) {
                    count++;
                }
                // 最开始没把这儿考虑到，导致了错误提交
                if (count < 0) {
                    count = 0;
                }
            }
            return count;
        }
    }
}
