package cn.gp.designpattern.decoration.cake;

/**
 * 遵守规则的具体组件
 *
 * @author hongzhou.wei
 * @date 2020/10/3
 */
public class BaseBatterCakeConcreteComponent extends AbsBatterCakeComponent {
    @Override
    String getMsg() {
        return "煎饼";
    }

    @Override
    int getPrice() {
        return 5;
    }
}
