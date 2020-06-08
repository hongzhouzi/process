package cn.algorithm.base;

import java.util.HashMap;

/**
 * LRU缓存淘汰策略实现
 *
 * @author weihongzhou
 * @date 2019/8/19
 */

/**
 * 要点：双向链表关联hashMap，每次往有序的链表中添加东西时需要改变hashmap的
 */
public class LRUCache {
    private HashMap<Object, Node> map;
    private DoubleList cache;
    private int cap;//最大容量

    public LRUCache(int cap) {
        this.map = new HashMap<>();
        this.cache = new DoubleList();
        this.cap = cap;
    }

    public Object get(Object key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Object val = map.get(key).val;
        //利用put方法把数据提前
        put(key, val);
        return val;
    }

    public void put(Object key, Object val) {
        //先做新节点
        Node node = new Node(key, val);
        if (map.containsKey(key)) {
            //删除旧的节点，新的插入到头部
            cache.remove(map.get(key));
            cache.addFirst(node);

            //更新map中对应数据
            map.put(key, node);
        } else {
            //空间满了，删除；链表最后一个元素，并在map中消除记录
            if (cap == cache.length()) {
                Node n = cache.removeLast();
                map.remove(n);
            }
            //直接添加到头部
            cache.addFirst(node);
            map.put(key, node);
        }
    }

}

class Node {
    Object key, val;
    Node prev, next;

    public Node(Object key, Object val) {
        this.key = key;
        this.val = val;
    }
}

class DoubleList {
    Node head = null;

    /**
     * 在链表头部添加节点node
     *
     * @param node
     */
    public void addFirst(Node node) {
        if (head == null) {
            head = node;
        } else {
            head.prev = node;
        }
    }

    /**
     * 删除链表中node节点，默认node存在
     *
     * @param node
     */
    public void remove(Node node) {
        Node temp = head;
        //从前往后遍历到node节点前一个停下
        while (temp != null && temp.next != node) {
            temp = temp.next;
        }
        //删除node节点
        temp.next = node.next;
    }

    /**
     * 删除链表中最后一个节点，并返回该节点
     *
     * @return
     */
    public Node removeLast() {
        Node temp = head;
        if (temp == null)
            return null;
        //从前往后遍历到最后一个节点
        while (temp.next != null) {
            temp = temp.next;
        }
        //删除最后一个节点，将最后一个节点的前个节点的next置null
        temp.prev.next = null;
        return temp;
    }

    /**
     * 返回链表的长度
     *
     * @return
     */
    public int length() {
        Node temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }
}
