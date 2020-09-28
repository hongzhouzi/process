package cn.gp.designpattern.factory.simple;

import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * 简单工厂-测试类
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class SimpleTest {
    public static void main(String[] args) {
        FruitFactory fruitFactory = new FruitFactory();
        IFruit apple = fruitFactory.create(Apple.class);
        System.out.println(apple.producer());
//        System.out.println( apple.consumer());

//        IFruit plum = fruitFactory.create(Plum.class);
//        System.out.println(plum.producer());
//        System.out.println(plum.consumer());

        // 在源码中的使用
        Calendar instance = Calendar.getInstance();
        LoggerFactory.getLogger(SimpleTest.class);
    }
}
