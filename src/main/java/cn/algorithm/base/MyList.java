package cn.algorithm.base;

/**
 * 单链表相关操作
 *
 * @author hongzhou.wei
 * @date 2020/5/4
 */
public class MyList {
    static void reverseList(MyNode head) {
        //1.反转
        MyNode pReverseHead = head;
        MyNode pNode = head;
        MyNode pPrev = null;
        while (pNode != null) {
            MyNode pNext = pNode.next;
            if (pNext == null) {
                pReverseHead = pNext;
            }
            pNode.next = pPrev;
            pPrev = pNode;
            pNode = pNext;
        }
    }

    // 头插法
    static MyNode reverseListByHeadInsert(MyNode head) {
        MyNode newHead = null;
        MyNode node = null;
        while (head != null) {
            // 1.之前链表做头删
            node = head;
            head = head.next;
            // 2.新链表做头插
            node.next = newHead;
            newHead = node;
        }
        return newHead;
    }


}

class MyNode {
    int data;
    MyNode next;
}
