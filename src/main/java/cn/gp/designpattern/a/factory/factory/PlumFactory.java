package cn.gp.designpattern.a.factory.factory;

/**
 * 李子生产工厂
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class PlumFactory implements IFruitFactory {

    @Override
    public IFruit create(){
        return new Plum();
    }
}
