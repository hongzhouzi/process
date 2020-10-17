package cn.gp.designpattern.m.strategy;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class ConcreteStrategyA implements IStrategy {
    @Override
    public void algorithm() {
        System.out.println("Strategy A");
    }
}
