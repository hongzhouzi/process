package cn.algorithm.leetcode;

import java.rmi.ServerException;

/**
 * @author hongzhou.wei
 * @date 2020/8/18
 */
public class P0109_ConvertSortedListtoBinarySearchTree {
    public static void main(String[] args) {

    }

    /**
     * 由双指针找到链表中间的数，然后把左边的给左子树，右边的给右子树
     * 时间复杂度：O(n logn)，其中 n 是链表的长度。设长度为 n 的链表构造二叉搜索树的时间为T(n)，递推式为 T(n)=2⋅T(n/2)+O(n)，根据主定理，T(n)=O(n logn)。
     * 空间复杂度：O(log n)，这里只计算除了返回答案之外的空间。平衡二叉树的高度为 O(logn)，即为递归过程中栈的最大深度，也就是需要的空间。
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        if(head == null){
            return null;
        }
        if(head.next == null){
            return new TreeNode(head.val);
        }
        // 双指针找到链表中间节点（慢指针走一步快指针走两步，快指针到末尾时结束循环）
        ListNode slow = head, quick = head, pre = null;
        while (quick != null && quick.next != null){
            // 这句要放在slow移动前面，记录上次的slow位置，不包含此次的，因为截取的是此次之前的slow
            pre = slow;
            slow = slow.next;
            quick = quick.next.next;
        }
        // 把head从中间截断，操作head指向的地址
        pre.next = null;

        // 此时head为原始的前半段，slow为中间部分
        TreeNode root = new TreeNode(slow.val);
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(slow.next);
        return root;
    }


    // ===============================分治+中序遍历==========================
    ListNode globalHead ;
    public TreeNode sortedListToBST1(ListNode head) {
        globalHead = head;
        int length = getLength(head);
        return buildTree(0, length - 1);
    }
    int getLength(ListNode head){
        int i = 0;
        while (head != null){
            i++;
            head = head.next;
        }
        return i;
    }
    TreeNode buildTree(int left, int right){
        if(left >= right){
            return null;
        }
        int mid =  (right + left+1) / 2;
        TreeNode root = new TreeNode();
        // 左
        root.left = buildTree(left, mid-1);
        // 中
        root.val = globalHead.val;
        // 链表后移
        globalHead = globalHead.next;
        // 右
        root.right = buildTree(mid+1, right);
        return root;
    }




    class TreeNode {
        int      val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class ListNode {
        int      val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}
