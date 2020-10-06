package cn.gp.designpattern.b.singleton.hungry;

/**
 * 饿汉式单例
 * keys：
 * 1.类加载时实例化（线程还没出现前就实例化了，不存在多线程下访问安全性问题）
 * 2.构造器私有化
 * 3.静态全局访问点
 * 变量前可加可不加 final 修饰
 *
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return instance;
    }
}
