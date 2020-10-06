package cn.gp.designpattern.b.singleton;

import cn.gp.designpattern.b.singleton.lazy.LazyDoubleCheckSingleton;
import cn.gp.designpattern.b.singleton.lazy.LazySingleton;

/**
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class SingletonTest {
    public static void main(String[] args) {
        new Thread( new SingletonRunnableTest()).start();
        new Thread( new SingletonRunnableTest()).start();
        System.out.println("end");
    }

    static class SingletonRunnableTest implements Runnable{

        @Override
        public void run() {
//            LazySingleton lazySingleton = LazySingleton.getInstance();
            LazyDoubleCheckSingleton lazySingleton = LazyDoubleCheckSingleton.getInstance();
            System.out.println(Thread.currentThread().getName()+" : "+lazySingleton);
        }
    }
}
