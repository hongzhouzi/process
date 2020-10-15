package cn.gp.designpattern.h.component.safe;

/**
 * 抽象根节点
 * 安全写法遵循最少知道原则
 *
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public abstract class AbsComponent {
    protected String name;

    public AbsComponent(String name) {
        this.name = name;
    }

    public abstract String operation();
}
