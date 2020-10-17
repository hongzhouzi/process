package cn.gp.designpattern.n.chain;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class Test {
    public static void main(String[] args) {
        Handler handlerA = new ConcreteHandlerA();
        Handler handlerB = new ConcreteHandlerB();
        handlerA.setNextHanlder(handlerB);
        handlerA.handleRequest("requestB");
    }
}
