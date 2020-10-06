package cn.gp.designpattern.a.factory.abstractfactory.huawei;

/**
 * 某区域具体产品实现类
 *
 * @author hongzhou.wei
 * @date 2020/10/6
 */
public class ChengDuHuaWeiMateBookX implements IHuaWeiMateBookX {
    @Override
    public void price() {
        System.out.println("售价：$7999");
    }

    @Override
    public void info() {
        System.out.println("华为MateBookX基本版……");
    }
}
