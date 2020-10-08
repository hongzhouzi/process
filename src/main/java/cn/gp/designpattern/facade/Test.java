package cn.gp.designpattern.facade;

/**
 * 测试类
 *  又叫外观模式，提供了一个统一的接口用来访问子系统中的一群接口。
 *  主要特征是定义了一个高级接口让子系统更容易使用，属于结构性模式。
 *
 * @author hongzhou.wei
 * @date 2020/10/3
 */
public class Test {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.doA();
    }

}
