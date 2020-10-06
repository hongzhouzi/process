package cn.gp.designpattern.a.factory.factory;

/**
 * 苹果生产工厂
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class AppleFactory implements IFruitFactory {

    @Override
    public IFruit create(){
        return new Apple();
    }
}
