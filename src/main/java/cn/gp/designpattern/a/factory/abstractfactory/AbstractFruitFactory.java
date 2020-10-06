package cn.gp.designpattern.a.factory.abstractfactory;

/**
 * 抽象水果工厂
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public abstract class AbstractFruitFactory {
    public void init(){
        System.out.println("初始化-展出水果");
    }

    abstract IApple saleApple();

    abstract IPlum salePlum();
}
