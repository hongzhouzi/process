package cn.gp.designpattern.i.adapter.interfaceadapter;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public abstract class Adapter implements Target {
    protected Adaptee adaptee;
    public Adapter(Adaptee adaptee){
        this.adaptee = adaptee;
    }


    /**
     * 用来适配更多可能性，在使用时选择性重写这些方法
     */
    public int request1() {
        return 0;
    }

    public int request2() {
        return 0;
    }

}
