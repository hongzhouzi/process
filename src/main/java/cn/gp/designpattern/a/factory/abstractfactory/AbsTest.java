package cn.gp.designpattern.a.factory.abstractfactory;

/**
 * @author hongzhou.wei
 * @date 2020/10/6
 */
public class AbsTest {
    public static void main(String[] args) {
        // 去永辉超市买些苹果和李子
        YongHuiFruitFactory yongHui = new YongHuiFruitFactory();
        System.out.println(yongHui.saleApple().info());;
        System.out.println(yongHui.salePlum().otherInfo());

        // 在盒马生鲜上买些苹果和李子
        HeMaFruitFactory heMa = new HeMaFruitFactory();
        System.out.println(heMa.saleApple().info());
        System.out.println(heMa.salePlum().otherInfo());

    }
}
