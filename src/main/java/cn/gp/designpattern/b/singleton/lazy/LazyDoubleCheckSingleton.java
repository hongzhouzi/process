package cn.gp.designpattern.b.singleton.lazy;

/**
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class LazyDoubleCheckSingleton {
    private static LazyDoubleCheckSingleton instance;

    private LazyDoubleCheckSingleton() {
        if(instance != null)
            throw new RuntimeException("不允许创建多个实例");
    }

    public static LazyDoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (instance ==null) {
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
