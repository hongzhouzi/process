package cn.algorithm.leetcode;

/**
 * @author hongzhou.wei
 * @date 2020/10/13
 */
public class P0024_SwapNodesInPairs {

    static class Solution {
        public ListNode swapPairs(ListNode head) {
            ListNode pre = new ListNode(0);
            pre.next = head;
            ListNode temp = pre;
            while(temp.next != null && temp.next.next != null) {
                ListNode start = temp.next;
                ListNode end = temp.next.next;
                temp.next = end;
                start.next = end.next;
                end.next = start;
                temp = start;
            }
            return pre.next;
        }
    }
}
//class ListNode {
//    int      val;
//    ListNode next;
//
//    ListNode() {
//    }
//
//    ListNode(int val) {
//        this.val = val;
//    }
//}
