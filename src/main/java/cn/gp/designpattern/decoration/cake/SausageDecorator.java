package cn.gp.designpattern.decoration.cake;

/**
 * 具体装饰器——加香肠
 * 需要重写抽象组件和抽象装饰器中的方法
 *
 * @author hongzhou.wei
 * @date 2020/10/3
 */
public class SausageDecorator extends AbsBatterCakeDecorator {
    public SausageDecorator(AbsBatterCakeComponent batterCakeComponent) {
        super(batterCakeComponent);
    }

    @Override
    void doSomething() {

    }

    @Override
    String getMsg() {
        return super.getMsg() + "+1份香肠";
    }

    @Override
    int getPrice() {
        return super.getPrice() + 2;
    }
}
