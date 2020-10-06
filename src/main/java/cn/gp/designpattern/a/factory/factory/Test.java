package cn.gp.designpattern.a.factory.factory;

/**
 * 工厂方法-测试类
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class Test {
    public static void main(String[] args) {
        // 实例化苹果工厂-生产苹果
        IFruitFactory appleFactory = new AppleFactory();
        IFruit apple= appleFactory.create();
        System.out.println(apple.producer());;
        // 实例化李子工厂-生产李子
        PlumFactory plumFactory = new PlumFactory();
        IFruit plum = plumFactory.create();
        System.out.println(plum.producer());;
    }
}
