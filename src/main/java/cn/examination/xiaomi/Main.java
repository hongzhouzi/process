package cn.examination.xiaomi;

import java.util.*;

/**
 * 目描述：
 * 给定一个数列，每一次移动可以将数列某个数移动到某个位置上，移动多次后，形成新的数列。定义数列中相邻两两之间的差的绝对值为“移动距离”，定义所有移动距离的总和为“总移动距离”。希望计算出最少的移动次数，使得新数列的“总移动距离”最小。 例如原数列为[4,2,7,6]，总移动距离为2+5+1=8。将6移动到7之前，会变成[4,2,6,7]，总移动距离变成2+4+1=7。
 *
 * 需要编写一个函数，输入为一个int数组表示数列内容，输出为一个int数字，表示最小移动次数
 */
public class Main {


    /*请完成下面这个函数，实现题目要求的功能
    当然，你也可以不按照下面这个模板来作答，完全按照自己的想法来 ^-^
    ******************************开始写代码******************************/
    static int solution(int[] arr) {
        int count = 0;
        int len = arr.length;
        for(int i=0; i<len; i++){
            int minPos = i;
            for(int j=i; j<len; j++){
                if(arr[i] > arr[j]){
                    minPos = j;
                }
            }
            if(minPos != i){
                count += minPos-i;
                System.out.println("i:"+i+" minPos:"+minPos);
            }
        }

        return count;
    }
    /******************************结束写代码******************************/


    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int res;

        int _arr_size = 0;
        _arr_size = Integer.parseInt(in.nextLine().trim());
        int[] _arr = new int[_arr_size];
        int _arr_item;
        for(int _arr_i = 0; _arr_i < _arr_size; _arr_i++) {
            _arr_item = Integer.parseInt(in.nextLine().trim());
            _arr[_arr_i] = _arr_item;
        }

        res = solution(_arr);
        System.out.println(String.valueOf(res));

    }
}

