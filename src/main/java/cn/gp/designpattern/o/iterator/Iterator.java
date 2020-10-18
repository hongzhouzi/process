package cn.gp.designpattern.o.iterator;

/**
 * 抽象迭代器
 *
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public interface Iterator<E> {
    E next();
    boolean hasNext();
}
