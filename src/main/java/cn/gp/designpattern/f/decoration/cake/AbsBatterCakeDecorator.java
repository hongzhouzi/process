package cn.gp.designpattern.f.decoration.cake;

/**
 * @author hongzhou.wei
 * @date 2020/10/3
 */
public abstract class AbsBatterCakeDecorator extends AbsBatterCakeComponent {
    // 静态代理
    private AbsBatterCakeComponent batterCakeComponent;

    public AbsBatterCakeDecorator(AbsBatterCakeComponent batterCakeComponent) {
        this.batterCakeComponent = batterCakeComponent;
    }

    /**
     * 用来扩展的抽象装饰器
     */
    abstract void doSomething();

    @Override
    String getMsg() {
        return this.batterCakeComponent.getMsg();
    }

    @Override
    int getPrice() {
        return this.batterCakeComponent.getPrice();
    }
}
