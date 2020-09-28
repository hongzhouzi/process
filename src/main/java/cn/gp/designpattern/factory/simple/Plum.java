package cn.gp.designpattern.factory.simple;

/**
 * @author hongzhou.wei
 * @date 2020/9/28
 */
public class Plum implements IFruit {

    @Override
    public String producer() {
        return "生产-李子";
    }

//    @Override
//    public String consumer() {
//        return "消费-李子";
//    }
}
