package cn.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongzhou.wei
 * @date 2020/8/22
 */
public class P0679_24Game {
    public static void main(String[] args) {
        int [] a = {4, 1, 8, 7};
        System.out.println(new P0679_24Game().judgePoint24(a));
    }

    static final int TARGET = 24;
    static final double EPSILON = 1e-6;
    static final int ADD = 0, MULTIPLY = 1, SUBTRACT = 2, DIVIDE = 3;

    public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for (int num : nums) {
            list.add((double) num);
        }
        return solve(list);
    }

    public boolean solve(List<Double> list) {
        if (list.size() == 0) {
            return false;
        }
        if (list.size() == 1) {
            return Math.abs(list.get(0) - TARGET) < EPSILON;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    List<Double> list2 = new ArrayList<Double>();
                    for (int k = 0; k < size; k++) {
                        if (k != i && k != j) {
                            list2.add(list.get(k));
                        }
                    }
                    for (int k = 0; k < 4; k++) {
                        if (k < 2 && i > j) {
                            continue;
                        }
                        if (k == ADD) {
                            list2.add(list.get(i) + list.get(j));
                        } else if (k == MULTIPLY) {
                            list2.add(list.get(i) * list.get(j));
                        } else if (k == SUBTRACT) {
                            list2.add(list.get(i) - list.get(j));
                        } else if (k == DIVIDE) {
                            if (Math.abs(list.get(j)) < EPSILON) {
                                continue;
                            } else {
                                list2.add(list.get(i) / list.get(j));
                            }
                        }
                        if (solve(list2)) {
                            return true;
                        }
                        list2.remove(list2.size() - 1);
                    }
                }
            }
        }
        return false;
    }

    // =============================================================================
    public boolean _judgePoint24(int[] nums) {
        double a = nums[0]; double b = nums[1];
        double c = nums[2]; double d = nums[3];
        return judge(a, b, c, d);
    }

    public boolean judge(double a, double b, double c, double d){
        return
            // a b
            judge(a + b, c, d) ||
                judge(a * b, c, d) ||
                judge(a - b, c, d) ||
                judge(b - a, c, d) ||
                judge(a / b, c, d) ||
                judge(b / a, c, d) ||
                // b c
                judge(c + b, a, d) ||
                judge(c * b, a, d) ||
                judge(c - b, a, d) ||
                judge(b - c, a, d) ||
                judge(c / b, a, d) ||
                judge(b / c, a, d) ||
                // c d
                judge(c + d, a, b) ||
                judge(c * d, a, b) ||
                judge(c - d, a, b) ||
                judge(d - c, a, b) ||
                judge(c / d, a, b) ||
                judge(d / c, a, b) ||
                // b d
                judge(b + d, a, c) ||
                judge(b * d, a, c) ||
                judge(b - d, a, c) ||
                judge(d - b, a, c) ||
                judge(b / d, a, c) ||
                judge(d / b, a, c) ||
                // a c
                judge(a + c, b, d) ||
                judge(a * c, b, d) ||
                judge(a - c, b, d) ||
                judge(c - a, b, d) ||
                judge(a / c, b, d) ||
                judge(c / a, b, d) ||
                // a d
                judge(a + d, b, c) ||
                judge(a * d, b, c) ||
                judge(a - d, b, c) ||
                judge(d - a, b, c) ||
                judge(a / d, b, c) ||
                judge(d / a, b, c) ;

    }
    public boolean judge(double a, double b, double c){// 24 , 3 , 8
        return
            // a b
            judge(a + b, c) ||
                judge(a * b, c) ||
                judge(a - b, c) ||
                judge(b - a, c) ||
                judge(a / b, c) ||
                judge(b / a, c) ||
                // b c
                judge(c + b, a) ||
                judge(c * b, a) ||
                judge(c - b, a) ||
                judge(b - c, a) ||
                judge(c / b, a) ||
                judge(b / c, a) ||
                // a c
                judge(c + a, b) ||
                judge(c * a, b) ||
                judge(c - a, b) ||
                judge(a - c, b) ||
                judge(c / a, b) ||
                judge(a / c, b);
    }

    public boolean judge(double a, double b){
        return
            //注意精度
            Math.abs(a + b - 24) < 0.001 ||
                Math.abs(a - b - 24) < 0.001 ||
                Math.abs(b - a - 24) < 0.001 ||
                Math.abs(a * b - 24) < 0.001 ||
                Math.abs(a / b - 24) < 0.001 ||
                Math.abs(b / a - 24) < 0.001;
    }
}
