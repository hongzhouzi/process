package cn.algorithm.base.problems;

import java.util.Calendar;

/**
 * @author hongzhou.wei
 * @date 2020/9/27
 */
public class FindSecondMin {
    public static void main(String[] args) {
        int[] data = {1,2};
//        int[] data = {1, 2, 3, 4, 5};
//        int[] data = {5, 4, 3, 2, 1};
//        Calendar.getInstance()
        System.out.println(findSecondMin(data));
    }

    static int findSecondMin(int [] data){
        if(data.length < 2) {
            throw new NegativeArraySizeException("数组长度必须大于等于2");
        }
        int min = Math.min(data[0],data[1]), secondMin = Math.max(data[0],data[1]);
        for (int i = 2; i < data.length; i++) {
            if(data[i] < secondMin){
                if(data[i] < min){
                    secondMin = min;
                    min = data[i];
                }else {
                    min = secondMin;
                    secondMin = data[i];
                }
            }
        }
        return secondMin;
    }
}
