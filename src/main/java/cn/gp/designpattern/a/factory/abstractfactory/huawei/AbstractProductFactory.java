package cn.gp.designpattern.a.factory.abstractfactory.huawei;

/**
 * 要求展出售卖的产品
 *
 * @author hongzhou.wei
 * @date 2020/10/6
 */
public abstract class AbstractProductFactory {
    abstract IHuaWeiP40 huaweiP40();
    abstract IHuaWeiMateBookX huaweiMateBookX();
}
