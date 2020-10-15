package cn.gp.designpattern.d.proxy.dynamic.cglib;


/**
 * 真实对象，不用实现接口
 *
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class RealSubject {
    public void business() {
        System.out.println(" this is RealSubject");
    }
}
