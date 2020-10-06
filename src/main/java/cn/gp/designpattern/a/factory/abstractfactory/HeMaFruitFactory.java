package cn.gp.designpattern.a.factory.abstractfactory;

/**
 * 盒马生鲜水果超市-（具体的工厂）
 * @author hongzhou.wei
 * @date 2020/10/6
 */
public class HeMaFruitFactory extends AbstractFruitFactory {
    @Override
    IApple saleApple() {
//        super.init();
        return new HeMaApple();
    }

    @Override
    IPlum salePlum() {
//        super.init();
        return new HeMaPlum();
    }
}
