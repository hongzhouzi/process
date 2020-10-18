package cn.gp.designpattern.s.mediator;

/**
 * @author hongzhou.wei
 * @date 2020/10/19
 */
public abstract class Colleague {
    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}
