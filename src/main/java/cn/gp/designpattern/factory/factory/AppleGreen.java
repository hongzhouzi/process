package cn.gp.designpattern.factory.factory;


/**
 * 生产青苹果的实现方法
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class AppleGreen implements IFruit {
    @Override
    public String producer() {
        return "生产-青苹果";
    }

}
