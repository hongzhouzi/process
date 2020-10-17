package cn.gp.designpattern.m.strategy;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Context {
    private IStrategy mStrategy;

    public Context(IStrategy strategy) {
        this.mStrategy = strategy;
    }

    public void algorithm() {
        this.mStrategy.algorithm();
    }
}
