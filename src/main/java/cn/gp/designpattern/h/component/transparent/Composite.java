package cn.gp.designpattern.h.component.transparent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public class Composite extends AbsComponent {
    private List<AbsComponent> mAbsComponents;

    public Composite(String name) {
        super(name);
        this.mAbsComponents = new ArrayList<>();
    }

    @Override
    public String operation() {
        StringBuilder builder = new StringBuilder(this.name);
        for (AbsComponent component : this.mAbsComponents) {
            builder.append("\n");
            builder.append(component.operation());
        }
        return builder.toString();
    }

    @Override
    public boolean addChild(AbsComponent component) {
        return this.mAbsComponents.add(component);
    }

    @Override
    public boolean removeChild(AbsComponent component) {
        return this.mAbsComponents.remove(component);
    }

    @Override
    public AbsComponent getChild(int index) {
        return this.mAbsComponents.get(index);
    }
}
