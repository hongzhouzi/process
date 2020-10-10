package cn.algorithm.leetcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hongzhou.wei
 * @date 2020/10/10
 */
public class P0142_LinkedListCycleII {

    /*
    设链表共有 a+b 个节点，其中a：链表头部到链表入口（不计链表入口节点），b：链表环
    1、第一次相遇时慢指针已经走了nb步
    2、走a+nb步一定是在环入口
     */
    public ListNode detectCycle2(ListNode head) {
        ListNode slow = head, fast = head;
        while (true) {
            if (fast == null || fast.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        slow = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }


    public ListNode detectCycle1(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode node = head;
        while (node != null) {
            if (!set.add(node)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }
}
