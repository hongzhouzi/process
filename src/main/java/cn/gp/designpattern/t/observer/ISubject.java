package cn.gp.designpattern.t.observer;

/**
 * 抽象主题者
 *
 * @author hongzhou.wei
 * @date 2020/10/19
 */
public interface ISubject<E> {
    boolean attach(IObserver<E> observer);

    boolean detach(IObserver<E> observer);

    void notify(E event);
}
