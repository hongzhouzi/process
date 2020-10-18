package cn.gp.designpattern.o.iterator;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public interface IAggregate<E> {
    boolean add(E element);

    boolean remove(E element);

    Iterator<E> iterator();
}
