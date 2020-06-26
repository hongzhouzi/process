package cn.algorithm.leetcode;

import cn.examination.pass58.Solution;

import java.util.HashSet;
import java.util.Set;

/**
 * 编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
 * <p>
 * 示例1:
 * 输入：[1, 2, 3, 3, 2, 1]
 * 输出：[1, 2, 3]
 * 示例2:
 * 输入：[1, 1, 1, 1, 2]
 * 输出：[1, 2]
 * 提示：
 * 链表长度在[0, 20000]范围内。
 * 链表元素在[0, 20000]范围内。
 * 进阶：
 * 如果不得使用临时缓冲区，该怎么解决？
 *
 * @author hongzhou.wei
 * @date 2020/6/26
 */
public class Interview0201_removeDuplicateNodeLcci {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode h = head;
        head.next = new ListNode(2);
        head = head.next;
        head.next = new ListNode(3);
        head = head.next;
        head.next = new ListNode(3);
        head = head.next;
        head.next = new ListNode(2);
        head = head.next;
        head.next = new ListNode(1);
        head = head.next;
        ListNode node = removeDuplicateNodes1(h);
        System.out.println(node);

    }

    static public ListNode removeDuplicateNodes(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode cur = head;
        Set<Integer> set = new HashSet<Integer>() {{
            add(head.val);
        }};
        while (cur.next != null) {
            // 将值添加到集合中，如果集合中已存在该值就跳过下个节点
            // 这样可以不用set.contains()，若集合中已存在则会添加失败返回false
            if (set.add(cur.next.val)) {
                cur = cur.next;
            } else {
                cur.next = cur.next.next;
            }
        }
        return head;
    }

    static public ListNode removeDuplicateNodes1(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            ListNode curBefore = cur;
            while (curBefore.next != null) {
                // 当前值==当前后面的值，就将后面重复的值跳过
                if (cur.val == curBefore.next.val) {
                    curBefore.next = curBefore.next.next;
                } else {
                    curBefore = curBefore.next;
                }
            }
            cur = cur.next;
        }
        return head;
    }

}
