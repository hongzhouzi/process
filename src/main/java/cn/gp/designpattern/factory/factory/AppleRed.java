package cn.gp.designpattern.factory.factory;


/**
 * 生产红苹果的实现方法
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class AppleRed implements IFruit {
    @Override
    public String producer() {
        return "生产-红苹果";
    }
}
