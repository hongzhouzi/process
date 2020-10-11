package cn.gp.designpattern.d.proxy.dynamic.jdk;

import cn.gp.designpattern.d.proxy.dynamic.write.MyClassLoader;
import cn.gp.designpattern.d.proxy.dynamic.write.MyInvocationHandler;
import cn.gp.designpattern.d.proxy.dynamic.write.MyProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类
 * 需要实现 InvocationHandler 类，用到jdk中封装好的 Proxy.newProxyInstance()
 *
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class MyProxyProcess implements MyInvocationHandler {

    private ISubject target;
    public ISubject getInstance(ISubject target){
        this.target = target;
        Class<?> clazz = target.getClass();
        return (ISubject) MyProxy.newProxyInstance(new MyClassLoader(),
                clazz.getInterfaces(),
                this::invoke);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(this.target, args);
        after();
        return invoke;
    }
    private void after(){
        System.out.println("===static proxy 前置处理===");
    }
    private void before(){
        System.out.println("===static proxy 后置处理===");
    }
}
