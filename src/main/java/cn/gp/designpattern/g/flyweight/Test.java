package cn.gp.designpattern.g.flyweight;

/**
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public class Test {
    public static void main(String[] args) {
        IFlyweight flyweight1 = FlyweightFactory.getFlyweight("aa");
        IFlyweight flyweight2 = FlyweightFactory.getFlyweight("bb");
        flyweight1.operation("a");
        flyweight1.operation("a1");
        flyweight2.operation("b");
    }
}
