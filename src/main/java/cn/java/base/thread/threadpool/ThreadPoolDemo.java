package cn.java.base.thread.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 线程池相关练习demo
 *
 * @author weihongzhou
 * @date 2019/9/24
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
//        newCachedThreadPoolWhz();
//        scheduled();
        /**
         * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
         * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
         * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定。
         */
        extendThreadPool();
        // 可用CPU数量
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    /**
     * 扩展线程池demo-beforeExecute、afterExecute、terminated
     */
    static void extendThreadPool() {
        ExecutorService es = new ThreadPoolExecutor(
            2,
            5,
            0,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("whz threadFactory");
                    System.out.println("create:" + t);
                    return t;
                }
            }
        ) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行：" + ((MyRunnable) r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成：" + ((MyRunnable) r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };
        for (int i = 0; i < 5; i++) {
            es.execute(new MyRunnable("test thread" + i));
        }
        es.shutdown();
    }

    static void customThreadPool(){

        ExecutorService es = new ThreadPoolExecutor(
            2,
            5,
            0,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(),
            new ThreadFactoryBuilder().setNameFormat("").build(),
            new ThreadPoolExecutor.DiscardPolicy()
        );


    }

    /**
     * 使用Future接收线程返回值
     */
    static void getReturnVal() {
        //1 可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        Future future = newCachedThreadPool.submit(new MyCallable());
        if (!future.isDone()) {
            System.out.println("task has not finished, please wait!");
        }
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            //线程池用完之后需要关闭
            newCachedThreadPool.shutdown();
        }
    }

    /**
     * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
     */
    static void newCachedThreadPoolWhz() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        threadPoolDemo(cachedThreadPool);
        //线程池用完之后需要关闭，不然程序不会停止
        cachedThreadPool.shutdown();
    }

    static void newFixed() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        threadPoolDemo(fixedThreadPool);
        fixedThreadPool.shutdown();
    }

    /**
     * 创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
     */
    static void scheduled() {
        //----------------start--------------
        ScheduledExecutorService e = Executors.newScheduledThreadPool(1);
        // 在给定时间对任务进行一次调度
        e.schedule(() -> {
            Thread cur = Thread.currentThread();
            System.out.println("name:" + cur.getName() + cur.getState());
        }, 100, TimeUnit.MILLISECONDS);

        // 对任务进行周期性调度，以上个任务开始执行时间作为起点，在之后的period时间调度下次任务
        e.scheduleAtFixedRate(() -> {
            Thread cur = Thread.currentThread();
            System.out.println("name:" + cur.getName() + cur.getState());
        }, 0, 100, TimeUnit.MILLISECONDS);

        // 对任务进行周期性调度，在上个任务结束后再经过delay时间进行任务调度
        e.scheduleWithFixedDelay(() -> {
            Thread cur = Thread.currentThread();
            System.out.println("name:" + cur.getName() + cur.getState());
        }, 0, 100, TimeUnit.MILLISECONDS);
        e.shutdown();
        //-----------------end--------------

        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(1);
        //每隔一段时间就打印一点东西
        executorService.scheduleAtFixedRate(() -> {
            System.out.println("=====");
        }, 1000, 500, TimeUnit.MILLISECONDS);

        //每隔一段时间打印当前时间，证明两者是互不影响的
        executorService.scheduleAtFixedRate(() -> {
            System.out.println(new SimpleDateFormat("HH:mm:ss SSS").format(new Date()));
        }, 1000, 500, TimeUnit.MILLISECONDS);
    }

    static void threadPoolDemo(ExecutorService threadPool) {
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPool.execute(() -> {
                Thread cur = Thread.currentThread();
                System.out.println(index + " |name:" + cur.getName() + " |status:" + cur.getState());
            });

            Future future = threadPool.submit(new MyCallable() {
                @Override
                public Integer call() {
                    Thread cur = Thread.currentThread();
                    System.out.println(index + " |name:" + cur.getName() + " |status:" + cur.getState());
                    return index;
                }
            });

            System.out.println(threadPool);
        }
    }
}

class MyRunnable implements Runnable {
    public String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("正在执行：id:" + Thread.currentThread().getId() + "|name:" + name);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int value = 1;
        System.out.println("Ready to work");
        Thread.sleep(2000);
        System.out.println("task done");
        return value;
    }
}