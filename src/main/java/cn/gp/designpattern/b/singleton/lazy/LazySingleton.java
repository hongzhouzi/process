package cn.gp.designpattern.b.singleton.lazy;

/**
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
