package cn.gp.designpattern.r.state;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class ConcreteStateB extends State {
    @Override
    public void handle() {
        System.out.println("StateB do action");
    }
}
