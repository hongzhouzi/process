package cn.gp.designpattern.d.proxy.static1;

/**
 * 代理类
 *
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class MyProxy implements ISubject{

    private ISubject subject;
    public MyProxy(ISubject subject){
        this.subject = subject;
    }
    @Override
    public void business() {
        after();
        subject.business();
        before();
    }

    private void after(){
        System.out.println("===static proxy 前置处理===");
    }
    private void before(){
        System.out.println("===static proxy 后置处理===");
    }
}
