package cn.gp.designpattern.a.factory.factory;


/**
 * 生产苹果
 *
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class Apple implements IFruit {
    @Override
    public String producer() {
        return "生产-苹果";
    }
}
