package cn.gp.designpattern.i.adapter.objectadapter;

import cn.gp.designpattern.i.adapter.classadpter.Adaptee;
import cn.gp.designpattern.i.adapter.classadpter.Target;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Adapter implements Target {
    private cn.gp.designpattern.i.adapter.classadpter.Adaptee adaptee;
    public Adapter(Adaptee adaptee){
        this.adaptee = adaptee;
    }
    @Override
    public int request() {
        return adaptee.specificRequest() / 10;
    }
}
