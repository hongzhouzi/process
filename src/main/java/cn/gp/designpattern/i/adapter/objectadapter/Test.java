package cn.gp.designpattern.i.adapter.objectadapter;

import cn.gp.designpattern.i.adapter.classadpter.Adaptee;
import cn.gp.designpattern.i.adapter.classadpter.Target;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Test {
    public static void main(String[] args) {
        Adaptee adaptee = new Adaptee();
        Target adapter = new Adapter(adaptee);
        System.out.println("源："+adaptee.specificRequest()+"目标："+adapter.request());
    }
}
