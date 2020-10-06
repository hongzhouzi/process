package cn.gp.designpattern.a.factory.abstractfactory.huawei;

/**
 * 华为产品售卖有很多的实体店，官方提供了一个产品售卖库，
 * 目的是可以在任何一个售卖店都可以买到产品库中的产品，就
 * 需要定义一个抽象产品工厂（提供一个产品类的库），让每个
 * 售卖店都遵守规则，产品库中的产品必须展出售卖（所有的产
 * 品以同样的接口出现）；然后同款产品必须遵守同一个定价，
 * 那么我们购买时在任意一个售卖店买都可（客户端不依赖于具
 * 体实现，因为具体实现都遵守同一个规则）
 *
 * @author hongzhou.wei
 * @date 2020/10/7
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("=========成都地区==========");
        ChengDuProductFactory chendu = new ChengDuProductFactory();
        chendu.huaweiMateBookX().info();
        chendu.huaweiMateBookX().price();
        chendu.huaweiP40().info();
        chendu.huaweiP40().price();

        System.out.println("=========重庆地区==========");
        ChongQingProductFactory chongqing = new ChongQingProductFactory();
        chongqing.huaweiMateBookX().info();
        chongqing.huaweiMateBookX().price();
        chongqing.huaweiP40().info();
        chongqing.huaweiP40().price();

    }
}
