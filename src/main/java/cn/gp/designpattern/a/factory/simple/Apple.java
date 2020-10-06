package cn.gp.designpattern.a.factory.simple;

/**
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class Apple implements IFruit {
    @Override
    public String producer() {
        return "生产-苹果";
    }

//    @Override
//    public String consumer() {
//        return "消费-苹果";
//    }
}
