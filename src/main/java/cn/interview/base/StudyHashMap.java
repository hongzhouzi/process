/*
package cn.interview.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class StudyHashMap {
    public static void main(String[] args) {
        HashMap map = new HashMap(5);
        map.put("A", "A");
        map.get("A");
//        map.remove()
//        Collections.synchronizedMap();
//        Collection
    }

    void transfer(Entry[] newTable, boolean rehash) {
        int newCapacity = newTable.length;
        for (Entry<K, V> e : table) {
            while (null != e) {
                // 记录当前节点的下个节点
                Entry<K, V> next = e.next;
                if (rehash) {
                    e.hash = null == e.key ? 0 : hash(e.key);
                }
                // 计算扩容后对应的数组下标
                int i = indexFor(e.hash, newCapacity);
                //！！！A线程若执行到这儿挂起，B线程开始进行扩容会导致链表成环
                // 将该数组下标的第一个元素放在当前节点后面
                e.next = newTable[i];
                // 当前节点放入对应数组下标，作为第一个元素
                newTable[i] = e;
                // 移向下一个节点
                e = next;
            }
        }
    }

    static final int hash(Object key) {
        int h;
        // key允许为空，对应hash值为0
        // 调用本地的hashCode()，返回的是32位int值
        */
/*
        (h = key.hashCode()) ^ (h >>> 16)作用：
        在计算元素落在数组中的位置时(n - 1) & hash
        一般只用到了32位hash值的低位，而高位的信息没有用上
        而将高16位与低16位进行按位异或运算后能将高位的信息
        也保留部分，这样能加大低位的随机性
         *//*

        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        HashMap.Node<K, V>[] tab;
        HashMap.Node<K, V> p;
        int n, i;
        // 判断数组是否为空，为空则初始化
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        // 计算存放在数组中的下标，并判断当前下标是否存在数据
        if ((p = tab[i = (n - 1) & hash]) == null)
            // 将当前键值对构造成Node结点放进去
            tab[i] = newNode(hash, key, value, null);
        else {
            HashMap.Node<K, V> e;
            K k;
            // 判断key是否相等，相等则准备覆盖旧值
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
                // 判断该位置存的结点是否为树型节点
            else if (p instanceof HashMap.TreeNode)
                // 将当前键值对构造成树型结点
                e = ((HashMap.TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            else {
                // 遍历链表，binCount用来记录链表长度
                for (int binCount = 0; ; ++binCount) {
                    // 到链表尾部，插入新结点
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        // 若链表长度大于等于8，则将链表转为树型结构
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    // 若与链表中某个元素相等，则准备覆盖旧值
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                // 判断是否允许覆盖旧值，若允许则覆盖
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        // 当前结点是否大于阈值，大于则扩容
        if (++size > threshold)
            resize();
        // 为继承HashMap的LinkedHashMap类服务的
        afterNodeInsertion(evict);
        return null;
    }

    final HashMap.Node<K,V> getNode(int hash, Object key) {
        HashMap.Node<K,V>[] tab; HashMap.Node<K,V> first, e; int n; K k;
        // 判断数组、key对应数组下标是否为空
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            // 检查第一个元素
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            // 检查下个节点
            if ((e = first.next) != null) {
                // 判断第一个节点是否是树型节点
                if (first instanceof HashMap.TreeNode)
                    // 从树中查询
                    return ((HashMap.TreeNode<K,V>)first).getTreeNode(hash, key);
                // 从链表中查询
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
}
*/
