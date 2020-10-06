package cn.gp.designpattern.a.factory.simple;

/**
 * 生产水果的工厂类
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class FruitFactory {
    public IFruit create(Class<? extends IFruit> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
