package cn.gp.designpattern.h.component.transparent;

/**
 * 抽象根节点
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public abstract class AbsComponent {
    protected String name;

    public AbsComponent(String name) {
        this.name = name;
    }

    public abstract String operation();

    public boolean addChild(AbsComponent component) {
        throw new UnsupportedOperationException("addChild not supported!");
    }

    public boolean removeChild(AbsComponent component) {
        throw new UnsupportedOperationException("removeChild not supported!");
    }

    public AbsComponent getChild(int index) {
        throw new UnsupportedOperationException("getChild not supported!");
    }
}