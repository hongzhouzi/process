package cn.gp.designpattern.decoration.cake;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 具体装饰器——加鸡蛋
 * 需要重写抽象组件和抽象装饰器中的方法
 *
 * @author hongzhou.wei
 * @date 2020/10/3
 */
public class EggDecorator extends AbsBatterCakeDecorator {
    public EggDecorator(AbsBatterCakeComponent batterCakeComponent) {
        super(batterCakeComponent);
    }

    @Override
    void doSomething() {

    }

    @Override
    String getMsg() {
        return super.getMsg() + "+1个鸡蛋";
    }

    @Override
    int getPrice() {
        return super.getPrice() + 1;
    }
}
