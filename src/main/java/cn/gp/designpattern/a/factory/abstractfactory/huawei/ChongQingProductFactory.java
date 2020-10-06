package cn.gp.designpattern.a.factory.abstractfactory.huawei;

/**
 * 重庆地区-展览售卖抽象类中要求的产品
 *
 * @author hongzhou.wei
 * @date 2020/10/6
 */
public class ChongQingProductFactory extends AbstractProductFactory {
    @Override
    IHuaWeiP40 huaweiP40() {
        return new ChongQingHuaWeiP40();
    }

    @Override
    IHuaWeiMateBookX huaweiMateBookX() {
        return new ChongQingHuaWeiMateBookX();
    }
}
