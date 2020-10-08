package cn.gp.designpattern.b.singleton.lazy;

import java.io.Serializable;

/**
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class LazyInnerClassSingleton{

    /**
     * 正常情况单例模式是不会在内部使用它的，
     * 但要防止通过反射拿到它而进行多次实例化
     */
    private LazyInnerClassSingleton() {
        if(Holder.INSTANCE != null)
            throw new RuntimeException("不允许创建多个实例");
    }

    /**
     * 内部类默认不加载，使用的时候才加载，
     * 方法上加final关键字防止被重写，属性引用上加final不清楚为啥，感觉不加也没有问题。
     * 而且在调试时发现个问题：当一个线程在加载内部类时切换到另个线程会报出
     * 原因是：
     * 虚拟机会保证一个类的<clinit>()方法在多线程环境中被正确的加锁、同步，
     * 若多个线程同时去初始化一个类，那么只会有一个线程去执行这个类的<clinit>()方法，
     * 其他线程都需要阻塞等待，直到活动线程执行<clinit>()方法完毕
     */
    public final static  LazyInnerClassSingleton getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder{
        private static final LazyInnerClassSingleton INSTANCE = new LazyInnerClassSingleton();
    }
}
