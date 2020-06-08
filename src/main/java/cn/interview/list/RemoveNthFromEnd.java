package cn.interview.list;

/**
 * @author: whz
 * @date: 2019/8/4
 **/
public class RemoveNthFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null)
            return null;
        ListNode front = head, after = head, h = head;
        //记录链表长度，判断删的是否是头结点时用
        int size = 1;
        //双指针，前指针指到末尾时后指针处就准备删除（这儿指向后指针前一个节点）
        for(; front.next != null; size++){
            if(size <= n){
                front = front.next;
            }else {
                front = front.next;
                after = after.next;
            }
        }
        //情况1：删头结点（考虑到只有一个结点的情况，删头结点要放在前面）
        if(size == n){
            return h.next;
        }
        /*
        //把删尾结点的情况单独提出来，可以提高效率。
        if(1 == n){
            after.next = null;
            return h;
        }*/
        //情况2：删中间指定结点+尾
        if(after.next != null && after.next.next != null)
            after.next = after.next.next;
        return h;
    }



    public ListNode removeNthFromEnd1(ListNode head, int n) {
        //整个链表接在准备返回的临时变量后面，返回时就返回.next，加长了一点之后就避免了讨论链表头中尾
        ListNode header = new ListNode(-1);
        header.next = head;
        ListNode p = header;
        ListNode q = header;
        for (int i = 0; i < n; i++) {
            p = p.next;
        }
        while (p.next != null) {
            q = q.next;
            p = p.next;
        }
        q.next = q.next.next;
        return header.next;
    }
}
