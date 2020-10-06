package cn.gp.designpattern.a.factory.abstractfactory;

/**
 * 永辉水果超市-（具体的工厂）
 * @author hongzhou.wei
 * @date 2020/10/6
 */
public class YongHuiFruitFactory extends AbstractFruitFactory {
    @Override
    IApple saleApple() {
//        super.init();
        return new YongHuiApple();
    }

    @Override
    IPlum salePlum() {
//        super.init();
        return new YongHuiPlum();
    }
}
