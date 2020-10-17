package cn.gp.designpattern.j.bridge;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public abstract class Abstraction {
    protected IImplementor mImplementor;

    public Abstraction(IImplementor implementor) {
        this.mImplementor = implementor;
    }

    public void operation() {
        this.mImplementor.operationImpl();
    }
}
