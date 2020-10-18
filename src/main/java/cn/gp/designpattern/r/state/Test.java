package cn.gp.designpattern.r.state;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class Test {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(new ConcreteStateA());
        context.handle();
    }
}
