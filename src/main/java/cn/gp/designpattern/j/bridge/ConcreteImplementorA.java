package cn.gp.designpattern.j.bridge;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class ConcreteImplementorA implements IImplementor {

    @Override
    public void operationImpl() {
        System.out.println("I'm ConcreteImplementor A");
    }
}
