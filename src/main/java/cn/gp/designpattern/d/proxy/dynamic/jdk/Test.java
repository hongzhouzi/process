package cn.gp.designpattern.d.proxy.dynamic.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class Test {
    public static void main(String[] args) {
        ProxyProcess proxy = new ProxyProcess();
        ISubject subject = proxy.getInstance(new RealSubject());
        subject.business("test");

        // 将临时代码输出到磁盘，可通过反编译tool查看到源码
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0",
                new Class[]{ISubject.class});
        try {
            FileOutputStream fos = new FileOutputStream("E://$Proxy0.class");
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
