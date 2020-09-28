package cn.gp.designpattern.factory.factory;

/**
 * 工厂方法-测试类
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class FactoryTest {
    public static void main(String[] args) {
        IFruitFactory appleFactory = new AppleFactory();
        IFruit redApple = appleFactory.create(AppleRed.class);
        IFruit greenApple = appleFactory.create(AppleGreen.class);
        System.out.println(redApple.producer());
        System.out.println(greenApple.producer());
    }
}
