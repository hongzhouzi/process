package cn.algorithm.leetcode;

import java.util.Arrays;

/**
 * 如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为 'X'。
 * 如果一个没有相邻地雷的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的未挖出方块都应该被递归地揭露。
 * 如果一个至少与一个地雷相邻的空方块（'E'）被挖出，修改它为数字（'1'到'8'），表示相邻地雷的数量。
 * 如果在此次点击中，若无更多方块可被揭露，则返回面板。
 *
 * @author hongzhou.wei
 * @date 2020/8/20
 */
public class P0529Minesweeper {
    public static void main(String[] args) {
        char[][] board = {
            {'E', 'E', 'E', 'E', 'E'},
            {'E', 'E', 'M', 'E', 'E'},
            {'E', 'E', 'E', 'E', 'E'},
            {'E', 'E', 'E', 'E', 'E'}};
        int click[] = {3, 0};
        System.out.println(Arrays.deepToString(updateBoard(board, click)));
    }

    /**
     * 定义8个方向
     */
    static int[] dx = {0, 0, 1, -1, 1, 1, -1, -1};
    static int[] dy = {1, -1, 0, 0, 1, -1, -1, 1};
    static public char[][] updateBoard(char[][] board, int[] click) {
        // 1.如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为 'X'。
        if(board[click[0]][click[1]] == 'M'){
            board[click[0]][click[1]] = 'X';
            return board;
        }else {
            dfs(board,click[0],click[1]);
        }
        return board;
    }

    static void dfs(char[][] board, int x, int y){
        // 2.如果一个没有相邻地雷的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的未挖出方块都应该被递归地揭露。
        // 3.如果一个至少与一个地雷相邻的空方块（'E'）被挖出，修改它为数字（'1'到'8'），表示相邻地雷的数量。
        // 4.如果在此次点击中，若无更多方块可被揭露，则返回面板。
        // 递归终止条件：周围有雷修改位置的雷数，终止对该路径的搜索；该位置已经遍历过，字符为B或数字

       /* if(board[x][y] == 'B' || (board[x][y]>1+'0' && board[x][y] <= '0'+9)){
            return;
        }*/
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int ix = x + dx[i];
            int iy = y + dy[i];
            if(ix < 0 || ix >= board.length || iy < 0 || iy >= board[0].length ){
                continue;
            }
            if(board[ix][iy] == 'M'){
                count++;
            }
        }
        // 填充数字
        if(count > 0){
            board[x][y] = (char) (count + '0');
            return;
        }
        // 若周围没有雷，置为B并向四周遍历
        board[x][y] = 'B';
        for (int i = 0; i < 8; i++) {
            int ix = x + dx[i];
            int iy = y + dy[i];
            if(ix < 0 || ix >= board.length || iy < 0 || iy >= board[0].length || board[ix][iy]!='E'){
                continue;
            }
            dfs(board,ix,iy);
        }
    }
}
