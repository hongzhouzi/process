package cn.gp.designpattern.factory.factory;

/**
 * 创建水果的工厂
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public interface IFruitFactory {
    IFruit create(Class<? extends IFruit> clazz);
}
