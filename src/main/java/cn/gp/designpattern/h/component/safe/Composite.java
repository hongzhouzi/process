package cn.gp.designpattern.h.component.safe;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 *
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public class Composite extends AbsComponent {
    private List<AbsComponent> mComponents;

    public Composite(String name) {
        super(name);
        this.mComponents = new ArrayList<AbsComponent>();
    }

    @Override
    public String operation() {
        StringBuilder builder = new StringBuilder(this.name);
        for (AbsComponent component : this.mComponents) {
            builder.append("\n");
            builder.append(component.operation());
        }
        return builder.toString();
    }


    public boolean addChild(AbsComponent component) {
        return this.mComponents.add(component);
    }


    public boolean removeChild(AbsComponent component) {
        return this.mComponents.remove(component);
    }


    public AbsComponent getChild(int index) {
        return this.mComponents.get(index);
    }

}