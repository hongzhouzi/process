package cn.algorithm.leetcode.weekly._202;

import java.util.Arrays;

/**
 * 1552. 两球之间的磁力
 * 在代号为 C-137 的地球上，Rick 发现如果他将两个球放在他新发明的篮子里，它们之间会形成特殊形式的磁力。
 * Rick 有 n 个空的篮子，第 i 个篮子的位置在 position[i] ，Morty 想把 m 个球放到这些篮子里，使得任意两球间 最小磁力 最大。
 * 已知两个球如果分别位于 x 和 y ，那么它们之间的磁力为 |x - y| 。
 * 给你一个整数数组 position 和一个整数 m ，请你返回最大化的最小磁力。
 * 示例 1：
 * 输入：position = [1,2,3,4,7], m = 3
 * 输出：3
 * 解释：将 3 个球分别放入位于 1，4 和 7 的三个篮子，两球间的磁力分别为 [3, 3, 6]。最小磁力为 3 。我们没办法让最小磁力大于 3 。
 * 示例 2：
 * 输入：position = [5,4,3,2,1,1000000000], m = 2
 * 输出：999999999
 * 解释：我们使用位于 1 和 1000000000 的篮子时最小磁力最大。
 * 提示：
 * n == position.length
 * 2 <= n <= 10^5
 * 1 <= position[i] <= 10^9
 * 所有 position 中的整数 互不相同 。
 * 2 <= m <= position.length
 *
 */
public class M3 {
    public static void main(String[] args) {
       /* int position[] = {1,2,3,4,7};
        int m = 3;*/

        /*int position[] = {5, 4, 3, 2, 1, 1000000000};
        int m = 2;*/

        int position[] = {79,74,57,22};
        int m = 4;
        System.out.println(new M3().maxDistance1(position, m));
        System.out.println(new M3().maxDistance(position, m));

    }

    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int left = 0, right = position[position.length - 1];
        // left == right 时二分区间已经缩小到一个数了，可确定就为该数，可返回了
        while (right - left > 1 ) {
            int mid = (right - left) / 2 + left;
            // 最小值最大：检验的间隔数大于等于预期，说明给定的值小了，需要加大，则左区间加大到中间值
            if(check(position,mid,m)){
                left = mid;
            } else {
                right = mid;
            }
        }
        // 返回left,因为left是验证过的，right是没有通过验证的，两个碰面的就说明范围缩小到left和right，而left验证通过，right验证未通过，故返回left
        return left;
    }

    boolean check(int[] position, int mid, int m) {
        // 因为1 <= position[i] <= 10^9，且根据题意肯定要算入第一个数
        // 所以起点从0开始，在验证时把第一个元素加上，后面依次加上间隔数
        int l = 0, count = 0;
        for (int i = 0; i < position.length; i++) {
            // 验证逻辑：当前数是否大于等于偏移间隔数，这儿需要取到小于号
            if (l <= position[i]) {
                l = mid + position[i];
                count++;
            }
        }
        return count >= m;
    }

    public int maxDistance1(int[] a, int m) {
        Arrays.sort(a);
        int low = 0, high = 1000000007;
        while (high - low > 1) {
            int h = high + low >> 1;
            if (ok(a, h, m)) {
                low = h;
            } else {
                high = h;
            }
        }
        return low;
    }

    boolean ok(int[] a, int h, int m) {
        int r = -1;
        int u = 0;
        for (int v : a) {
            if (r <= v) {
                r = v + h;
                u++;
            }
        }
        return u >= m;
    }

}
