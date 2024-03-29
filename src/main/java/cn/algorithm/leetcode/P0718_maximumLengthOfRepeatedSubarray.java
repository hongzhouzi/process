package cn.algorithm.leetcode;

import java.math.BigInteger;
import java.util.*;

/**
 * 718. 最长重复子数组
 * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
 * 示例 1:
 * 输入:
 * A: [1,2,3,2,1]
 * B: [3,2,1,4,7]
 * 输出: 3
 * 解释:
 * 长度最长的公共子数组是 [3, 2, 1]。
 * 说明:
 * 1 <= len(A), len(B) <= 1000
 * 0 <= A[i], B[i] < 100
 *
 * @author hongzhou.wei
 * @date 2020/7/1
 */
public class P0718_maximumLengthOfRepeatedSubarray {
    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 2, 1};
        int[] b = new int[]{3, 2, 1, 4, 7};
        System.out.println(findLengthDp(a, b));
    }

    /**
     * 滑动窗口：遍历A时，在B中找一样的元素并记录索引，A继续遍历下个元素和B下个索引的数对比是否一致，不一致则更新最大长度
     * 并在B中找到下个一样的元素并记录索引，A继续遍历下个元素并和B中下个索引对比。
     *
     * @param A
     * @param B
     * @return
     */
    /**
     * 暴力破解：直接循环嵌套遍历A和B，遇到相等的情况再同一遍历A和B中的元素
     *
     * @param A
     * @param B
     * @return
     */
    static public int findLength0(int[] A, int[] B) {
        int lenA = A.length, lenB = B.length, maxLen = 0;
        for (int i = 0; i < lenA; i++) {
            for (int j = 0; j < lenB; j++) {
                int k = 0;
                while (i + k < lenA && j + k < lenB && A[i + k] == B[j + k]) {
                    k++;
                }
                maxLen = Math.max(maxLen, k);
            }
        }
        return maxLen;
    }


    static public int findLengthDp(int[] A, int[] B) {
        int lenA = A.length, lenB = B.length, maxLen = 0;
        int[][] dp = new int[lenA + 1][lenB + 1];
        for (int i = lenA - 1; i >= 0; i--) {
            for (int j = lenA - 1; j >= 0; j--) {
                dp[i][j] = A[i] == B[j] ? dp[i + 1][j + 1] + 1 : 0;
                maxLen = Math.max(maxLen, dp[i][j]);
            }
        }
        return maxLen;
    }


    /**
     * 1、Hash表存储B字符的索引，穷举A的字符（时间超限）
     * O(M*N*min(M，N))
     */
    public int findLength1(int[] A, int[] B) {
        Map<Integer, ArrayList<Integer>> startsB = new HashMap<>();
        for (int i = 0; i < B.length; i++) {
            startsB.computeIfAbsent(B[i], v -> new ArrayList<>()).add(i);
        }
        int max = 0;
        for (int i = 0; i < A.length; i++) {
            if (startsB.containsKey(A[i])) {
                for (int j : startsB.get(A[i])) {
                    int k = 0;
                    while (i + k < A.length && j + k < B.length && A[i + k] == B[j + k]) {
                        k++;
                    }
                    max = Math.max(max, k);
                }
            }
        }
        return max;
    }


    /**
     * 2、二分查找 + 简单判断（时间超限）
     * O((M + N) * min(M, N) * log(min(M, N)))。其中 M 和 N 是数组 A 和 B 的长度。
     * 对于 L，简单判断是否有长度为 L 的子数组的时间复杂度为 O((M + N) * L)，二分查找为对数时间复杂度。
     * O(M^2)
     */
    public int findLength2(int[] A, int[] B) {
        int lo = 0;
        int hi = Math.min(A.length, B.length) + 1;
        while (lo < hi) {
            int mi = (lo + hi) >>> 1;
            if (check(mi, A, B)) {
                lo = mi + 1;
            } else {
                hi = mi;
            }
        }
        return lo - 1;
    }

    public boolean check(int subLen, int[] A, int[] B) {
        Set<String> seen = new HashSet<>();
        for (int i = 0; i + subLen <= A.length; i++) {
            seen.add(Arrays.toString(Arrays.copyOfRange(A, i, i + subLen)));
        }
        for (int i = 0; i + subLen <= B.length; i++) {
            if (seen.contains(Arrays.toString(Arrays.copyOfRange(B, i, i + subLen)))) {
                return true;
            }
        }
        return false;
    }


    private static final long  P   = (int) 1e7 + 19;
    private static final int   MOD = (int) 1e9 + 7;
    private              int[] powP;

    /**
     * 3、字符串Hash（时间超限）
     * O(m^2 + n^2)
     */
    public int findLength3(int[] A, int[] B) {
        powP = powP(Math.max(A.length, B.length));
        int[] hashA = getHash(A);
        int[] hashB = getHash(B);
        Map<Integer, Integer> subMapA = getSubMap(hashA);
        Map<Integer, Integer> subMapB = getSubMap(hashB);
        return getMax(subMapA, subMapB);
    }

    private int[] powP(int len) {
        int[] powP = new int[len + 1];
        powP[0] = 1;
        for (int i = 1; i <= len; i++) {
            powP[i] = (int) ((powP[i - 1] * P) % MOD);
        }
        return powP;
    }

    private int[] getHash(int[] nums) {
        int[] hash = new int[nums.length];
        hash[0] = nums[0] + 1;
        for (int i = 1; i < nums.length; i++) {
            hash[i] = (int) ((hash[i - 1] * P + nums[i] + 1) % MOD);
        }
        return hash;
    }

    private Map<Integer, Integer> getSubMap(int[] hash) {
        Map<Integer, Integer> subHash = new HashMap<>((int) ((hash.length * (hash.length + 1)) / 2 / 0.75) + 1);
        for (int i = 0; i < hash.length; i++) {
            for (int j = i; j < hash.length; j++) {
                int hashVal = getSingleSubHash(hash, i, j);
                subHash.put(hashVal, j - i + 1);
            }
        }
        return subHash;
    }

    private int getSingleSubHash(int[] hash, int i, int j) {
        if (i == 0) {
            return hash[j];
        }
        return (int) (((hash[j] - (long) hash[i - 1] * powP[j - i + 1]) % MOD + MOD) % MOD);
    }

    private int getMax(Map<Integer, Integer> subMapA, Map<Integer, Integer> subMapB) {
        final int[] max = {0};
        subMapA.forEach((subHashA, subLenA) -> {
            subMapB.forEach((subHashB, subLenB) -> {
                if (subHashA.equals(subHashB)) {
                    max[0] = Math.max(max[0], subLenB);
                }
            });
        });
        return max[0];
    }


    /**
     * 4、动态规划
     * O(m * n)
     */
    public int findLength4(int[] A, int[] B) {
        int max = 0;
        int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i - 1] == B[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max;
    }


    final int p    = 113;
    final int mod  = 1_000_000_007;
    final int invP = BigInteger.valueOf(p).modInverse(BigInteger.valueOf(mod)).intValue();

    /**
     * 5、二分查找 + 字符串Hash
     * O((M + N) * log(min(M,N)))，检测Hash碰撞需要加 O(min(M, N))，小于 O(M + N)，不影响
     */
    public int findLength5(int[] A, int[] B) {
        int lo = 0;
        int hi = Math.min(A.length, B.length) + 1;
        while (lo < hi) {
            int mi = (lo + hi) >>> 1;
            if (check2(mi, A, B)) {
                lo = mi + 1;
            } else {
                hi = mi;
            }
        }
        return lo - 1;
    }

    private boolean check2(final int guess, int[] A, int[] B) {
        if (guess == 0) {
            return true;
        }

        Map<Integer, List<Integer>> hashes = new HashMap<>(2048);
        int k = 0;
        for (int x : rolling(A, guess)) {
            hashes.computeIfAbsent(x, v -> new ArrayList<>()).add(k++);
        }

        int j = 0;
        for (int x : rolling(B, guess)) {
            for (int i : hashes.getOrDefault(x, new ArrayList<>())) {
                if (Arrays.equals(Arrays.copyOfRange(A, i, i + guess), Arrays.copyOfRange(B, j, j + guess))) {
                    return true;
                }
            }
            j++;
        }
        return false;
    }

    private int[] rolling(int[] source, final int length) {
        int[] ans = new int[source.length - length + 1];
        long h = 0;
        long power = 1;
        for (int i = 0; i < source.length; i++) {
            h = (h + source[i] * power) % mod;
            if (i < length - 1) {
                power = (power * p) % mod;
            } else {
                ans[i - (length - 1)] = (int) h;
                h = (h - source[i - (length - 1)]) * invP % mod;
                if (h < 0) {
                    h += mod;
                }
            }
        }
        return ans;
    }


    /**
     * 滑动窗口
     */
    public int findLength6(int[] A, int[] B) {
        if (A.length > B.length) {
            return findLength(B, A);
        }

        int max = 0;
        int lenA = A.length;
        int lenB = B.length;
        for (int len = 1; len <= lenA; len++) {
            max = Math.max(max, maxLen(A, 0, B, lenB - len, len));
        }
        for (int j = 0; j < lenB - lenA; j++) {
            max = Math.max(max, maxLen(A, 0, B, j, lenA));
        }
        for (int i = 1; i < lenA; i++) {
            max = Math.max(max, maxLen(A, i, B, 0, lenA - i));
        }
        return max;
    }

    private int maxLen(int[] a, int i, int[] b, int j, int len) {
        int count = 0, max = 0;
        for (int k = 0; k < len; k++) {
            if (a[i + k] == b[j + k]) {
                count++;
            } else if (count > 0) {
                max = Math.max(max, count);
                count = 0;
            }
        }
        return count > 0 ? Math.max(max, count) : max;
    }


    public int[] hs1;
    public int[] hs2;
    public int   acc = 0;
    public int[] list1;
    public int[] list2;

    public int findLength(int[] A, int[] B) {
        list1 = A;
        list2 = B;
        int max;
        int maxlength = list1.length;
        if (list1.length > list2.length) {
            maxlength = list2.length;
        }
        max = bigSearch(1, maxlength);
        return max;
    }

    public int bigSearch(int start, int end) {
        if (start == end) {
            if (forSpecificLength(start)) {
                return start;
            } else {
                return 0;
            }
        }
        if (start == end - 1) {
            if (forSpecificLength(end)) {
                return end;
            }
            if (forSpecificLength(start)) {
                return start;
            } else {
                return 0;
            }
        }
        int mid = (start + end) / 2;
        if (forSpecificLength(mid)) {
            return bigSearch(mid, end);
        } else {
            return bigSearch(start, mid - 1);
        }
    }

    public boolean forSpecificLength(int length) {
        hs1 = hashGenerate(length, list1);
        hs2 = hashGenerate(length, list2);
        Arrays.sort(hs1);
        for (int value : hs2) {
            if (smallSearch(hs1, value, 0, hs1.length - 1) >= 0) {
                return true;
            }
        }
        return false;
    }

    public int smallSearch(int[] input, int target, int start, int end) {
        if (start == end) {
            if (input[end] == target) {
                return end;
            } else {
                return -1;
            }
        }

        int mid = (start + end) / 2;
        if (input[mid] == target) {
            return mid;
        }
        if (input[mid] > target) {
            return smallSearch(input, target, start, mid);
        }
        if (input[mid] < target) {
            return smallSearch(input, target, mid + 1, end);
        }
        return 0;
    }

    public int[] hashGenerate(int length, int[] list) {
        //f group
        int[] hs = new int[list.length - length + 1];
        int base = 139;
        //long dvd=9223372036854775783l;
        int temp = 1, temp2;
        for (int i = length - 1; i >= 0; i--) {
            temp = ((temp) * (base));
            if (temp < 0) {
                temp = (Integer.MAX_VALUE + 1 + temp);
            }
            hs[0] = hs[0] + list[i] * temp;
            if (hs[0] < 0) {
                hs[0] = (Integer.MAX_VALUE + 1 + hs[0]);
            }
        }
        for (int i = 1; i < hs.length; i++) {
            temp2 = (hs[i - 1] - (temp * list[i - 1]));
            if (temp2 < 0) {
                temp2 = (Integer.MAX_VALUE + 1 + temp2);
            }
            temp2 = temp2 * base;
            if (temp2 < 0) {
                temp2 = (Integer.MAX_VALUE + 1 + temp2);
            }
            temp2 = (temp2 + list[i + length - 1] * base);
            if (temp2 < 0) {
                temp2 = (Integer.MAX_VALUE + 1 + temp2);
            }
            hs[i] = temp2;
        }
        return hs;
    }
}
