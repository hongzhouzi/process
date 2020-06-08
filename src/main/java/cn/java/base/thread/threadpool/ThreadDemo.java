package cn.java.base.thread.threadpool;

import org.junit.Test;

import java.util.concurrent.*;

public class ThreadDemo {
    public static void main(String[] args) {
        threadPriority();
    }

    static void threadPriority() {
        Runnable r = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " i:" + i);
            }
        };
        Thread t1 = new Thread(r, "one");
        Thread t2 = new Thread(r, "two");
        // 设置线程优先级，[0,10]的整数，默认5。修改线程抢到CPU时间片的概率
        t1.setPriority(10);
        t2.setPriority(1);
        t1.start();
        t2.start();
    }

    @Test
    public void test() {
        /**
         * corePoolSize :线程池的核心池大小，在创建线程池之后，线程池默认没有任何线程。当有任务过来的时候才会去创建创建线程执行任务。换个说法，线程池创建之后，线程池中的线程数为0，当任务过来就会创建一个线程去执行，直到线程数达到corePoolSize 之后，就会被到达的任务放在队列中。（注意是到达的任务）。换句更精炼的话：corePoolSize 表示允许线程池中允许同时运行的最大线程数。如果执行了线程池的prestartAllCoreThreads()方法，线程池会提前创建并启动所有核心线程。

         maximumPoolSize :线程池允许的最大线程数，他表示最大能创建多少个线程。maximumPoolSize肯定是大于等于corePoolSize。

         keepAliveTime :非核心线程闲置超时时长，一个非核心线程若不干活(闲置状态)的时长超过这个参数所设定的时长，就会被销毁掉。默认情况下，只有线程池中线程数大于corePoolSize 时，keepAliveTime 才会起作用。如果设置allowCoreThreadTimeOut = true，则会作用于核心线程。

         Unit:keepAliveTime 的单位。TimeUnit是一个枚举类型

         workQueue ：一个阻塞队列，用来存储等待执行的任务，当线程池中的线程数超过它的corePoolSize的时候，线程会进入阻塞队列进行阻塞等待。通过workQueue，线程池实现了阻塞功能

         threadFactory ：线程工厂，用来创建线程。

         handler :表示当拒绝处理任务时的策略。
         */
        int corePoolSize = 5;
        int maximumPoolSize = 5;
        int keepAliveTime = 5;
        TimeUnit unit = TimeUnit.DAYS;

		/*Executor executor=new ThreadPoolExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit, workQueue);*/
    }

    @Test
    public void exc() {
        ExecutorService executorService = new ThreadPoolExecutor(
                10,
                10,
                1000,
                TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<Runnable>(10)
        );
        executorService.execute(() -> {
            System.out.println("run");
        });
//        ThreadPoolExecutor
//        Executors.newScheduledThreadPool();
//        Executors.newSingleThreadExecutor()
    }

}
