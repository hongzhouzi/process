package cn.gp.designpattern.a.factory.factory;


/**
 * 生产李子
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class Plum implements IFruit {
    @Override
    public String producer() {
        return "生产-李子";
    }
}
