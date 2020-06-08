package cn.interview.list;

public class ReverseLinkList {
    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        a.next = b;
        b.next = c;

        System.out.println("a:"+a);
        System.out.println("b:"+b);
        System.out.println("c:"+c);
        System.out.println("逆序前a:"+a.next);
        System.out.println("逆序前b:"+b.next);
        System.out.println("逆序前c:"+c.next);
        re(a);
        System.out.println("逆序后a:"+a.next);
        System.out.println("逆序后b:"+b.next);
        System.out.println("逆序后c:"+c.next);
    }
    static Node re(Node head){
        Node reHead = head;
        Node pNode = head;
        Node pPre = null;
        while (pNode != null){
            Node nextNode = pNode.next;
            //到链表尾部时
            if(nextNode == null){
                reHead = pNode;
            }
            pNode.next = pPre;//指向前一个结点
            pPre = pNode;//记录当前结点，让后结点指
            pNode = nextNode;//移向下一个结点
        }
        return reHead;
    }
}
class Node{
    int data;
    Node next;
    Node(int d){
        data = d;
    }
}
