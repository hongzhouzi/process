package cn.algorithm.base.sort;

import java.util.Arrays;

/**
 * @author: whz
 * @date: 2019/8/4
 **/
public class Test {
    public static void main(String[] args) {
        int[] data={3,6,9,8,5,2,1,4,7};
        for(int i = 1; i <data.length; i++){
            int temp = data[i];
            int j = i;
            if(data[j-1] > temp){
                while (j >= 1 && data[j-1] >temp){
                    data[j] = data[j-1];
                    j--;
                }
                data[j] = temp;
            }
        }

        System.out.println(Arrays.toString(data));
    }
}
