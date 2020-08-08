package cn.algorithm.leetcode.weekly._199;

/**
 * @author hongzhou.wei
 * @date 2020/7/26
 */
public class P5473_BulbSwitcherIV {
    public static void main(String[] args) {
    String target = "001011101";
        System.out.println(minFlips(target) );
    }

    /**
     * 从后往前依次翻转成与前面数保持一致
     *
     * @param target
     * @return
     */
    static public int minFlips(String target) {
        int count = 0;
        for (int i = target.length() - 1; i >= 0; i--) {
            if (i == 0 ) {
                if(target.charAt(i) == '1') {
                    count++;
                }
                break;
            }
            if (target.charAt(i) != target.charAt(i - 1)) {
                count++;
            }
        }
        return count;
    }
}
