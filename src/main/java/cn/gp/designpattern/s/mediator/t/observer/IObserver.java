package cn.gp.designpattern.s.mediator.t.observer;

/**
 * 抽象观察者
 *
 * @author hongzhou.wei
 * @date 2020/10/19
 */
public interface IObserver<E> {
    void update(E event);
}
