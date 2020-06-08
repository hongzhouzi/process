package cn.java.base.interfac;

/**
 * 接口测试-实现的多个接口中有相同的方法签名，实现类中只能有一个
 * 这种情况接口调用时不用上转型
 *
 * @author hongzhou.wei
 * @date 2020/5/5
 */
public class Demo {
    public static void main(String[] args) {
        ICall am = new AndroidPhone();
        IGame ag = new AndroidPhone();
        am.basic();
        ((AndroidPhone) am).game();
        am.call();
        System.out.println("------------------");
        ag.basic();
        ag.game();
        ((AndroidPhone) ag).call();

    }
}
