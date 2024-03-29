package cn.algorithm.leetcode;

import java.util.Arrays;

/**
 * 面试题 16.11. 跳水板
 * 你正在使用一堆木板建造跳水板。有两种类型的木板，其中长度较短的木板长度为shorter，
 * 长度较长的木板长度为longer。你必须正好使用k块木板。编写一个方法，生成跳水板所有可能的长度。
 * 返回的长度需要从小到大排列。
 * 示例：
 * 输入：
 * shorter = 1
 * longer = 2
 * k = 3
 * 输出： {3,4,5,6}
 * 提示：
 * 0 < shorter <= longer
 * 0 <= k <= 100000
 *
 * @author hongzhou.wei
 * @date 2020/7/8
 */
public class Interview1611_DivingBoardLCCI {

    public static void main(String[] args) {
        int shorter = 1;
        int longer = 1;
        int k = 100000;
        System.out.println(Arrays.toString(divingBoard(shorter, longer, k)));
    }

    /**
     * 【分析】k块木板总共有k+1总组合，长短木板的关系是：长i，短则k-i
     * 直接遍历组合就OK了
     *
     * @param shorter
     * @param longer
     * @param k
     * @return
     */
    static public int[] divingBoard(int shorter, int longer, int k) {
        // 考虑两种特殊情况，k=0不能建造；shorter=longer只有一种情况
        if (k == 0) {
            return new int[0];
        }
        if (shorter == longer) {
            return new int[]{shorter * k};
        }
        // 其他常规情况，因为长木板和短木板一共用k块，则总共有k+1中情况
        int ret[] = new int[k + 1];
        int c = 0;
        for (int i = 0; i <= k; i++) {
            ret[c++] = longer * i + shorter * (k - i);
        }
        return ret;
    }
}
