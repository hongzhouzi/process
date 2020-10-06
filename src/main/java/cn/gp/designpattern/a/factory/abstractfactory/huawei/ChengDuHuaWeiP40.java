package cn.gp.designpattern.a.factory.abstractfactory.huawei;

/**
 * 某区域具体产品实现类
 *
 * @author hongzhou.wei
 * @date 2020/10/6
 */
public class ChengDuHuaWeiP40 implements IHuaWeiP40 {
    @Override
    public void price() {
        System.out.println("售价：$4488");
    }

    @Override
    public void info() {
        System.out.println("华为P40基本版……");
    }
}
