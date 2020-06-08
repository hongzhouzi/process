package cn.examination.meituan;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
5 5
28 35 38 10 19
4  76 72 38 86
96 80 81 17 10
70 64 86 85 10
1  93 19 34 41
思路：计算每一列最大的数共位于多上行中
需要考虑每行都相等的情况、部分相等的情况、全部等的情况
并列最高时需要将这些都记录下来
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // n行，每行m个代表各个单科成绩
        int n = sc.nextInt(),m = sc.nextInt();
        int data[][] = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                data[i][j] = sc.nextInt();
            }
        }
        Map<Integer,Integer> result = new HashMap<>();
        // 处理数据
        for (int col = 0; col < m; col++) {
            int maxVal = data[0][col];
            int maxValRow[] = new int[n];
            int count = 0;
            for (int row = 1; row < n; row++) {
                // 取该列最大的值，并记录位置所在行号
                if(data[row][col] > maxVal){
                    maxVal = data[row][col];
                    count = 0;
                    maxValRow[count++] = row;
                }else if(data[row][col] == maxVal){
                    // 把相等的都加上
                    // +之前行
                    if(count == 0){
                        maxValRow[count++] = 0;
                    }
                    // +当前行
                    maxValRow[count++] = row;
                }
            }
            for (int i = 0; i < count; i++) {
                result.put(maxValRow[i],maxVal);
            }
        }
        System.out.println(result.size());
    }
}
