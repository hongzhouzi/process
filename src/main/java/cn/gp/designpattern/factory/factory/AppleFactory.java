package cn.gp.designpattern.factory.factory;

/**
 * 苹果生产工厂
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class AppleFactory implements IFruitFactory {

    @Override
    public IFruit create(Class<? extends IFruit> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
