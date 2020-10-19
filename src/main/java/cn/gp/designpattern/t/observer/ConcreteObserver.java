package cn.gp.designpattern.t.observer;

/**
 * 具体观察者
 *
 * @author hongzhou.wei
 * @date 2020/10/19
 */
public class ConcreteObserver<E> implements IObserver<E> {
    @Override
    public void update(E event) {
        System.out.println("receive event: " + event);
    }
}
