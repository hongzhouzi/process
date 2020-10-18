package cn.gp.designpattern.r.state;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class ConcreteStateA extends State {
    @Override
    public void handle() {
        System.out.println("StateA do action");
        // A状态完成后自动切换到B状态
        this.context.setState(Context.STATE_B);
        this.context.getState().handle();
    }
}
