package cn.gp.designpattern.j.bridge;

/**
 * 修正抽象
 *
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class RefinedAbstraction extends Abstraction {

    public RefinedAbstraction(IImplementor implementor) {
        super(implementor);
    }

    @Override
    public void operation() {
        super.operation();
        System.out.println("refined operation");
    }
}
