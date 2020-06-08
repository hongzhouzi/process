package cn.interview.list;

import java.util.*;

/**
 * @author: whz
 * @date: 2019/8/3
 **/
public class CircleLinkedList {
    public static void main(String[] args) {
        List<Integer> a =new LinkedList<Integer>();
        a.add(1);
        a.add(2);
        a.add(3);
        for(Integer i: a){
            System.out.println(i);
            System.out.println(i);
        }
    }

    boolean isLoop(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        if(fast == null){
            return false;
        }
        while (fast != null && fast.next != null){
            //快指针一次走两步，慢指针一次走一步
            fast = fast.next.next;
            slow = slow.next;
            //快指针和慢指针相遇了，那么就说明有环
            if(fast == slow){
                return true;
            }
        }
        //两个指针没有相遇，就返回（false）综合上面的循环条件来做返回值
        return !(fast == null || fast.next == null);
    }

    ListNode findLoopPort(ListNode head){
        if(head == null || head.next == null)
            return null;
        ListNode fast = head;
        ListNode slow = head;
        //优化---------------------------
        /*slow = slow.next;
        fast = fast.next.next;
        while (fast != slow){
            if(fast.next == null || fast.next.next == null)
                return null;
            slow = slow.next;
            fast = fast.next.next;
        }*/

        while (fast != null && fast.next != null){
            //快指针一次走两步，慢指针一次走一步
            fast = fast.next.next;
            slow = slow.next;
            //快指针和慢指针相遇了，那么就说明有环
            if(fast == slow){
                break;
            }
        }
        if(fast == null || fast.next == null){
            return null;
        }
        slow = head;
        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    ListNode findLoopPortByHash(ListNode node){
        Set<ListNode> vis = new HashSet<ListNode>();
        ListNode head = node;
        while (head != null){
            if(vis.contains(head)){
                return head;
            }
            vis.add(head);
            head = head.next;
        }
        return null;
    }
}
