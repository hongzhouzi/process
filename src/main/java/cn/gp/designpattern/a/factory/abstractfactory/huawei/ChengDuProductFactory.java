package cn.gp.designpattern.a.factory.abstractfactory.huawei;

/**
 * 成都地区-展览售卖抽象类中要求的产品
 *
 * @author hongzhou.wei
 * @date 2020/10/6
 */
public class ChengDuProductFactory extends AbstractProductFactory {
    @Override
    IHuaWeiP40 huaweiP40() {
        return new ChengDuHuaWeiP40();
    }

    @Override
    IHuaWeiMateBookX huaweiMateBookX() {
        return new ChengDuHuaWeiMateBookX();
    }
}
