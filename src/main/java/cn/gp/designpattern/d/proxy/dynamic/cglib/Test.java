package cn.gp.designpattern.d.proxy.dynamic.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

/**
 * @author hongzhou.wei
 * @date 2020/10/12
 */
public class Test {
    public static void main(String[] args) {
        // 利用CGLib的代理类可将内存中的.class文件写入本地磁盘
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./cglib_proxy_class/");

        RealSubject obj = (RealSubject) new ProxyProcess().getInstance(RealSubject.class);
        obj.business();
    }
}
