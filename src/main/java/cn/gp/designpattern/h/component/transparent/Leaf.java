package cn.gp.designpattern.h.component.transparent;

/**
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public class Leaf extends AbsComponent {

    public Leaf(String name) {
        super(name);
    }

    @Override
    public String operation() {
        return this.name;
    }
}
