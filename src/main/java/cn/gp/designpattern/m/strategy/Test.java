package cn.gp.designpattern.m.strategy;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Test {
    public static void main(String[] args) {
        //选择一个具体策略
        IStrategy strategy = new ConcreteStrategyA();
        //来一个上下文环境
        Context context = new Context(strategy);
        //客户端直接让上下文环境执行算法
        context.algorithm();

        //选择一个具体策略
        IStrategy strategyB = new ConcreteStrategyB();
        //来一个上下文环境
        Context contextB  = new Context(strategyB);
        //客户端直接让上下文环境执行算法
        contextB.algorithm();
    }
}
