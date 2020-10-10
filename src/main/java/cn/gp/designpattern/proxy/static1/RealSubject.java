package cn.gp.designpattern.proxy.static1;

/**
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class RealSubject implements ISubject{
    @Override
    public void business() {
        System.out.println(" this is RealSubject");
    }
}