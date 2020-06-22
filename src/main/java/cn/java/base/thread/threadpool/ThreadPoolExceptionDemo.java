package cn.java.base.thread.threadpool;

import java.util.concurrent.*;

/**
 * 线程池中对异常的获取
 *
 * @author hongzhou.wei
 * @date 2020/6/8
 */
public class ThreadPoolExceptionDemo {
    public static void main(String[] args) {
//        notTrace();
//        trace();
//        useFuture();
        useExecute();
    }

    /**
     * 没有打印异常的情况，发生了异常都不易察觉
     */
    static void notTrace() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 5,
            60, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            // i为0时会抛异常
            threadPoolExecutor.submit(new DivTask(100, i));
        }
    }

    /**
     * 1.使用execute提交方法，发生异常时使之打印出异常信息
     */
    static void useExecute() {
        ExecutorService es = new ThreadPoolExecutor(0, 5,
            60, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            // i为0时会抛异常
            es.execute((new DivTask(100, i)));
        }
    }

    /**
     * 2.使用Future获取异常
     */
    static void useFuture() {
        ExecutorService es = new ThreadPoolExecutor(0, 5,
            60, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            // i为0时会抛异常
            Future future = es.submit((new DivTask(100, i)));
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 3.使用自定义的打印堆栈异常行为的类，相比之下自定义这个打印的要更全面些
     * 可以从错误堆栈中看到是在哪里提交的任务
     */
    static void trace() {
        ThreadPoolExecutor threadPoolExecutor = new TraceThreadPoolExecutor(0, 5,
            60, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            // i为0时会抛异常
            threadPoolExecutor.execute(new DivTask(100, i));
        }
    }
}

/**
 * 做除法的类，用来演示异常发生
 */
class DivTask implements Runnable {

    int a, b;

    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        double ret = a / b;
        System.out.println(ret);
    }
}