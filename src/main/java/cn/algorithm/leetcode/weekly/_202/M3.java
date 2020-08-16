package cn.algorithm.leetcode.weekly._202;

import java.util.Arrays;

public class M3 {
    public static void main(String[] args) {
       /* int position[] = {1,2,3,4,7};
        int m = 3;*/

        int position[] = {5, 4, 3, 2, 1, 1000000000};
        int m = 2;

        System.out.println(new M3().maxDistance1(position, m));
        System.out.println(new M3().maxDistance(position, m));

    }

    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int left = 0, right = position[position.length - 1];
        while (right > left + 1 ) {
            int mid = (right - left) / 2 + left;
            // 最小值最大：检验的间隔数大于等于预期，说明给定的值小了，需要加大，则左区间加大到中间值
            if(check(position,mid,m)){
                left = mid;
            } else {
                right = mid;
            }
        }
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
