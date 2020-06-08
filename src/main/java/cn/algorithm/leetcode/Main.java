package cn.algorithm.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        /*int[] a = {2, 3, 3};
        int[] b = {2, 2, 1, 1, 3, 2};
        System.out.println(majorityElement(a));
        System.out.println(majorityElement(b));*/

        /*ListNode l1 = new ListNode(2);
        l1 = l1.next;l1.val=4;l1=l1.next;l1.val=3;

        ListNode l2 = new ListNode(5);
        l2 = l2.next;l2.val=6;l2=l2.next;l2.val=4;
        ListNode l = new Main().addTwoNumbers(l1,l2);
        while (l.next != null){
            System.out.println(l.val);
        }*/
        int[] a = {1, 1, 2,3,4};
        System.out.println(Arrays.toString(rearrangeBarcodes(a)));
    }

    public static int[] rearrangeBarcodes(int[] barcodes) {
        /*int right = 0, legt = barcodes.length - 1;
        while (right < legt) {
            // 交换
            barcodes[right] ^= barcodes[legt];
            barcodes[legt] ^= barcodes[right];
            barcodes[right] ^= barcodes[legt];
            right = right + 2;
            legt = legt - 2;
        }
        return barcodes;*/

        List<Integer> list = new LinkedList<>();
        for(int i=0; i<barcodes.length/2; i++){
            list.add(barcodes[i]);
            list.add(barcodes[barcodes.length/2+i]);
        }
        if(barcodes.length%2==1){
            list.add(barcodes[barcodes[barcodes.length/2+1]]);
        }
        int result[] = new int[list.size()];
        for(int i=0; i<list.size(); i++){
            result[i] = list.get(i);
        }
        return result;
    }

    public static int majorityElement(int[] nums) {
        // 从开始遇到相同的就+1，不同的就-1，为0了就从当前开始取
        int res = nums[0], count = 0;
        for (int i : nums) {
            if (i == res) {
                count++;
            } else {
                count--;
                if (count == 0) {
                    count = 1;
                    res = i;
                }
            }
        }
        return res;
        // 排序后返回中间的数
        /*Arrays.sort(nums);
        return nums[nums.length / 2];*/
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode cursor = root;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int l1Val = l1 != null ? l1.val : 0;
            int l2Val = l2 != null ? l2.val : 0;
            int sumVal = l1Val + l2Val + carry;
            carry = sumVal / 10;

            ListNode sumNode = new ListNode(sumVal % 10);
            cursor.next = sumNode;
            cursor = sumNode;

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        return root.next;
        /*// 初始化头结点
        int val = l1.val + l2.val;
        ListNode res = new ListNode(val % 10);
        // 相加过程
        while (l1.next != null && l2.next != null) {
            l1 = l1.next;
            l2 = l2.next;
            res = res.next;
            if (val / 10 > 0) {
                val = l1.val + l2.val + val / 10;
                res.val = val;
            } else {
                val = l1.val + l2.val;
                res.val = val;
            }
        }
        // 位数不一样时
        while (l1.next != null){
            res = res.next;
            if (val / 10 > 0) {
                val = l1.val + val / 10;
                res.val = val;
            } else {
                val = l1.val ;
                res.val = val;
            }
        }
        while (l2.next != null){
            res = res.next;
            if (val / 10 > 0) {
                val = l2.val + val / 10;
                res.val = val;
            } else {
                val = l2.val ;
                res.val = val;
            }
        }

        return res;*/
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
