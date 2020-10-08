package cn.gp.designpattern.decoration.cake;

/**
 * @author hongzhou.wei
 * @date 2020/10/3
 */
public class Test {
    public static void main(String[] args) {
        // 买个煎饼
        AbsBatterCakeComponent batterCake = new BaseBatterCakeConcreteComponent();
        // 加个鸡蛋
        batterCake = new EggDecorator(batterCake);
        // 加份香肠
        batterCake = new SausageDecorator(batterCake);

        // 消费
        System.out.println(batterCake.getMsg() + " 价格:"+batterCake.getPrice());
    }
}
