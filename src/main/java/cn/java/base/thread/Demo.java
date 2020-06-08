package cn.java.base.thread;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author hongzhou.wei
 * @date 2020/5/4
 */
public class Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Demo demo = new Demo();
        demo.createThread();
    }

    public void createThread() throws ExecutionException, InterruptedException {
        // ===============创建线程===============
        //1.继承Thread
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println("继承Thread");
            }
        };
        //2.实现runable接口
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("实现runable接口");
            }
        };
        //3.实现callable接口
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("实现callable接口创建的线程");
                return "ok";
            }
        };

        // ============直接通过Thread类的start()启动线程==============
        // 1.Thread类：直接调用其start方法
        thread0.start();
        // 2.Runnable接口：从Thread类构造方法传入Runnable，调Thread.start()
        Thread thread1 = new Thread(runnable);
        thread1.start();
        // 3.Callable接口：从Thread类构造方法传入FutureTask，调Thread.start()
        FutureTask futureTask = new FutureTask(callable);
        Thread thread2 = new Thread(futureTask);
        thread2.start();
        try {
            // 获取线程返回值
            String ret = (String) futureTask.get();
            System.out.println(ret);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // ============通过线程池启动（间接用Thread.start()）===========
        ExecutorService service = Executors.newSingleThreadExecutor();
        // Runnable接口：提交到线程池
        service.execute(runnable);
        // Callable接口：提交到线程池
        Future<String> future = service.submit(callable);
        /*
        execute(Runnable task)无返回值，submit()有返回值
       若要用execute执行Callable接口的任务，则需要用FutureTask包装
         */
        service.execute(new FutureTask<>(callable));
        Future future1 = service.submit(new FutureTask<>(runnable,"aa"));
        System.out.println(future1.get());
        try {
            // 获取call方法返回参数
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
