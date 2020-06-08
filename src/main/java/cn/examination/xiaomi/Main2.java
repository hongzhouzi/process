package cn.examination.xiaomi;

import java.util.Scanner;

/**
 * 2048》是一款热门的数字游戏。游戏中，每个方块上的数字都有2的幂，数字方块会根据指令整体进行上下左右移动，如果两个数字相同的方块在移动中碰撞，他们就会合成一个新的方块。例如下图为4*4格子的游戏，0表示格子为空，图a为移动前格子中的数字，图b为图a左移后的结果:
 *
 * 0 0 2 4
 * 0 2 2 2
 * 0 4 2 2
 * 8 8 2 2
 *    图a
 * 2 4 0 0
 * 4 2 0 0
 * 4 4 0 0
 * 16 4 0 0
 *    图b
 * 问，给定n*m的矩阵M，0表示空格子，非0整数表示待移动的数字，按照2048的移动规则，输出进行左移操作后的矩阵结果。
 input:

 4
 0 0 2 4
 0 2 2 2
 0 4 2 2
 8 8 2 2


 */
public class Main2 {


        /*请完成下面这个函数，实现题目要求的功能
        当然，你也可以不按照下面这个模板来作答，完全按照自己的想法来 ^-^
        ******************************开始写代码******************************/
        static String solution(String[] input) {
            StringBuffer out = new StringBuffer();
            for(int i=0; i< input.length; i++){
                String[] cur = input[i].split(" ");
                for(int j=1; j<cur.length;){
                    if( cur[j].equals(cur[j-1]) && !cur[j-1].equals("0")){
                        cur[j-1] = String.valueOf(Integer.valueOf(cur[j])*Integer.valueOf(cur[j]));
                        cur[j] = "0";
                        System.out.println(cur[j-1]+ " " +cur[j]);
                    }else {
                        j++;
                    }
                }
                out.append(input[i]+"\n");
            }
            return out.toString();
        }
        /******************************结束写代码******************************/


        public static void main(String[] args){
            Scanner in = new Scanner(System.in);
            String res;

            int _input_size = 0;
            _input_size = Integer.parseInt(in.nextLine().trim());
            String[] _input = new String[_input_size];
            String _input_item;
            for(int _input_i = 0; _input_i < _input_size; _input_i++) {
                try {
                    _input_item = in.nextLine();
                } catch (Exception e) {
                    _input_item = null;
                }
                _input[_input_i] = _input_item;
            }

            res = solution(_input);
            System.out.println(res);
        }

}
