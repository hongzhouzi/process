package cn.gp.designpattern.proxy.dynamic.write;

import javax.jws.Oneway;
import java.lang.reflect.Method;

/**
 * @author hongzhou.wei
 * @date 2020/10/3
 */
public interface MyInvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
