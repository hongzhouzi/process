package cn.gp.designpattern.proxy.static1;

/**
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class Test {
    public static void main(String[] args) {
        MyProxy proxy = new MyProxy(new RealSubject());
        proxy.business();
    }
}
