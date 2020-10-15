package cn.gp.designpattern.d.proxy.dynamic.jdk;

/**
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class RealSubject implements ISubject{
    @Override
    public String business(String s) {
        System.out.println(s+" this is RealSubject");
        return s +  "this is RealSubject";
    }
}
