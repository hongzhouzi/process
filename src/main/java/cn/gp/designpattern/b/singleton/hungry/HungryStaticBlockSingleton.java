package cn.gp.designpattern.b.singleton.hungry;

/**
 * 饿汉式单例
 * keys：
 * 1.类加载时实例化（线程还没出现前就实例化了，不存在多线程下访问安全性问题）
 * 2.构造器私有化
 * 3.静态全局访问点
 * 变量前可加可不加 final 修饰
 * 和另一种写法相比没多大区别，只是换一种写法（在静态代码块中实例化）
 *
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class HungryStaticBlockSingleton {
    private static HungryStaticBlockSingleton instance ;
    static {
        instance = new HungryStaticBlockSingleton();
    }

    private HungryStaticBlockSingleton() {
    }

    public static HungryStaticBlockSingleton getInstance() {
        return instance;
    }
}
