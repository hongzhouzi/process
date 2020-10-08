package cn.gp.designpattern.b.singleton;

/**
 * @author hongzhou.wei
 * @date 2020/10/8
 */
public class ThreadLocalSingleton {
    private ThreadLocalSingleton(){}

    /*private static final ThreadLocal<ThreadLocalSingleton> INSTANT = new ThreadLocal<ThreadLocalSingleton>(){
        @Override
        protected ThreadLocalSingleton initialValue() {
            return new ThreadLocalSingleton();
        }
    };*/
    private static final ThreadLocal<ThreadLocalSingleton> INSTANT = ThreadLocal.withInitial(ThreadLocalSingleton::new);
    public static ThreadLocalSingleton getInstance(){
        return INSTANT.get();
    }
}
